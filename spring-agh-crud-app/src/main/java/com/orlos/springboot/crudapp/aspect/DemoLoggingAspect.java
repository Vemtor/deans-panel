package com.orlos.springboot.crudapp.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {
    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.orlos.springboot.crudapp.controller.*.*(..))")
    private void forControllerPackage(){
    }

    @Pointcut("execution(* com.orlos.springboot.crudapp.service.*.*(..))")
    private void forServicePackage(){
    }

    @Pointcut("execution(* com.orlos.springboot.crudapp.dao.*.*(..))")
    private void forDaoPackage(){
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){
    }

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint){
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("<<<INFO>>> calling method" + theMethod);

        Object[] args = theJoinPoint.getArgs();

        for(Object tempArg: args){
            myLogger.info("<<<<<INFO>>>>> argument" + tempArg);
        }
    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult){
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("<<<INFO>>> Return from method: " + theMethod);
        myLogger.info("<<<INFO>>> Result from method: " + theResult);
    }

}
