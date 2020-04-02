package com.lizhihong.basecomment.exception;

import com.lizhihong.basecomment.api.vo.R;
import com.lizhihong.basecomment.constant.ResultCodeEnum;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Mr.Joker
 * @date 2020/03/24
 * @time 22:39:08
 * @description 统一异常处理器
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*----------通用异常处理方法-----------*/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        // e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error();
    }

    /*----------指定异常处理方法-----------*/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R error(NullPointerException e) {
        // e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.NULL_POINTER);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public R error(HttpClientErrorException e) {
        // e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.HTTP_CLIENT_ERROR);
    }

    /*----------自定义异常处理方法-----------*/
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public R error(GlobalException e) {
        // e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }

}