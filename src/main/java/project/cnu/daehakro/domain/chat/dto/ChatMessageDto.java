package project.cnu.daehakro.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.cnu.daehakro.domain.entity.ChatMessage;
import project.cnu.daehakro.domain.enums.ChatMessageType;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private Long messageId;

    private ChatMessageType messageType;

    private String content;

    private Timestamp createAt;

    private String sender;

    private Long roomId;

    public ChatMessageDto(ChatMessage chat){
        this.messageId = chat.getMessageId();
        this.messageType = chat.getMessageType();
        this.content = chat.getContent();
        this.createAt = chat.getCreateAt();
        this.sender = chat.getMember().getMemberName();
        this.roomId = chat.getChatRoom().getRoomId();
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}