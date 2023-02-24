package com.cc.domain.dto;


import lombok.Data;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


/**
 * @author cc
 * @version 1.0
 * @date 2023/2/21 10:40
 */
@Data
public class UserDto {

    //目标分组
    public interface Target {
    }


    //用户名
    private String username;
    //头像
    private String avatar;
    //个性签名
    private String tags;
    //1表示男，0表示女
    private Integer sex;
    //出生年月
    private LocalDateTime birthday;
    //背景图片
    private String background;
    //学习目标
    @NotBlank(message = "目标不能为空",groups = {Target.class})
    @Length(min = 1, max = 10, message = "目标长度必须在1-10之间",groups = {Target.class})
    private String target;
    //学习目标的截止时间
    @Future(message = "截止时间错误",groups = {Target.class})
    private LocalDateTime targetTime;
    //正在学习的单词书Id
    private Integer bookId;
}
