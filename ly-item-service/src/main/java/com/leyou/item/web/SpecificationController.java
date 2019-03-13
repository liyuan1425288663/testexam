package com.leyou.item.web;

import com.leyou.entity.SpecGroup;
import com.leyou.entity.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {


    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id 查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>>queryGroupByCid(@PathVariable("cid")Long cid){

        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));

    }

    /**
     * 根据组id 查询参数
     * @param gid 组id
     *  @param cid  分类id
     *   @param searching 是否否搜索
     * @return
     */
         @GetMapping("params")
        public ResponseEntity<List<SpecParam>> queryParamList(@RequestParam(value = "gid",required = false) Long gid, @RequestParam(value = "cid",required = false) Long cid, @RequestParam(value = "searching",required = false )Boolean searching){
             return ResponseEntity.ok(specificationService.queryParamList(gid,cid,searching));

        }


}
