package com.leyou.entity;

import lombok.Data;

@Data
public class User {
    private String username;

    private String loginname;

    private String password;

    private String code;

    private String phone;


    private int sex;
    //用户的头像信息
    private String userImage;
    //用户当前登录ip
    private  String address;

}
