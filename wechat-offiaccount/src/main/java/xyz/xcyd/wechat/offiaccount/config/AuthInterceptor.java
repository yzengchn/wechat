package xyz.xcyd.wechat.offiaccount.config;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权
 */
public class AuthInterceptor implements HandlerInterceptor, SmartInitializingSingleton {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("SmartInitializingSingleton");
    }
}
