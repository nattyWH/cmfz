package com.baizhi.wangh.controller;

import com.baizhi.wangh.entity.Admin;
import com.baizhi.wangh.entity.Logentity;
import com.baizhi.wangh.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    HttpServletRequest request;
    @Autowired
    LogService logService;


    @Around(value = "pt()")
    public Object testAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object proceed;
        //怎么获取执行操作的用户
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        //怎么获取执行时间
        Date date = new Date();
        //获取方法对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解对象
        Log annotation = method.getAnnotation(Log.class);
        String name = annotation.name();

        //事情的执行结果
        boolean result = false;

        try{
            proceed = proceedingJoinPoint.proceed();
            result = true;
            return proceed;
        }catch (Throwable throwable){
            throw throwable;
        }finally {
            log.info("Admin--->{},date--->{},thing--->{},result--->{}", admin.getUsername(), date, name, result);
            Logentity logentity = new Logentity(UUID.randomUUID().toString(), name, admin.getUsername(), date, result);
            logService.save(logentity);
        }
    }


    @Pointcut(value = "@annotation(com.baizhi.wangh.controller.Log)")
    public void pt(){

    }
}
