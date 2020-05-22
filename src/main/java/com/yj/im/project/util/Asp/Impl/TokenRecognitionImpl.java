package com.yj.im.project.util.Asp.Impl;

import com.yj.im.project.util.CommonResult;
import com.yj.im.project.util.contants.ResultConstantsEnum;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Aspect
@Component
public class TokenRecognitionImpl {

    @Pointcut("@annotation(com.yj.im.project.util.Asp.TokenRecognition)")
    public void tokenPointcut() {

    }

    @Before("tokenPointcut()")
    public void tokenMethod() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        if (request.getHeader("Authorization") == null) {
            JSONObject responseJSONObject = JSONObject.fromObject(new CommonResult(ResultConstantsEnum.ERROR_IS_501, "用户信息异常,请重新登录！！！"));
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

    public Method currentMethod(String methodName, JoinPoint joinPoint) {
        Method resultMethod = null;
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }


}
