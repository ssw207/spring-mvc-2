package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class); // 컨트롤러 호출전  파라미터에 어노테이션이 있느지 확인
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType()); // 타입을 체크

        return hasParameterAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument");

        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null; // Member 객체 null
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
