package project.cnu.daehakro.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.repository.ChatRoomRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    @Override
    public void createChatRoom(String key) {
        // sorting 알고리즘.. 어케할건데..
        // random sorting

        chatRoomRepository.deleteAll();
        // 1단계 관리자 확인 인증
    }

    @Override
    public void deleteRooms(String key) {
        // 1단계 관리자 확인 인증
        chatRoomRepository.deleteAll();
    }
}
