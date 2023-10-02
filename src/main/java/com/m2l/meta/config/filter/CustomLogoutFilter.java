//package com.m2l.meta.config.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class CustomLogoutFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if(request.getServletPath().equals("/logout")) {
//            HttpSession session = request.getSession(false);
//            if(session != null) {
//                session.invalidate();
//            }
//            SecurityContext context = SecurityContextHolder.getContext();
//            if(context != null) {
//                SecurityContextHolder.clearContext();
//                context.setAuthentication(null);
//            }
//            String redirectUri = request.getParameter("redirect_uri");
////            if(Utils.isEmpty(redirectUri)) {
////                redirectUri = request.getHeader("referer");
////            }
////            if(Utils.notEmpty(redirectUri)) {
////                response.sendRedirect(redirectUri);
////                return;
////            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}
