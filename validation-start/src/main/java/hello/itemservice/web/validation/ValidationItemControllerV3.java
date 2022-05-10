package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    /*
    @Validated 적용시 어노테이션 기반의 BeanValidator가 동작한다.
    1.@ModelAttribute 각각의 필드에 맞는 타입으로 변환
      1. 성공하면 다음
      2. 실패하면 typeMismatch로 FieldError에 추가 -> 타입미스매치사 사전에 정의해준 propertis의 메시지가 출력됨
    2. Validator 적용
       1) @NotBlank 적용시 아래순서대로 메시지를 찾는다.
       - NotBlank.item.itemName
       - NotBlank.itemName
       - NotBlank.java.lang.String
       2) 어노테이션의 message 속성을 사용
       3) 라이브러리가 제공하는 기본값 사용
     */
    //@PostMapping("/add")
    public String addItemV1 (@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice <= 100000) {
                //글로벌 에러는 ObjectError 객체로 세팅
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증 실패시 처리
        if (bindingResult.hasErrors()) {
            //bindingResult는 자동으로 모델에 담기기 때문에 모델에 따로 담을 필요없다.
            log.info("error={} ", bindingResult);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    /*
    @Validated(SaveCheck.class) : Validated 적용시 SaveCheck가 표시된 필드만 동작한다
     */
    @PostMapping("/add")
    public String addItemV2 (@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice <= 100000) {
                //글로벌 에러는 ObjectError 객체로 세팅
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증 실패시 처리
        if (bindingResult.hasErrors()) {
            //bindingResult는 자동으로 모델에 담기기 때문에 모델에 따로 담을 필요없다.
            log.info("error={} ", bindingResult);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    //@GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, @Validated @ModelAttribute Item item, BindingResult bindingResult, Model model) {

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice <= 100000) {
                //글로벌 에러는 ObjectError 객체로 세팅
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증 실패시 처리
        if (bindingResult.hasErrors()) {
            //bindingResult는 자동으로 모델에 담기기 때문에 모델에 따로 담을 필요없다.
            log.info("error={} ", bindingResult);
            return "validation/v3/addForm";
        }

        model.addAttribute("item", itemRepository.findById(itemId));
        return "validation/v3/editForm";
    }

    @GetMapping("/{itemId}/edit")
    public String editFormV2(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult, Model model) {

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice <= 100000) {
                //글로벌 에러는 ObjectError 객체로 세팅
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증 실패시 처리
        if (bindingResult.hasErrors()) {
            //bindingResult는 자동으로 모델에 담기기 때문에 모델에 따로 담을 필요없다.
            log.info("error={} ", bindingResult);
            return "validation/v3/addForm";
        }

        model.addAttribute("item", itemRepository.findById(itemId));
        return "validation/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}

