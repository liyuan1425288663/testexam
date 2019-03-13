package com.leyou.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
@RegisterMapper
public interface baseMapper<T> extends Mapper<T>, IdListMapper<T,Long>, InsertListMapper<T> {

}
