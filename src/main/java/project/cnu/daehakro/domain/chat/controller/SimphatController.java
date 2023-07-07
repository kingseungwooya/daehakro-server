package project.cnu.daehakro.domain.chat.controller;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import project.cnu.daehakro.domain.chat.dto.ChatRequestDto;
import project.cnu.daehakro.domain.chat.dto.ChatRoomDto;
import project.cnu.daehakro.domain.chat.dto.MemberDto;
import project.cnu.daehakro.domain.chat.service.ChatService;
import project.cnu.daehakro.domain.common.ResponseDto;
import project.cnu.daehakro.domain.enums.ResponseEnum;

@RestController
@Slf4j
@RequestMapping("/api/chat")
@AllArgsConstructor
public class SimphatController {

    private final SimpMessagingTemplate messageTemplate;

    private final ChatService chatService;


    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> roomDetail(String memberId, @PathVariable @NotNull Long roomId) {
        ChatRoomDto roomDto = chatService.roomDetail(memberId, roomId);
        return new ResponseEntity<>(new ResponseDto<>(ResponseEnum.CHAT_ROOM_DETAIL_SUCCESS, roomDto), HttpStatus.OK);
    }

    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.
    // 프론트에서 첫 채팅메시지를 받을 때 방이 없다면 그 때 http 요청을 해서 방을 받아오는 도중 두번째메시지가 온다면 꼬일위험 many! => room 리턴
    @MessageMapping("/chat.send") // 요청 엔드포인트 /app/chat.send
    // @SendTo("/queue/pub") // 이건 pub/ sub구조를 쓸떈 안쓴다 SimpMessaging Temp 에서만 씀
    public void sendMessage(@Payload ChatRequestDto message) {
        /*
         * 특정 유저에게만 보내기!
         * 구독할 때는 /user/topic/pub
         */
        // 지금은 특저 유저들에게 보내는 구조 topic으로 해야함
        String destination = "/queue/pub";
        ChatRoomDto chat = chatService.sendMessage(message);
        for (MemberDto member : chat.getMembers()) {
            if (!message.getMemberId().equals(member.getMemberId())) {
                messageTemplate.convertAndSendToUser(member.getMemberId(), destination, chat);
            }
        }

    }

}
