package hello.exception.api;

import hello.exception.dto.MemberDto;
import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if ("ex".equals(id)) {
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @GetMapping("/api/response-status-ex1")
    public void statusEx1() {
        throw new BadRequestException();
    }

    @GetMapping("/api/response-status-ex2")
    public void statusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException()); // 에러코드, 메시지, 에러
    }

    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        // 스프링에서 타입이 잘못되면 DefaultHandlerExceptionResolver에서 500 -> 400에러로 기본적으로 바꿔서 전달한다.
        return "ok";
    }

}
