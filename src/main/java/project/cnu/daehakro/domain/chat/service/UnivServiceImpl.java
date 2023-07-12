package project.cnu.daehakro.domain.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.UnivInfoDto;
import project.cnu.daehakro.domain.chat.repository.UnivRepository;
import project.cnu.daehakro.domain.entity.UnivInfo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UnivServiceImpl implements UnivService {
    private final UnivRepository univRepository;

    @Override
    public List<UnivInfoDto> getUnivInfos() {
        List<UnivInfo> all = univRepository.findAll();
        return all.stream()
                .map(
                        e -> new UnivInfoDto(e)
                ).collect(Collectors.toList());
    }
}
