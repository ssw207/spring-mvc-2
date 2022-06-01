package hello.exception.interceptor.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@ControllerAdvice(assignableTypes = {ApiExceptionV2Controller.class}) // 특정 클래스 직접지정
//@ControllerAdvice("org.example") // 특정패키지 하위에서 에러가 발생한경우
//@ControllerAdvice(annotations = RestController.class) // RestControlelr 어노테이션이 붙은 클래스 에러가 났을때만 적용
@RestControllerAdvice // 여러 컨트롤러에서 발생한 오류를 처리해줌, @ExceptionHandler, @InitBinder 기능을 부여해준다. 대상을 지정안하면 글로벌하게 적용됨
public class ExControllerAdvice {

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

}
