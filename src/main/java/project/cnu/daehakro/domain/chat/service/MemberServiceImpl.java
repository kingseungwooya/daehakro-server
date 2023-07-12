package project.cnu.daehakro.domain.chat.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.MemberApplyForm;
import project.cnu.daehakro.domain.chat.repository.EventRepository;
import project.cnu.daehakro.domain.chat.repository.MemberRepository;
import project.cnu.daehakro.domain.entity.Event;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.enums.ResponseEnum;
import project.cnu.daehakro.domain.handler.CustomApiException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    /**
     * 검증필요
     * 인증된 사용자인가?
     * 쿠폰을 사용하였는가?
     * 결제를 하였는가?
     * 신청기간이 지났는가?
     */

    @Override
    public void applyEvent(MemberApplyForm applyForm) {
        Event event = eventRepository.findById(applyForm.getEventId()).orElseThrow(
                () -> new CustomApiException(ResponseEnum.EVENT_NOT_EXIST)
        );
        memberRepository.findById(applyForm.getMemberId());
        Member member = memberRepository.findById(applyForm.getMemberId()).orElseThrow(
                () -> new CustomApiException(ResponseEnum.USER_NOT_FOUND)
        );
        event.apply(member);
    }


}
