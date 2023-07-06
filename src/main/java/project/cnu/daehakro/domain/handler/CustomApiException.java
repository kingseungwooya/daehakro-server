package project.cnu.daehakro.domain.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.cnu.daehakro.domain.enums.ResponseEnum;

@Getter
@RequiredArgsConstructor
public class CustomApiException extends RuntimeException{

    private final ResponseEnum responseEnum;

}