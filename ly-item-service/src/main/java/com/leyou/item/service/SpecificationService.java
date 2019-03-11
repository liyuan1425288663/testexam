package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.excption.LyException;
import com.leyou.entity.SpecGroup;
import com.leyou.entity.SpecParam;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper Param;

    public List<SpecGroup> queryGroupByCid(Long cid){
        //查询条件
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
  //查到
        List<SpecGroup> list = specGroupMapper.select(specGroup);

        if (CollectionUtils.isEmpty(list)){
            //没查到
            throw  new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return  list;
    }

    public List<SpecParam> queryParamByGid(Long gid){
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);

        List<SpecParam> list = Param.select(specParam);

        if (CollectionUtils.isEmpty(list)){
            //没查到
            throw  new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
        }
        return  list;
    }
}
