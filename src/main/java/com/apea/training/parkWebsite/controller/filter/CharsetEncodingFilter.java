package com.apea.training.parkWebsite.controller.filter;

import com.apea.training.parkWebsite.controller.AppAssets;

import javax.servlet.*;
import java.io.IOException;

public class CharsetEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(AppAssets.getInstance().get("CHARACTER_ENCODING"));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
