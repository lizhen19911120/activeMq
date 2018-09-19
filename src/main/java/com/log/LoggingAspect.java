package com.log;

import com.Enum.LogActionTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
//@Component
public class LoggingAspect {

    public static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    private final Environment env;

    public LoggingAspect(Environment env) {
        this.env = env;
        System.out.println("-------------LoggingAspect created------------");
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("execution(* com.lizhen.*.*(..))")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before("applicationPackagePointcut()")
    public void before(JoinPoint joinPoint) {
        LOG.info("Target class: {} | target method: {} |actionType: {} | detail: params are {}", joinPoint.getTarget(), joinPoint.getSignature().getName(), LogActionTypeEnum.Enter.getMsg(),
                Arrays.asList(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "ret")
    public void after(JoinPoint joinPoint, Object ret) {
        LOG.info("Target class: {} | target method: {} | actionType: {} | detail: Retval is {}", joinPoint.getTarget(),joinPoint.getSignature().getName(), LogActionTypeEnum.Leave.getMsg(), ret);
    }
}
