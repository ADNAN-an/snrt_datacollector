package com.snrt.datacollector.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter("/*")
public class CorsFilter implements Filter  {
	
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
	
	public CorsFilter() {
		
    }


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, PATCH, DELETE");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    response.setHeader("Access-Control-Expose-Headers", "Authorization");
	    response.addHeader("Access-Control-Expose-Headers", "USERID");
	    response.addHeader("Access-Control-Expose-Headers", "ROLE");
	    response.addHeader("Access-Control-Expose-Headers", "responseType");
	    response.addHeader("Access-Control-Expose-Headers", "observe");
	    
	    if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
	          try {
	              chain.doFilter(req, res);
	          } catch(Exception e) {
	              e.printStackTrace();
	          }
	      } else {
	          response.setHeader("Access-Control-Allow-Origin", "*");
	          response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT,PATCH");
	          response.setHeader("Access-Control-Max-Age", "3600");
	          response.setHeader("Access-Control-Allow-Headers", "Access-Control-Expose-Headers"+"Authorization, content-type," +
	          "USERID"+"ROLE"+
	                  "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with,responseType,observe");
	          response.setStatus(HttpServletResponse.SC_OK);
	      }
	}
	
	@Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }


}
