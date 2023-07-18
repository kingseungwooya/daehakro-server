package project.cnu.daehakro.domain.chat.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import project.cnu.daehakro.domain.chat.dto.EventDto;
import project.cnu.daehakro.domain.chat.repository.EventRepository;
import project.cnu.daehakro.domain.chat.repository.UnivRepository;
import project.cnu.daehakro.domain.entity.Event;
import project.cnu.daehakro.domain.entity.UnivInfo;
import project.cnu.daehakro.domain.enums.EventType;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
@Transactional
// @Rollback(true)
class AdminServiceImplTest {

    @Autowired
    AdminService adminService;
    @Autowired
    private UnivRepository univRepository;
    @Autowired
    private EventRepository eventRepository;
    @Test
    public void testCreateEvent() {
        UnivInfo univInfo = UnivInfo.builder()
                     .place("daejeon")
                     .name("충남대학교").domain("o.cnu.ac.kr").build();
        univInfo = univRepository.save(univInfo);

        EventDto eventDto = new EventDto();
        eventDto.setEventName("Sample Event");
        eventDto.setMaxApply(100);
        eventDto.setStartDate(LocalDate.of(2023, 8, 1));
        eventDto.setEndDate(LocalDate.of(2023, 8, 10));
        eventDto.setUnivId(univInfo.getUnivId());
        eventDto.setEventType(EventType.ONE_ONE);

        adminService.createEvent(eventDto);

        // eventRepository로부터 저장된 이벤트를 조회하여 검증
        List<Event> events = eventRepository.findAll();


        Event event = events.get(0);
        assertThat(event.getEventName()).isEqualTo("Sample Event");
        assertThat(event.getMaxApply()).isEqualTo(100);
        assertThat(event.getStartDate()).isEqualTo(LocalDate.of(2023, 8, 1));
        assertThat(event.getEndDate()).isEqualTo(LocalDate.of(2023, 8, 10));

    }
    @Test
    void randomMatch() {
    }

    @Test
    void randomTeamMatch() {
    }

    @Test
    void closeEvent() {
    }

    @Test
    void closeTeamEvent() {
    }

    // @Test
    // void createEvent() {
    //     UnivInfo mockUnivInfo = UnivInfo.builder()
    //             .place("daejeon")
    //             .name("충남대학교").domain("o.cnu.ac.kr").build();
//
    //     EventDto eventDto = new EventDto("test_event"
    //             , LocalDate.of(2023, 7, 18)
    //             , LocalDate.of(2023, 7, 22)
    //             , 200
    //             , EventType.ONE_ONE
    //             , 1L);
    //     // univRepository.findById()가 호출될 때 Mock 데이터 리턴
    //     when(univRepository.findById(1L)).thenReturn(Optional.of(mockUnivInfo));
//
    //     // eventRepository.save()가 호출될 때 Mock 데이터 리턴
    //     when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> {
    //         Event event = invocation.getArgument(0);
    //         event.setEventId(1L); // 저장 후 생성된 이벤트의 ID 설정
    //         return event;
    //     });
//
    //     // createEvent() 메소드 호출
    //     adminService.createEvent(eventDto);
//
    //     // univRepository.findById()가 올바르게 호출되었는지 검증
    //     verify(univRepository).findById(1L);
//
    //     // eventRepository.save()가 올바르게 호출되었는지 검증
    //     verify(eventRepository).save(any(Event.class));
//
    // }

    @Test
    void getAllEvents() {
    }
}