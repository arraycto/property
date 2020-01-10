package com.mlb.orderserviceconsumer.Interceptor;

import com.mlb.userserviceprovider.common.TokenUse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author mlb
 */
public class SessionInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        String Token = request.getHeader("accessToken");
        //Token不存在
        if(Token != null){
            //验证Token是否正确
            boolean result = TokenUse.tokenVerify(Token);
            if(result){
                logger.info(new Date()+"登陆成功");
                return true;
            }
        }
        System.out.println("123123123");
        logger.info("认证失败，未通过拦截器");
        return false;
    }
}
