package com.qst.eduservice.entity.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/16
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
    private String id;
    private String title;
    //是否可以试听：0收费 1免费
    private Boolean isFree;
    //云端视频资源
    private String videoSourceId;
}
