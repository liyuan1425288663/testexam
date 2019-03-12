package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.excption.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.entity.Brand;
import com.leyou.item.mapper.BrandMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBradByPage(Integer page, Integer rows, String sortBy, boolean desc, String key){

        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Brand.class);


        if (StringUtils.isNotBlank(key)){
            //过滤条件
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if (StringUtils.isNotBlank(sortBy)){
            String orderByCause=sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByCause);
        }
        //查询
        List<Brand> list = brandMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUN);
        }
        PageInfo<Brand> brandPageInfo = new PageInfo<>(list);

        return  new PageResult<>(brandPageInfo.getTotal(),list);
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids){
        //新增品牌
        brand.setId(null);
        int insert = brandMapper.insert(brand);

        if (insert !=1){
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        //新增中间表
        for (Long cid : cids) {
            int i = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (i!=1){
                throw new LyException(ExceptionEnum.BRAND_NOT_FOUN);
            }
        }
    }


    public Brand  queryById(Long id){
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand==null){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUN);
        }
        return  brand;
    }
}
