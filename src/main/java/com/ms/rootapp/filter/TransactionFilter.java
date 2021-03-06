package com.ms.rootapp.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(1)
public class TransactionFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TransactionFilter.class.getName());
    @Value("${domain.extension}")
    private String extension;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String requestURL = String.valueOf(req.getRequestURL());


        logger.info(
                "Starting a transaction for req : {}"+
                req.getRequestURI());

        logger.info(
                "Committing a transaction for req : {}"+
                req.getRequestURI());


        if (requestURL.endsWith(extension) || requestURL.endsWith("health/") || requestURL.endsWith("health") || requestURL.endsWith("//")){

        }else{
            res.sendRedirect(requestURL+"/");
            return;
        }

        filterChain.doFilter(req, res);

    }

    // other methods
}