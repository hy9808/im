package com.yj.im.project.config.exception;

import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice(annotations = {RestController.class})
public class ExceptionHandlerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);


    /**
     * 自定义异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public CommonResult sqlExceptionHandle(HttpServletRequest request, HttpServletResponse response, BaseException e) {
        LOGGER.error("********************Throw SqlException.url:{} ERROR:{}********************", request.getRequestURL(), e.getMessage(), e);
        return new CommonResult(ResultConstantsEnum.ERROR_IS_RunTimeException, "出现错误");
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception e) {
        LOGGER.error("********************Throw Exception.url:{} ERROR:{}********************", request.getRequestURL(), e.getMessage(), e);
        CommonResult exc = new CommonResult(ResultConstantsEnum.ERROR_IS_RunTimeException, "出现错误");
        responseOutWithJson(response, exc);
        return exc;
    }

    /**
     * 400错误
     *
     * @param e
     * @return java.lang.ResponseResult
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, TypeMismatchException.class, MissingServletRequestParameterException.class})
    public CommonResult requestNotReadable(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException e) {
        LOGGER.error("********************Throw Exception.url:{} ERROR:{}********************", request.getRequestURL(), e.getMessage(), e);
        CommonResult commonResult = new CommonResult(ResultConstantsEnum.ERROR_IS_400, "400错误");
        responseOutWithJson(response, commonResult);
        return commonResult;
    }

    /**
     * 404错误
     *
     * @param e
     * @return java.lang.ResponseResult
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public CommonResult requestNotReadable(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException e) {
        LOGGER.error("********************Throw Exception.url:{} ERROR:{}********************", request.getRequestURL(), e.getMessage(), e);
        CommonResult exc = new CommonResult(ResultConstantsEnum.ERROR_IS_404, "请求找不到结果");
        responseOutWithJson(response, exc);
        return exc;
    }

    /**
     * 405错误
     *
     * @param e
     * @return java.lang.ResponseResult
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public CommonResult request405(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
        LOGGER.error("********************Throw Exception.url:{} ERROR:{}********************", request.getRequestURL(), e.getMessage(), e);
        CommonResult cmr = new CommonResult(ResultConstantsEnum.ERROR_IS_405, "方法不支持");
        responseOutWithJson(response, cmr);
        return cmr;

    }

    /**
     * 406错误
     *
     * @param e
     * @return java.lang.ResponseResult
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public CommonResult request406(HttpServletRequest request, HttpServletResponse response, HttpMediaTypeNotAcceptableException e) {
        LOGGER.error("********************Throw Exception.url:{} ERROR:{}********************", request.getRequestURL(), e.getMessage(), e);
        CommonResult cmr = new CommonResult(ResultConstantsEnum.ERROR_IS_406, "媒体类型不可接受");
        responseOutWithJson(response, cmr);
        return cmr;
    }

    /**
     * Throwable拦截器
     *
     * @param request
     */
    @ExceptionHandler(Throwable.class)
    public CommonResult exceptionHandle(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        LOGGER.error("********************Throw Exception.url:{} ERROR:{}********************", request.getRequestURL(), e.getMessage(), e);
        CommonResult cmr = new CommonResult(ResultConstantsEnum.ERROR_IS_500, "服务器异常");
        responseOutWithJson(response, cmr);
        return cmr;
    }

    /**
     * 以JSON格式输出
     *
     * @param response
     */
    protected void responseOutWithJson(HttpServletResponse response,
                                       Object responseObject) {
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(responseJSONObject.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


}
