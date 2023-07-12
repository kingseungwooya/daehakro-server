package project.cnu.daehakro.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.common.protocol.types.Field;
import project.cnu.daehakro.domain.entity.UnivInfo;

@Getter
public class UnivInfoDto {
    private final Long univId;

    private final String univName;



    public UnivInfoDto(UnivInfo univInfo) {
        this.univId = univInfo.getUnivId();
        this.univName = univInfo.getName();
    }
}
