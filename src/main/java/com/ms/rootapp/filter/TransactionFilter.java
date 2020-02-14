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


        if (requestURL.endsWith(".io/") || requestURL.endsWith("health/") || requestURL.endsWith("health")){

        }else{
            res.sendRedirect(requestURL="/");
            return;
        }
//        if (requestURL.endsWith("health/")){
//        }else{
//            if (!requestURL.endsWith(extension+"/")) {
//
////                HttpServletRequest reque = (HttpServletRequest) req;
////                HttpSession mySession = requestURL.getSession();
////                //get the url to submit the save params
////                String saveUrl = (String) requestURL.getParameter("saveUrl");
////                System.out.println(saveUrl);
////                RequestDispatcher x = reque.getRequestDispatcher(requestURL );
//                //run the save
////                x.forward(reque, res);
////                res.sendRedirect(requestURL + "/");
////                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
////                httpResponse.sendRedirect(req.getContextPath() + requestURL + "/");
//
//                res.sendRedirect(requestURL);
//                return;
////                res.setHeader("Location", requestURL + "/");
////                res.setStatus(302);
//            }
//        }
        filterChain.doFilter(req, res);

    }

    // other methods
}