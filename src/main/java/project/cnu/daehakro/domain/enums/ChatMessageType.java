package project.cnu.daehakro.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HOST 한 명 설정 그리고 나머지는 SENDER
 */
@Getter
@AllArgsConstructor
public enum ChatMessageType {
    SENDER_TO_HOST,
    HOST_TO_SENDER;
}