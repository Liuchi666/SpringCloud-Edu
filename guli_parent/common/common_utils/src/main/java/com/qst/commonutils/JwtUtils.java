package com.qst.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author helen
 * @since 2019/10/16
 *          token是按照一定规则生成的字符串，包含用户信息，但是生成规则不统一，
 *          所以一般采用官方给出的一套通用的规则，即 JWT
 *          JWT由三部分组成(通过 "."隔开):
 *              第一部分：jwt头信息
 *              第二部分：有效载荷 包含主体信息(用户信息)
 *              第三部分：签名哈希，防伪标志
 *          JWT示例:
 *              eyJhbGciOiJIUzI1NiJ9
 *              .eyJqdGkiOiJjYWM2ZDVhZi1mNjVlLTQ0MDAtYjcxMi0zYWEwOGIyOTIwYjQiLCJzdWIiOiJzZyIsImlzcyI6InNnIiwiaWF0IjoxNjM4MTA2NzEyLCJleHAiOjE2MzgxMTAzMTJ9
 *              .JVsSbkP94wuczb4QryQbAke3ysBDIL5ou8fWsbt_ebg
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;  //token有效期
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";   //秘钥，用于生成签名

    /**
     *   根据用户id和用户名生成一个Token(当然，这里只是用的这两个，也可以用其他的用户信息)
     */
    public static String getJwtToken(String id, String nickname){
        String JwtToken = Jwts.builder()
                //jwt第一部分(头信息)
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                //设置jwt过期时间
                .setSubject("guli-user")  //主题
                .setIssuedAt(new Date())  //签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))  //过期时间，是用当前时间戳+有效期得到

                //jwt第二部分(有效载荷，包含用户信息)
                .claim("id", id)
                .claim("nickname", nickname)

                //jwt第三部分(签名)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
