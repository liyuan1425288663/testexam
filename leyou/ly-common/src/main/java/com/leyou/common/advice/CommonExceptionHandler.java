package com.leyou.common.advice;


import com.leyou.common.excption.LyExcption;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(LyExcption.class)
    public ResponseEntity<ExceptionResult> hadlerExcption(LyExcption e){

        return  ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));

    }
}
