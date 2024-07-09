package com.study8.sys.security;

import com.study8.core.util.CoreLanguageUtils;
import com.study8.sys.config.SettingVariable;
import com.study8.sys.constant.ExceptionConstant;
import com.study8.sys.util.ResourceBundleUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * RateLimitFilter
 * @Date: 2024-07-09
 * @Author: HuyNH
 * @Desc: Fighting DDoS Attacks
 */
@Component
@Order(SettingVariable.PRIORITY_1)
@Slf4j
public class RateLimitFilter implements Filter {
    private final ConcurrentHashMap<String, AtomicLong> requestCount = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Locale locale = CoreLanguageUtils.getLanguageFromHeader((HttpServletRequest) request);
        String ipAddress = request.getRemoteAddr();
        AtomicLong count = requestCount.computeIfAbsent(ipAddress, k -> new AtomicLong());
        if (count.incrementAndGet() > SettingVariable.RATE_LIMIT) {
            log.info("Blocked request from IP: " + ipAddress + ", URI: " + ((HttpServletRequest) request).getRequestURI());
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            httpResponse.getWriter().write(ResourceBundleUtils.getMessage(
                    ExceptionConstant.EXCEPTION_SECURITY_RATE_LIMIT_EXCEEDED, locale));
            return;
        }
        chain.doFilter(request, response);
    }
}
