package project.cnu.daehakro.domain.chat.dto.converter;

import org.springframework.core.convert.converter.Converter;
import project.cnu.daehakro.domain.enums.Department;


public class DepartmentConverter implements Converter<String, Department> {
    @Override
    public Department convert(String source) {
        return Department.of(source);
    }
}
