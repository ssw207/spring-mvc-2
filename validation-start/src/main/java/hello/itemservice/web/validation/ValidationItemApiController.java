package hello.itemservice.web.validation;

import hello.itemservice.web.validation.item.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    //RequestBody는 필드단위로 검증하는게 아니라 객체를 한번에 만들기 때문에 HttpMessageConverter에서 에러가 발생하면 컨트롤러가 호출되지 않는다.
    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
        log.info("api 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            return  bindingResult.getAllErrors(); // objectError, fieldError 모두 반환한다.
        }

        log.info("성공 로직실행");
        return form;
    }
}
