package project.cnu.daehakro.domain.chat.controller;

/**
 * admin 권한으로만 채팅방 모두 생성, 랜덤 매칭 가능 지금은 비밀 키를 입력받지만
 */

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.cnu.daehakro.domain.chat.dto.ApplyInfoDto;
import project.cnu.daehakro.domain.chat.service.AdminService;
import project.cnu.daehakro.domain.chat.service.ChatRoomService;

/**
 * ROLE_ADMIN 권한 security 적용해야함
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final ChatRoomService chatRoomService;
    private final AdminService adminService;

    @DeleteMapping("/room")
    public ResponseEntity<?> deleteAllRooms(@RequestParam String key) {
        chatRoomService.deleteRooms(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/apply-info")
    public ResponseEntity<ApplyInfoDto> applyStatus() {
        return null;
    }

    @PostMapping("/matching")
    public ResponseEntity<?> matching(@RequestParam String key) {
        chatRoomService.createChatRoom(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
