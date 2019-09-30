package com.mybatis.generation.util.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

/**
 * 返回结果封装
 *
 * @author Liu Runyong
 * @date 2019/8/15
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ResponseResult<T> implements Serializable {

    private Integer status;
    private String msg;
    private String url;
    private T data;

    private ResponseResult() {
    }

    private ResponseResult(Integer status) {
        this.status = status;
    }

    private ResponseResult(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private ResponseResult(Integer status, String msg, T data) {
        this.data = data;
        this.status = status;
        this.msg = msg;
    }

    private ResponseResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseResult(Integer status, String msg, String url) {
        this.status = status;
        this.msg = msg;
        this.url = url;
    }


    public static <T> ResponseResult<T> createBySuccess() {
        return new ResponseResult<T>(ResponseResultEnum.SUCCESS.getCode());
    }

    public static <T> ResponseResult<T> createBySuccessMessage(String msg) {
        return new ResponseResult<T>(ResponseResultEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResponseResult<T> createBySuccess(T data) {
        return new ResponseResult<T>(ResponseResultEnum.SUCCESS.getCode(), data);
    }


    public static <T> ResponseResult<T> createBySuccess(String msg, T data) {
        return new ResponseResult<T>(ResponseResultEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResponseResult<T> createByError() {
        return new ResponseResult<T>(ResponseResultEnum.ERROR.getCode(), ResponseResultEnum.ERROR.getDesc());
    }

    public static <T> ResponseResult<T> createByError(T data) {
        return new ResponseResult<T>(ResponseResultEnum.ERROR.getCode(), data);
    }

    public static <T> ResponseResult<T> createByErrorMessage(String errorMessage) {
        return new ResponseResult<T>(ResponseResultEnum.ERROR.getCode(), errorMessage);
    }

    public static <T> ResponseResult<T> createByErrorCodeMessage(Integer errorCode, String errorMessage) {
        return new ResponseResult<T>(errorCode, errorMessage);
    }

    public static <T> ResponseResult<T> createByErrorInfo(String msg, String url) {
        return new ResponseResult<T>(ResponseResultEnum.EXCEPTION.getCode(), msg, url);
    }

}
