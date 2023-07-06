package project.cnu.daehakro.domain.chat.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ChatRoomService {
    void createChatRoom(String key);

    void deleteRooms(String key);
}
