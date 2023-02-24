package com.cc.domain.entity;

import java.time.LocalDateTime;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户(User)表实体类
 *
 * @author makejava
 * @since 2023-02-20 19:35:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {
    
    @TableId
    private Integer id;
    //微信openid    
    private String openid;
    //用户名    
    private String username;
    //头像    
    private String avatar;
    //个性签名    
    private String tags;
    //1表示正常，0表示封禁    
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //1表示删除，0表示未删除    
    private Integer delFlag;
    //1表示男，0表示女    
    private Integer sex;
    //出生年月    
    private LocalDateTime birthday;
    //背景图片    
    private String background;
    //学习目标    
    private String target;
    //学习目标的截止时间    
    private LocalDateTime targetTime;
    //正在学习的单词书Id    
    private Integer bookId;
}

