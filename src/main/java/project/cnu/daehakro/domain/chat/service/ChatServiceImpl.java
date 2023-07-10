package project.cnu.daehakro.domain.chat.service;

import project.cnu.daehakro.domain.chat.dto.ChatMessageDto;
import project.cnu.daehakro.domain.chat.dto.ChatRequestDto;
import project.cnu.daehakro.domain.chat.dto.ChatRoomDto;
import project.cnu.daehakro.domain.chat.repository.ChatMessageRepository;
import project.cnu.daehakro.domain.chat.repository.ChatRoomRepository;
import project.cnu.daehakro.domain.chat.repository.MemberRepository;
import project.cnu.daehakro.domain.entity.ChatMessage;
import project.cnu.daehakro.domain.entity.ChatRoom;
import project.cnu.daehakro.domain.entity.Member;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.enums.ResponseEnum;
import project.cnu.daehakro.domain.handler.CustomApiException;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final MemberRepository memberRepository;

    @Override
    public ChatRoomDto roomDetail(String memberId, Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(
                        () -> new CustomApiException(ResponseEnum.CHAT_ROOM_NOT_EXIST)
                );
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new CustomApiException(ResponseEnum.USER_NOT_FOUND)
                );
        checkRoomPermission(chatRoom, member);

        return new ChatRoomDto(chatRoom);
    }

    private void checkRoomPermission(ChatRoom chatRoom, Member member) {
        List<Member> members = chatRoom.getMembers();
        if (!members.contains(member)) {
            throw new CustomApiException(ResponseEnum.CHAT_ROOM_NOT_PERMISSION);
        }
    }

    @Override
    public ChatRoomDto sendMessage(ChatMessageDto message) {
        Member member = memberRepository.findById(message.getMemberId())
                .orElseThrow(
                        () -> new CustomApiException(ResponseEnum.USER_NOT_FOUND)
                );
        ChatRoom chatRoom = chatRoomRepository.findById(message.getRoomId())
                .orElseThrow(
                        () -> new CustomApiException(ResponseEnum.CHAT_ROOM_NOT_EXIST)
                );
        ChatMessage chatMessage = ChatMessage.builder()
                .content(message.getContent())
                .chatRoom(chatRoom)
                .member(member)
                .build();
        chatMessageRepository.save(chatMessage);

        return new ChatRoomDto(chatRoom, chatMessage);
    }
}
