package com.leyou.item.web;

import com.leyou.common.utills.AESUtils;
import com.leyou.common.utills.CommonUtils;
import com.leyou.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {

    @RequestMapping("login")
    public ResponseEntity<User> login(HttpServletRequest request) throws Exception{
        //获取参数
        Map<String, Object> parameterMap = CommonUtils.getParameterMap ( request );
        String canshu=parameterMap.get ( "canshu" ).toString ();
        //AES解密参数
        String decrypt = AESUtils.desEncrypt ( canshu );//json的参数

        User user=null;





        return  null;
    }



}
