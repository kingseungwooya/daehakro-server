package project.cnu.daehakro.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.cnu.daehakro.domain.chat.dto.MemberApplyForm;
import project.cnu.daehakro.domain.chat.dto.TeamApplyForm;
import project.cnu.daehakro.domain.chat.service.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class MemberController {

    private final MemberService memberService;

    /**
     * 개인 참여 신청
     */
    @PostMapping("/apply-individual")
    public void applyIndividual(@RequestBody MemberApplyForm applyForm) {
        memberService.applyEvent(applyForm);
    }
    /**
     * 팀 참여 신청
     */
    @PostMapping("/apply-team")
    public void applyTeam(@RequestBody TeamApplyForm applyForm) {
        memberService.applyTeamEvent(applyForm);
    }
    /**
     * 이메일 인증
     */


    /**
     * 회원가입
     */

    /**
     * 로그인
     */

}
