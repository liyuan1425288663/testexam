package com.leyou.item.web;

import com.alibaba.fastjson.JSON;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.excption.LyExcption;
import com.leyou.common.utills.AESUtils;
import com.leyou.common.utills.CommonUtils;
import com.leyou.common.utills.MD5;
import com.leyou.common.utills.jwt.JWTUtils;
import com.leyou.entity.User;
import com.leyou.item.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<User> login(HttpServletRequest request) throws Exception{
        //获取参数
        Map<String, Object> parameterMap = CommonUtils.getParameterMap ( request );
        String canshu=parameterMap.get ( "canshu" ).toString ();
        //AES解密参数
        String decrypt = AESUtils.desEncrypt ( canshu );//json的参数

        User user=null;
        User userget=null;
        user = JSON.parseObject ( decrypt, User.class );
        String password=user.getPassword ();
        if(decrypt!=null){

            password= MD5.encryptPassword ( password, password);

             userget = userService.getUser(user.getUsername());
            if(userget==null){
                throw   new LyExcption(ExceptionEnum.USER_NULL);
            }
            if (!userget.getPassword().equals(password)){

                throw   new LyExcption(ExceptionEnum.USER_NULL);

            }else {

                return  ResponseEntity.ok(userget);
            }

        }
        //根据用户信息生成Token
        String token = JWTUtils.generateToken ( JSON.toJSONString ( userget ) );
  /*      responseResult.setToken ( token );

        responseResult.setResult ( userget )
        //将用户信息放进redis中
    redisTemplate.opsForHash ().put ( userget.getId (),"userinfo", userget);
;*/


        return  null;
    }



}
