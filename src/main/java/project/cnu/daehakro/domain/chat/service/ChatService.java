package project.cnu.daehakro.domain.chat.service;

import org.springframework.stereotype.Service;

import project.cnu.daehakro.domain.chat.dto.ChatMessageDto;
import project.cnu.daehakro.domain.chat.dto.ChatRoomDto;


@Service
public interface ChatService {

    ChatRoomDto roomDetail(String memberId, Long roomId);

    ChatRoomDto sendMessage(ChatMessageDto message);
}
