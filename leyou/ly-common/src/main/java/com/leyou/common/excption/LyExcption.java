package com.leyou.common.excption;


import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LyExcption extends RuntimeException {
        private ExceptionEnum exceptionEnum;


}
