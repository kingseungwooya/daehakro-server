package project.cnu.daehakro.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.cnu.daehakro.domain.entity.ChatMessage;
import project.cnu.daehakro.domain.entity.ChatRoom;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {

    private Long roomId;

    private MemberDto host;

    private List<MemberDto> members;

    private List<ChatMessageDto> messages;

    private Timestamp createAt;

    public ChatRoomDto(ChatRoom room){
        this.roomId = room.getRoomId();
        if(room.getMembers() != null) {
            this.members = room.getMembers()
                    .stream()
                    .map(MemberDto::new)
                    .collect(Collectors.toList());
        }
        if(room.getMessages() !=null){
            this.messages = room.getMessages()
                    .stream()
                    .map(ChatMessageDto::new)
                    .collect(Collectors.toList());
        }
        this.createAt = room.getCreateAt();
    }

    public ChatRoomDto(ChatRoom room, ChatMessage lastMessage){
        this.roomId = room.getRoomId();
        this.messages = List.of(new ChatMessageDto(lastMessage));
        this.createAt = room.getCreateAt();
    }

}