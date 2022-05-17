package hello.login;

import hello.login.filter.LogCheckFilter;
import hello.login.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() { // WAS를 띄울때 부트가 필터를 넣어준다
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1); // 필터순서
        filterRegistrationBean.addUrlPatterns("/*"); //모든 URL에 적용한다
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean logCheckFilter() { // WAS를 띄울때 부트가 필터를 넣어준다
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogCheckFilter());
        filterRegistrationBean.setOrder(1); // 필터순서
        filterRegistrationBean.addUrlPatterns("/*"); //모든 URL에 적용한다
        return filterRegistrationBean;
    }
}
