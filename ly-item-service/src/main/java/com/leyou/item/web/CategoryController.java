package com.leyou.item.web;



import com.leyou.entity.Category;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    /**
     * 根据父节点id 查询商品分类
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> quertCategoryList(@RequestParam("pid") Long pid){

        return ResponseEntity.ok(categoryService.quertCategoryList(pid));
    }



}
