package hello.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {

    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable{
        //시간 로직
        long start=System.currentTimeMillis();

        System.out.println("START: "+ joinPoint.toString());

        try{
            return joinPoint.proceed();
        }finally {
            long finish =System.currentTimeMillis();
            long timeMs= finish-start;

            System.out.println("END: "+joinPoint.toString()+" "+timeMs+"ms");
        }
    }
}
