package project.cnu.daehakro.domain.chat.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.MemberApplyForm;
import project.cnu.daehakro.domain.chat.dto.TeamApplyForm;
import project.cnu.daehakro.domain.chat.repository.EventRepository;
import project.cnu.daehakro.domain.chat.repository.MemberRepository;
import project.cnu.daehakro.domain.entity.Event;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.enums.MemberSex;
import project.cnu.daehakro.domain.enums.ResponseEnum;
import project.cnu.daehakro.domain.handler.CustomApiException;

import java.util.List;
import java.util.stream.Collectors;

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
        if (event.isFull(member.getSex())) {
            throw new CustomApiException(ResponseEnum.EVENT_MATCH_FULL);
        }
        event.apply(member);

    }

    @Override
    public void applyTeamEvent(TeamApplyForm applyForm) {
        Event event = eventRepository.findById(applyForm.getEventId()).orElseThrow(
                () -> new CustomApiException(ResponseEnum.EVENT_NOT_EXIST)
        );
        if (event.getEventType().getLimitOfEachSex() != applyForm.getMemberIds().size()) {
            throw new CustomApiException(ResponseEnum.EVENT_APPLICANTS_NUM_FAIL);
        }

        List<Member> members = applyForm.getMemberIds().stream().
                map(
                        m -> memberRepository.findById(m).orElseThrow(
                                () -> new CustomApiException(ResponseEnum.USER_NOT_FOUND)
                        )
                ).collect(Collectors.toList());

        List<MemberSex> memberSexes = members.stream()
                .map(m -> m.getSex())
                .collect(Collectors.toList());
        if(!isAllEnumsEqual(memberSexes)) {
            throw new CustomApiException(ResponseEnum.EVENT_APPLICANT_FORM_FAIL);
        }
        /**
         * 여기서부터 수정필요 현재 event들은 그룹으로 묶인것이 아니라 성별대로 묶여있다.
         * 팀으로 지원할 수 있게 바꿔줘야함 
         */
        event.apply(member);
    }



    public boolean isAllEnumsEqual(List<MemberSex> enumList) {
        if (enumList == null || enumList.isEmpty()) {
            return false;
        }

        MemberSex firstEnum = enumList.get(0);

        for (MemberSex enumValue : enumList) {
            if (enumValue != firstEnum) {
                return false;
            }
        }

        return true;
    }


}
