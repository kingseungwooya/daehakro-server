package project.cnu.daehakro.domain.chat.service;


import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.ApplyInfoDto;
import project.cnu.daehakro.domain.chat.dto.EventDto;
import project.cnu.daehakro.domain.chat.dto.EventResDto;

import java.util.List;

@Service
public interface AdminService {

    void randomMatch(Long eventId);

    void createEvent(EventDto eventDto);

    List<EventResDto> getAllEvents();

    void closeEvent(Long eventId);

}
