package project.cnu.daehakro.domain.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.ApplyInfoDto;
import project.cnu.daehakro.domain.chat.repository.ChatRoomRepository;
import project.cnu.daehakro.domain.chat.repository.MemberRepository;
import project.cnu.daehakro.domain.chat.repository.UnivRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UnivRepository univRepository;

    @Override
    public ApplyInfoDto applyStatus() {
        return null;
    }

    @Override
    public void randomMatch() {

    }
}
