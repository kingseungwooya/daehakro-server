package project.cnu.daehakro.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.cnu.daehakro.domain.enums.Department;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberApplyForm {

    private String memberId;

    private Long eventId;

    private List<Department> excludeDepartments;

}
