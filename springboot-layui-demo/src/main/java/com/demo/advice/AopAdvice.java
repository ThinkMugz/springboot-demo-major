package com.demo.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/6/23 17:59
 * @Description TODO
 */
@Aspect
@Component
public class AopAdvice {
    // 定义一个切点：所有被GetMapping注解修饰的方法会织入advice
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void getAdvicePointcut() {}

    @Pointcut("execution(* com.demo.service.impl..PersonServiceImpl.*(..)))")
    private void personAdvicePointcut() {}

    // Before表示logAdvice将在目标方法执行前执行
    @Before("getAdvicePointcut()")
    public void getAdvice(){
        // 这里只是一个示例，你可以写任何处理逻辑
        System.out.println("get请求的advice触发了");
    }

    @Before("personAdvicePointcut()")
    public void personAdvice(){
        // 这里只是一个示例，你可以写任何处理逻辑
        System.out.println("personServiceImpl的advice触发了");
    }
}
