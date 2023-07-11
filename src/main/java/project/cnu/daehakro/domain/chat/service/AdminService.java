package project.cnu.daehakro.domain.chat.service;


import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.ApplyInfoDto;

@Service
public interface AdminService {
    ApplyInfoDto applyStatus();

    void randomMatch();

}
