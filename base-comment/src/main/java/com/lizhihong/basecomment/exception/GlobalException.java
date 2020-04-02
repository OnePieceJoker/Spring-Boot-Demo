package com.lizhihong.basecomment.exception;

import com.lizhihong.basecomment.constant.ResultCodeEnum;

import lombok.Data;

/**
 *
 * @author Mr.Joker
 * @date 2020/03/24
 * @time 22:38:30
 * @description 全局异常类
 */
@Data
public class GlobalException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer code;

    public GlobalException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public GlobalException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GlobalException{code=" + code + ",message=" + this.getMessage() + "}";
    }
}