package com.leyou.item.mapper;

import com.leyou.entity.Brand;
import com.leyou.entity.Spu;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuMapper extends Mapper<Spu> {


    @Update("update tb_spu set saleable=#{sal} where id=#{id}")
    void editshelf(@Param("id")Long id,@Param("sal") Integer sal);

}
