package com.cc.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 20:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    //用户名
    private String username;
    //头像
    private String avatar;
    //个性签名
    private String tags;
    //1表示正常，0表示封禁
    private String status;
    //1表示男，0表示女
    private Integer sex;
    //出生年月
    private LocalDateTime birthday;
    //背景图片
    private String background;
}
