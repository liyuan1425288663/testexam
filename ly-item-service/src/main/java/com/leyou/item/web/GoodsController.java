package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.entity.Brand;
import com.leyou.entity.Spu;
import com.leyou.item.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
