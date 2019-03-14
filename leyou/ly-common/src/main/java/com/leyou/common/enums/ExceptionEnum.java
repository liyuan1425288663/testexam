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
    SPEC_GROUP_NOT_FOND(404,"商品分类没查到!"),
    SPEC_DATEIL_NOT_FOND(404,"商品详情不存在!"),
    SPEC_PARAM_NOT_FOND(404,"商品参数不存在!"),
    GOODS_NOT_FOND(404,"商品参数不存在!"),
    USER_NULL(400,"账号密码错误"),
    BRAND_NOT_FOUN(404,"品牌没查到!"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    UPLOAD_FILE_TYPE_ERROR(500,"上传文件失败"),
    INVALID_FILE_TYPE(500,"文件类型无效"),
    GOOD_SAVE_ERROR(500,"新增商品失败"),
    GOOD_UPDATE_ERROR(500,"修改商品失败"),
    GOOD_ID_CANNOT_BE_NULL(500,"商品ID不能为空"),
    SPEC_SKU_NOT_FOND(500,"商品Sku不存在"),
    SPEC_STOCK_NOT_FOND(500,"商品STOCK不存在"),
    DEL_GOODS_ERROR(500,"刪除失敗"),
    ;
  private  int code;
  private  String msg;


}
