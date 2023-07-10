package project.cnu.daehakro.domain.chat.service;

import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.ChatMessageDto;
import project.cnu.daehakro.domain.chat.dto.ChatRequestDto;
import project.cnu.daehakro.domain.chat.dto.ChatRoomDto;
import project.cnu.daehakro.domain.entity.Member;

@Service
public interface ChatService {

    ChatRoomDto roomDetail(String memberId, Long roomId);

    ChatRoomDto sendMessage(ChatMessageDto message);
}
