package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz); // 자식과 부모 모두 체크하기 위함
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        /*
        if (!StringUtils.hasText(item.getItemName())) {
            // objectName은 이미 알고있다
            // 먼저 required.item.itemName을 가져온다. 없으면 이름인 required 인 값을 찾는다.
            // message codes resolver
            bindingResult.rejectValue("itemName", "required");
        }
         */

        if (item.getPrice() == null || item.getPrice() != null && (item.getPrice() < 1000 || item.getPrice() > 100000)) {
            errors.rejectValue("price", "range", new Object[] {1000, 100000}, null);
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice <= 100000) {
                //글로벌 에러는 ObjectError 객체로 세팅
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
