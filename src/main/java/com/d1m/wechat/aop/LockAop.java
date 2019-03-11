package com.d1m.wechat.aop;

import com.d1m.wechat.anno.RedisLock;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.util.DistributedLock;
import com.d1m.wechat.util.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by jone.wang on 2019/3/1.
 * Description:
 */
@Component
@Aspect
public class LockAop {

    @Autowired
    private DistributedLock distributedLock;

    @Around(value = "@annotation(redisLock)")
    public Object lock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {

        final String elKey = redisLock.key();
        final Method method = this.getMethod(joinPoint);
        final String key = this.parseKey(elKey, method, joinPoint.getArgs());
        try {
            final boolean lock = distributedLock.lock(key, redisLock.timeUnit().toMillis(redisLock.expire()), redisLock.retryTimes(), redisLock.sleep());
            if (lock) {
                return joinPoint.proceed(joinPoint.getArgs());
            }
            throw new WechatException(Message.JOB_RUNNING);
        } finally {
            distributedLock.releaseLock(key);
        }
    }


    /**
     * 获取被拦截方法对象
     * <p>
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     * 而缓存的注解在实现类的方法上
     * 所以应该使用反射获取当前对象的方法对象
     */
    private Method getMethod(ProceedingJoinPoint pjp) {
        //获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return method;

    }


    /**
     * @param key    SPEL 表达获取真正key
     * @param method 拦截方法
     * @param args   方法的具体参数
     * @return 最终key
     */
    private String parseKey(String key, Method method, Object[] args) {


        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        if (StringUtils.isEmpty(key) || ObjectUtils.isEmpty(paraNameArr)) {
            return key;
        }

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

}
