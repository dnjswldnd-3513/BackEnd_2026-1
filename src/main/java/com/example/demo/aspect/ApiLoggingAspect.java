package com.example.demo.aspect;


import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class ApiLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(ApiLoggingAspect.class);

    @Around("execution(* com.example.demo.controller..*(..))")
    public Object logEx(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        long start = System.currentTimeMillis();
        try {
            return  joinPoint.proceed();
        }finally {
            long as = System.currentTimeMillis() - start;
            log.info("[{}] {} - {}ms",
                    request.getMethod(), request.getRequestURI(), as);
        }
    }
}
