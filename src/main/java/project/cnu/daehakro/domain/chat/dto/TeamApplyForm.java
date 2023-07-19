package project.cnu.daehakro.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import project.cnu.daehakro.domain.enums.Department;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamApplyForm {

    private List<String> memberIds;

    private Long eventId;

    private String teamName;

    private String applicant;

    private List<Department> excludeDepartments;

}
