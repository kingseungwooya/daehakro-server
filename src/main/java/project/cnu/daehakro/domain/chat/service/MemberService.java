package project.cnu.daehakro.domain.chat.service;

import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.MemberApplyForm;

@Service
public interface MemberService {

    // 이벤트 신청하기
    void applyEvent(MemberApplyForm applyForm);
}
