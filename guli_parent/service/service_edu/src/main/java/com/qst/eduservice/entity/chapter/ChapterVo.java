package com.qst.eduservice.entity.chapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/16
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterVo {

    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();

}
