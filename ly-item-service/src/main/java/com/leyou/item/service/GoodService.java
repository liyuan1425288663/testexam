package com.leyou.item.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.excption.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.entity.*;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    public PageResult<Spu> queryBradByPage(Integer page, Integer rows, Boolean saleable, Boolean desc, String key){
        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Spu.class);
        //搜索字段过滤
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        //上下架过滤
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        //默认排序
        example.setOrderByClause("last_update_time DESC");
        //查询
        List<Spu> spus = spuMapper.selectByExample(example);


        //判断
        if (CollectionUtils.isEmpty(spus)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOND);
        }
        //解析分类和品牌的名称
        loadCategoryAndBrandName(spus);

        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);
        return new PageResult<>(info.getTotal(), spus);

    }

    private void loadCategoryAndBrandName(List<Spu> spus){
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3())).stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names, "/"));
            //处理品牌名称
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }
    }

    @Transactional
    public void saveGoods(Spu spu){
        SaveSkuAndStock(spu);

    }

    private void SaveSkuAndStock(Spu spu){
        //新增Spu
        spu.setCreateTime(new Date());
        spu.setId(null);
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);
        int insert = spuMapper.insert(spu);
        if (insert != 1) {
            throw new LyException(ExceptionEnum.GOOD_SAVE_ERROR);
        }
        //新增 datail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);
        //新增sku

        //定义库存集合

        ArrayList<Stock> StockList = new ArrayList<>();

        List<Sku> skus = spu.getSkus();

        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            insert = skuMapper.insert(sku);

            if (insert == 0) {
                throw new LyException(ExceptionEnum.GOOD_SAVE_ERROR);
            }
            //新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            //添加到集合
            StockList.add(stock);

        }
        //批量添加库存
        insert = stockMapper.insertList(StockList);
        if (insert == 0) {
            throw new LyException(ExceptionEnum.GOOD_SAVE_ERROR);
        }
    }


    @Transactional
    public void updateGoods(Spu spu){
        Long oldid = spu.getId();
        if (spu.getId() == null) {
            throw new LyException(ExceptionEnum.GOOD_ID_CANNOT_BE_NULL);
        }
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spu.getId());
        //查询sku
        List<Sku> selectList = skuMapper.select(sku);
        if (!CollectionUtils.isEmpty(selectList)) {

            //删除sku
            int   delete = skuMapper.delete(sku);
            //删除stock
            List<Long> ids = selectList.stream().map(Sku::getId).collect(Collectors.toList());
            int i = stockMapper.deleteByIdList(ids);
        }

        //修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1) {
            throw new LyException(ExceptionEnum.GOOD_UPDATE_ERROR);
        }
        //修改detail
        count = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());

        if (count != 1) {
            throw new LyException(ExceptionEnum.GOOD_UPDATE_ERROR);
        }
        SaveSkuAndStock(spu);


        //删除  old spu
        spuMapper.deleteByPrimaryKey(oldid);
   //   删除spuDetaile
       spuDetailMapper.deleteByPrimaryKey(oldid);

    }


    public SpuDetail queryDateilById(Long spuId){
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if (spuDetail == null) {
            throw new LyException(ExceptionEnum.SPEC_DATEIL_NOT_FOND);
        }
        return spuDetail;
    }

    public List<Sku> querySkuBySpuId(Long spuId){
        //查询sku
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);

        if (CollectionUtils.isEmpty(skus)) {
            throw new LyException(ExceptionEnum.SPEC_SKU_NOT_FOND);
        }
        //查询库存
  /*      for (Sku s : skus) {

            Stock stock = (Stock) stockMapper.selectByPrimaryKey(s.getId());
            if (stock==null){
                throw  new LyException(ExceptionEnum.SPEC_STOCK_NOT_FOND);
            }
            s.setStock(stock.getStock());
        }*/

        List<Long> ids = skus.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stocklist = stockMapper.selectByIdList(ids);

        if (CollectionUtils.isEmpty(stocklist)) {
            throw new LyException(ExceptionEnum.SPEC_STOCK_NOT_FOND);
        }

        //我们把srock 变成 一个map 其实key sku 的id 就是库存值
        Map<Long, Integer> stockMap =
                stocklist.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skus.forEach(s -> s.setStock(stockMap.get(s.getId())));


        return skus;

    }


    @Transactional
    public Integer delGoods(Long id){
        Sku sku = new Sku();
        sku.setSpuId(id);
        //删除sku
        int delete = skuMapper.delete(sku);
        //删除spu
        Spu spu = new Spu();
        spu.setId(id);
        delete = spuMapper.delete(spu);
        //删除SpuDetail
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(id);
        delete = spuDetailMapper.delete(spuDetail);
        if (delete == 0) {
            throw new LyException(ExceptionEnum.DEL_GOODS_ERROR);
        }
        return delete;
    }



    public void editshelf(Long id, Integer sal){

        spuMapper.editshelf(id,sal);
    }
}
