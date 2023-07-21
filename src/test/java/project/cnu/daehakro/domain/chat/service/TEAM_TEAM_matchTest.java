package project.cnu.daehakro.domain.chat.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import project.cnu.daehakro.domain.chat.dto.EventDto;
import project.cnu.daehakro.domain.chat.dto.MemberApplyForm;
import project.cnu.daehakro.domain.chat.repository.*;
import project.cnu.daehakro.domain.entity.ChatRoom;
import project.cnu.daehakro.domain.entity.Event;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.entity.UnivInfo;
import project.cnu.daehakro.domain.enums.Department;
import project.cnu.daehakro.domain.enums.EventType;
import project.cnu.daehakro.domain.enums.MemberSex;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
@Transactional
@Rollback(false)
class TEAM_TEAM_matchTest {

    @Autowired
    AdminService adminService;
    @Autowired
    private UnivRepository univRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private EventLogRepository eventLogRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("event를 생성할 수 있다.")
    public void testCreateEvent() {
        // given
        UnivInfo univInfo = UnivInfo.builder()
                .place("daejeon")
                .name("충남대학교")
                .domain("o.cnu.ac.kr")
                .build();
        univInfo = univRepository.save(univInfo);

        EventDto eventDto = new EventDto();
        eventDto.setEventName("Team Team Event");
        eventDto.setMaxApply(100);
        eventDto.setStartDate(LocalDate.of(2023, 8, 1));
        eventDto.setEndDate(LocalDate.of(2023, 8, 10));
        eventDto.setUnivId(univInfo.getUnivId());
        eventDto.setEventType(EventType.TWO_TWO);
        // when
        adminService.createEvent(eventDto);

        // then
        List<Event> events = eventRepository.findAll();

        Event event = events.get(0);
        assertThat(event.getEventName()).isEqualTo("Sample Event");
        assertThat(event.getMaxApply()).isEqualTo(100);
        assertThat(event.getStartDate()).isEqualTo(LocalDate.of(2023, 8, 1));
        assertThat(event.getEndDate()).isEqualTo(LocalDate.of(2023, 8, 10));
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


    @Test
    void getAllEvents() {
    }
}