package com.leyou.item.service;

import com.leyou.item.pojo.Item;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ItamService {

  public Item   saveItam(Item item){
      int i = new Random().nextInt(100);
      item.setId(i);
      return  item;
  }

}
