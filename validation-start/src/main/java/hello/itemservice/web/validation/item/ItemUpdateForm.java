package hello.itemservice.web.validation.item;

import hello.itemservice.domain.item.Item;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 100000)
    private Integer price;

    private Integer quantity;

    public Item toEntity() {
        Item item = new Item();
        item.setId(id);
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        return item;
    }
}
