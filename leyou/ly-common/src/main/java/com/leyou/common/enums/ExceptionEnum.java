package com.leyou.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnum{

    PRICE_CANNOT_BE_NULL(404,"价格不能为空!"),
    CATEGORY_NOT_FOND(404,"商品分类没查到!"),
    USER_NULL(400,"账号密码错误"),
    BRAND_NOT_FOUN(404,"品牌没查到!"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),

    ;
  private  int code;
  private  String msg;


}
