package project.cnu.daehakro.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.cnu.daehakro.domain.chat.dto.MemberApplyForm;
import project.cnu.daehakro.domain.chat.service.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class MemberController {

    private final MemberService memberService;

    /**
     * 참여 신청
     */
    @PostMapping("/apply-matching")
    public void apply(@RequestBody MemberApplyForm applyForm) {
        memberService.applyEvent(applyForm);
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
