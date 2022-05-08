package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesREsolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    
    @Test
    public void messageCodesREsolverObject() throws Exception {
        //given
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        //when
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }

        //then
        assertThat(messageCodes).containsExactly("required.item", "required");
    }


    @Test
    public void messageCodesResolverField() throws Exception {
        //given
        // 디테일한 순서대로 나온다.
        String[] codes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);

        //when
        for (String code : codes) {
            System.out.println("code = " + code);
        }

        //BindingResult.rejectValue("itemName", "rquired") -> rejectValue 내부에서 codesResolver를 호출하고 new FieldError 생성
        //then

        assertThat(codes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
