package project.cnu.daehakro.domain.chat.controller;

/**
 * admin 권한으로만 채팅방 모두 생성, 랜덤 매칭 가능 지금은 비밀 키를 입력받지만
 */

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.cnu.daehakro.domain.chat.service.ChatRoomService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin/chat")
public class AdminController {

    private final ChatRoomService chatRoomService;
    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<?> createRooms(@RequestParam String key) {
        chatRoomService.createChatRoom(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //
    @DeleteMapping("/room")
    public ResponseEntity<?> deleteAllRooms(@RequestParam String key) {
        chatRoomService.deleteRooms(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
