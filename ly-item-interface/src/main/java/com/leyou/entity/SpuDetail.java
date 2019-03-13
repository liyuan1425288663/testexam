package com.leyou.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author li
 */
@Table(name="tb_spu_detail")
@Data
public class SpuDetail {
    @Id
    private Long spuId;//对应spu的id
    //商品描述
    private String description;
    //商品的全局规格属性
    private String genericSpec;
    //商品特殊规格的名称及可选模板
    private String specialSpec;
    //包装清单
    private String packingList;
    //售后服务
    private String afterService;
}
