package project.cnu.daehakro.domain.chat.service;

import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.UnivInfoDto;

import java.util.List;

@Service
public interface UnivService {
    List<UnivInfoDto> getUnivInfos();
}
