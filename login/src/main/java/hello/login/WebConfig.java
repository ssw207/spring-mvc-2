package hello.login;

import hello.login.filter.LogCheckFilter;
import hello.login.filter.LogFilter;
import hello.login.interceptor.LogInterceptor;
import hello.login.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
    public FilterRegistrationBean logFilter() { // WAS를 띄울때 부트가 필터를 넣어준다
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1); // 필터순서
        filterRegistrationBean.addUrlPatterns("/*"); //모든 URL에 적용한다
        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean logCheckFilter() { // WAS를 띄울때 부트가 필터를 넣어준다
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogCheckFilter());
        filterRegistrationBean.setOrder(1); // 필터순서
        filterRegistrationBean.addUrlPatterns("/*"); //모든 URL에 적용한다
        return filterRegistrationBean;
    }

    // 인터셉터 추가
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") // 서블릿과 패턴이 다르므로 주의할것
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 특정경로를 제외할 경우

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico","error");
    }
}
