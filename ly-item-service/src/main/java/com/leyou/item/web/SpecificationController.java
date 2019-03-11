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
     * @param gid
     * @return
     */
         @GetMapping("params")
        public ResponseEntity<List<SpecParam>> queryParamByGid(@RequestParam("gid") Long gid){
             return ResponseEntity.ok(specificationService.queryParamByGid(gid));

        }


}
