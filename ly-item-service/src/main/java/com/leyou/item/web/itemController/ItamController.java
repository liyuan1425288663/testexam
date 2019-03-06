package com.leyou.item.web.itemController;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.excption.LyExcption;
import com.leyou.item.pojo.Item;
import com.leyou.item.service.ItamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.leyou.common.enums.ExceptionEnum.PRICE_CANNOT_BE_NULL;


@RestController
@RequestMapping("itam")
public class ItamController extends  RuntimeException {


    @Autowired
    private ItamService itamService;

    @PostMapping
    public ResponseEntity<Item> saveItam(Item item){

        //校验价格
        if (item.getPrice()==null){

            throw  new LyExcption(ExceptionEnum.PRICE_CANNOT_BE_NULL);

        }
        itamService.saveItam(item);

        return  ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
