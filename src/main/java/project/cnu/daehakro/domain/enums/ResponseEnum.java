package project.cnu.daehakro.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

    // AUTH_BAD_REQUEST(403, "bad request"),
    // AUTH_INVALID_TOKEN(401, "invalid token"),
    // AUTH_NOT_JOINED(405, "not joined user"),
    // AUTH_REFRESH_DOES_NOT_EXIST(401, "REFRESH_DOES_NOT_EXIST"),
    // AUTH_REFRESH_EXPIRED(401, "AUTH_REFRESH_EXPIRED"),

    USER_USERNAME_CK_SUCCESS(200, "사용가능한 아이디입니다."),
    USER_USERNAME_CK_FAIL(500, "사융할 수 없는 아이디입니다."),

    USER_JOIN_SUCCESS(200, "회원가입에 성공하였습니다."),
    USER_JOIN_FAIL(500, "다시 시도해주세요."),

    USER_MY_INFO_SUCCESS(200,"조회 성공"),
    USER_NOT_FOUND(401,"회원이 존재하지 않습니다"),


    CHAT_ROOM_MAKE_SUCCESS(200, "생성 성공"),
    CHAT_ROOM_MAKE_EXIST(-1,"이미 존재하는 채팅방 입니다"),
    CHAT_ROOM_CAN_NOT_TO_ME(-1,"자기 자신에게는 보낼 수 없습니다"),

    CHAT_ROOM_LIST_SUCCESS(200, "조회 성공"),

    CHAT_ROOM_DETAIL_SUCCESS(200, "조회 성공"),
    CHAT_ROOM_NOT_EXIST(-1,"존재하지 않는 채팅방입니다"),
    CHAT_ROOM_NOT_PERMISSION(-1,"접근할 수 없는 채팅방입니다");


    private final int code;
    private final String message;

}