package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.excption.LyException;
import com.leyou.entity.Category;
import com.leyou.item.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> quertCategoryList(Long pid){
//查询条件  mapper 会把对象中的非空属性作为查询条件
        Category category = new Category();
        category.setParentId(pid);
        List<Category> list = categoryMapper.select(category);
        //判断结果
        if (CollectionUtils.isEmpty(list)){
            throw   new LyException(ExceptionEnum.PRICE_CANNOT_BE_NULL);
        }
        return  list;

    }

    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryByBrandId(bid);
    }

    public List<Category>queryByIds(List<Long> ids){
        List<Category> list = categoryMapper.selectByIdList(ids);
        //判断结果
        if (CollectionUtils.isEmpty(list)){
            throw   new LyException(ExceptionEnum.PRICE_CANNOT_BE_NULL);
        }
        return  list;
    }

}
