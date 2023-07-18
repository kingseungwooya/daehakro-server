package project.cnu.daehakro.domain.chat.controller;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.cnu.daehakro.domain.chat.dto.EventDto;
import project.cnu.daehakro.domain.chat.dto.EventResDto;
import project.cnu.daehakro.domain.chat.dto.UnivInfoDto;
import project.cnu.daehakro.domain.chat.service.AdminService;
import project.cnu.daehakro.domain.chat.service.ChatRoomService;
import project.cnu.daehakro.domain.chat.service.UnivService;

import java.util.List;

/**
 * ROLE_ADMIN 권한 security 적용해야함
 * admin 권한으로만 채팅방 모두 생성, 랜덤 매칭 가능
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final ChatRoomService chatRoomService;
    private final AdminService adminService;
    private final UnivService univService;

    @DeleteMapping("/room")
    public ResponseEntity<?> deleteAllRooms(@RequestParam String key) {
        chatRoomService.deleteRooms(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/matching")
    public ResponseEntity<?> matching(@RequestParam Long eventId) {
        adminService.randomMatch(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/team-matching")
    public ResponseEntity<?> teamMatching(@RequestParam Long eventId) {
        adminService.randomTeamMatch(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/univ")
    public ResponseEntity<List<UnivInfoDto>> univInfo() {
        return new ResponseEntity<>(univService.getUnivInfos(), HttpStatus.OK);
    }

    @GetMapping("/event")
    public ResponseEntity<List<EventResDto>> eventInfo() {
        return new ResponseEntity<>(adminService.getAllEvents(), HttpStatus.OK);
    }

    @PutMapping("/event/close")
    public ResponseEntity<?> closeEvent(@RequestParam Long eventId) {
        adminService.closeEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/event")
    public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {
        adminService.createEvent(eventDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
