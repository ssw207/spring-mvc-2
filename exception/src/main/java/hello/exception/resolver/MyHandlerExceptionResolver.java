package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("CALL");

        try {
            if (ex instanceof IllegalArgumentException) { // 400에러로 바꿔서 보낸다
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView(); // WAS는 정상흐름으로 판단
            }

        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null; // null 반환시 기본적으로 다음 exception resolver를 호출하고 더이상 resolver가 없을때 기존에 발생한 에러가 was까지 넘어간다.
    }
}
