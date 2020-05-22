package com.yj.im.project.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import org.springframework.ui.ModelMap;

import java.io.Serializable;

/**
 * 返回json 数据
 */
@JsonInclude(Include.NON_NULL)
public class CommonResult implements Serializable {

    private static final long serialVersionUID = 2120869894112984147L;
    private Integer code = 0;
    private String message = null;
    private Object result = null;
    private int count;


    public CommonResult() {
    }

    public CommonResult(ResultConstantsEnum code) {
        this.code = code.getCode();
    }

    public CommonResult(Object result) {
        this.result = result;
    }

    public CommonResult(ResultConstantsEnum code, String message) {
        this.code = code.getCode();
        this.message = message;
    }

    public CommonResult(ResultConstantsEnum code, Object result) {
        this.code = code.getCode();
        this.result = result;
    }


    public CommonResult(ResultConstantsEnum code, String message, Object result) {
        this.code = code.getCode();
        this.message = message;
        this.result = result;
    }

    public CommonResult(ResultConstantsEnum code, String message, Object result, int count) {
        this.code = code.getCode();
        this.message = message;
        this.result = result;
        this.count = count;
    }

    public CommonResult(ResultConstantsEnum code, Object result, int count) {
        this.code = code.getCode();
        this.result = result;
        this.count = count;
    }

    public CommonResult(String message, Object result) {
        this.message = message;
        this.result = result;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toFtl(ModelMap model, String url) {

        return url;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}