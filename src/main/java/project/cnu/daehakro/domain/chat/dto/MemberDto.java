package project.cnu.daehakro.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.cnu.daehakro.domain.entity.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private String username;

    private String memberId;

    public MemberDto(Member member) {
        this.username = member.getMemberName();
        this.memberId = member.getMemberId();
    }

}
