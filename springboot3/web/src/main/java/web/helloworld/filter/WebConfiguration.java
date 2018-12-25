package web.helloworld.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 配置类
 * 
 * @author DINGJUN
 *
 */
@Configuration
public class WebConfiguration {
	// 配置一个对象
	@Bean
	public RemoteIpFilter remoteIpFilter() {
		return new RemoteIpFilter();
	}

	// 配置一个
	@Bean
	public FilterRegistrationBean<Filter> testFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(new MyFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("myFilter");
		registration.setOrder(1);
		return registration;
	}

	public class MyFilter implements Filter {
		@Override
		public void destroy() {
			System.out.println("myfilter destory");
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain chain) throws IOException, ServletException {
			HttpServletRequest req = (HttpServletRequest) request;
			System.out.println("this is myfilter,url:" + req.getRequestURL());
			chain.doFilter(request, response);
		}

		@Override
		public void init(FilterConfig arg0) throws ServletException {
			System.out.println("myfilter init");
		}
	}
}
