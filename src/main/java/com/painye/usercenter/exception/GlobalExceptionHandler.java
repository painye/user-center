package com.painye.usercenter.exception;

import com.painye.usercenter.common.BaseResponse;
import com.painye.usercenter.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author pan
 * @ClassName : com.painye.usercenter.exception.GlobalExceptionHandler
 * @Description : 全局异常类处理器
 * @date 2024/7/10 21:19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(final BusinessException e) {
        log.error("{},{}", e.getMessage(), e.getDetail(), e);
        return ResponseUtils.error(e.getCode(), e.getMessage(), e.getDetail());
    }
}
