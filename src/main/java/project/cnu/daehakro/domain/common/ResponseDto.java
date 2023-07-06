package project.cnu.daehakro.domain.common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.cnu.daehakro.domain.enums.ResponseEnum;

@Builder
@AllArgsConstructor
@Getter
public class ResponseDto<T> {

    private final Integer code;
    private final String message;
    private final T content;

    public ResponseDto(ResponseEnum respEnum, T content) {
        this.code = respEnum.getCode();
        this.message = respEnum.getMessage();
        this.content = content;
    }

    public ResponseDto(ResponseEnum respEnum) {
        this.code = respEnum.getCode();
        this.message = respEnum.getMessage();
        this.content = null;
    }
}