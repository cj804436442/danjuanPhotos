package com.example.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1.获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // 从header没拿到从请求参数里拿试试
            token = request.getParameter("token");
        }
        // 2.开始执行认证
        if (StrUtil.isBlank(token)) {
            throw new CustomException("无token，请重新登录");
        }

        // 获取token
        String userId;
        Admin admin;
        try {
            // 解码token
            userId = JWT.decode(token).getAudience().get(0);
            // 根据token中的userId查询数据库
            admin = adminService.findById(Integer.parseInt(userId));
        } catch (Exception e) {
            String errMsg = "token验证失败，请重新登录";
            log.error(errMsg + ",token=" + token,e);
            throw new CustomException(errMsg);
        }
        if (admin == null) {
            throw new CustomException("用户不存在，请重新登录");
        }
        try {
            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new CustomException("token验证失败，请重新登录");
        }
        log.info("token验证成功");
        return true;
    }

}
