package simple.auth.boot.filter;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import simple.auth.boot.service.SabAccessDecisionManager;
import simple.auth.boot.service.SabSourceService;


@Component
public class SabSecurityInterceptor extends FilterSecurityInterceptor{

	@Resource(name = "sabSourceService")
    private SabSourceService sourceService;

    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    @Resource(name = "sabAccessDecisionManager")
    private SabAccessDecisionManager accessDecisionManager;

    @PostConstruct
    public void init() {
        super.setAuthenticationManager(authenticationManager);
        super.setAccessDecisionManager(accessDecisionManager);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        if (SecurityContextHolder.getContext().getAuthentication() == null || null == fi || fi.getRequestUrl().contains(".js")
                || fi.getRequestUrl().contains(".css")) {
            chain.doFilter(request, response);
        } else {
            invoke(fi);
        }
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return sourceService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

}
