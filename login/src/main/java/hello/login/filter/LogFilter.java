package hello.login.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter { // 필터라르 사용하려면 Filter인터페이스를 구현해야하며 필터를 등록해야 적용된다.
    
    /** 요청이 들어올때마다 실행되는 영역 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter"); //요창마다 호출됨

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response); // 다음필터 실행. 다음필터가 없으면 서블릿이 실행된다.
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI); // 서블릿처리가 끝나면 response가 출력된다.
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init");
    }

    @Override
    public void destroy() {
        log.info("destory");
    }
}
