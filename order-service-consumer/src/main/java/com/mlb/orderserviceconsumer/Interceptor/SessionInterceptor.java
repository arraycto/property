package com.mlb.orderserviceconsumer.Interceptor;

import com.mlb.userserviceprovider.common.TokenUse;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mlb
 */
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        String Token = request.getHeader("accessToken");
        //Token不存在
        if(Token != null){
            //验证Token是否正确
            boolean result = TokenUse.tokenVerify(Token);
            if(result){
                return true;
            }
        }
        return false;
    }
}
