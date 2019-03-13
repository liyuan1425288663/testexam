package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.entity.Brand;
import com.leyou.entity.Spu;
import com.leyou.item.service.GoodService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("goods")
    public  ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        goodService.saveGoods(spu);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
