package hello.exception.api;

import hello.exception.dto.MemberDto;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    /*
    컨트롤러 내부에서 에러발생시 공통으로 처리한다.
    컨트롤러 에러발생 -> 익셉션 리졸버 -> 처리후 정상응답
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 상태코드를 바꾸지 않으면 기본적으로 200이 리턴된다.
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illeageExHandler(IllegalArgumentException e) { // IllegalArgumentException 자식 예외까지 잡힌다
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage()); // 상태코드를 바꾸지 않으면 200리턴
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) { // @ExceptionHandler(UserException.class) 생략
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("BAD", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) { // 자식예외로 처리되지 않은 예외는 여기서 처리된다.
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부오류");
    }

    @GetMapping("/api2/members/{id}")
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
}
