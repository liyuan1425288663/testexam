package com.leyou.item.web;

import com.leyou.common.vo.PageResult;

import com.leyou.entity.Sku;
import com.leyou.entity.Spu;
import com.leyou.entity.SpuDetail;
import com.leyou.item.service.GoodService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


@RestController
public class GoodsController {

    @Autowired
    private GoodService goodService;

    /**
     * 分页查询spu
     * @param page
     * @param rows
     * @param saleable
     * @param desc
     * @param key
     * @return
     */
    @RequestMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "saleable",required = false)Boolean saleable,
            @RequestParam(value = "desc",defaultValue = "false")Boolean desc,
            @RequestParam(value = "key",required = false)String key
    ){
        PageResult<Spu> result=goodService.queryBradByPage(page,rows,saleable,desc,key);
        return ResponseEntity.ok(result);

    }


    /**
     * 商品添加
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public  ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        goodService.saveGoods(spu);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 商品新增
     * @param spu
     * @return
     */
    @PutMapping("goods")
    public  ResponseEntity<Void> updateGoods(@RequestBody Spu spu){
        goodService.updateGoods(spu);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据Spu 的id 查询详情Dateil
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> queryDateilById(@PathVariable("id") Long spuId){
        return  ResponseEntity.ok(goodService.queryDateilById(spuId));
    }
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long spuId){
          return   ResponseEntity.ok(goodService.querySkuBySpuId(spuId));
    }

    @GetMapping("/spu/del")
    public ResponseEntity<Integer> delGoods(@RequestParam("id") Long id){

        return   ResponseEntity.ok(goodService.delGoods(id));
    }


}
