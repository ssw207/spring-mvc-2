package hello.exception.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String id;
    private String name;

    public MemberDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
