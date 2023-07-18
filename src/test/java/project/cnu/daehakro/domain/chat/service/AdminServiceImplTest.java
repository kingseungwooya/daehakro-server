package project.cnu.daehakro.domain.chat.service;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import project.cnu.daehakro.domain.chat.dto.EventDto;
import project.cnu.daehakro.domain.chat.repository.*;
import project.cnu.daehakro.domain.chat.service.matcher.OneToOneEventMatcher;
import project.cnu.daehakro.domain.entity.*;
import project.cnu.daehakro.domain.enums.Department;
import project.cnu.daehakro.domain.enums.EventType;
import project.cnu.daehakro.domain.enums.MemberSex;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private EventLogRepository eventLogRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("event를 생성할 수 있다.")
    public void testCreateEvent() {
        // given
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
    public void testRandomMatch() {
        // Event 객체 생성 및 저장
        Event event = Event.builder()
                .eventType(EventType.ONE_ONE)
                .startDate(LocalDate.of(2023, 8, 1))
                .endDate(LocalDate.of(2023, 8, 10))
                .eventName("충남대 첫번째 랜덤 매칭 이벤트!")
                .maxApply(100).build();

        Event targetEvent = eventRepository.save(event);

        // 매칭에 필요한 Member 데이터 생성 및 저장
        Member man1 = Member.builder()
                .memberId("test1")
                .memberName("남자1")
                .sex(MemberSex.MAN)
                .department(Department.CSE)
                .age(20)
                .build();
        Member man2 = Member.builder()
                .memberId("test2")
                .memberName("남자2")
                .sex(MemberSex.MAN)
                .department(Department.NAOE)
                .age(20)
                .build();
        Member man3 = Member.builder()
                .memberId("test3")
                .memberName("남자3")
                .sex(MemberSex.MAN)
                .department(Department.NAOE)
                .age(20)
                .build();
        Member man4 = Member.builder()
                .memberId("test4")
                .memberName("남자4")
                .sex(MemberSex.MAN)
                .department(Department.CSE)
                .age(20)
                .build();
        Member woman1 = Member.builder()
                .memberId("test5")
                .memberName("여자1")
                .sex(MemberSex.WOMAN)
                .department(Department.CSE)
                .age(20)
                .build();
        Member woman2 = Member.builder()
                .memberId("test6")
                .memberName("여자2")
                .sex(MemberSex.WOMAN)
                .department(Department.NAOE)
                .age(20)
                .build();
        Member woman3 = Member.builder()
                .memberId("test7")
                .memberName("여자3")
                .sex(MemberSex.WOMAN)
                .department(Department.NAOE)
                .age(20)
                .build();
        Member woman4 = Member.builder()
                .memberId("test8")
                .memberName("여자4")
                .sex(MemberSex.WOMAN)
                .department(Department.PIANO)
                .age(20)
                .build();


        Long eventId = event.getEventId();
        man1.applyEvent(eventId);
        man2.applyEvent(eventId);
        man3.applyEvent(eventId);
        man4.applyEvent(eventId);
        woman1.applyEvent(eventId);
        woman2.applyEvent(eventId);
        woman3.applyEvent(eventId);
        woman4.applyEvent(eventId);
        memberRepository.saveAll(List.of(man1, man2, man3, man4, woman1, woman2, woman3, woman4));
        event.apply(man1);
        event.apply(man2);
        event.apply(man3);
        event.apply(man4);
        event.apply(woman1);
        event.apply(woman2);
        event.apply(woman3);
        event.apply(woman4);


        adminService.randomMatch(targetEvent.getEventId());

        // ChatRoom 검증
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        Assert.assertEquals(targetEvent.getMembersOfMan().size(), chatRooms.size());
        for (ChatRoom chatRoom : chatRooms) {
            Assert.assertEquals(2, chatRoom.getMembers().size());
            System.out.println(chatRoom.getRoomId());
            System.out.println(chatRoom.getEventId());
            chatRoom.getMembers()
                    .forEach(s -> System.out.println(s.getMemberId()));
        }

        // eventLog 검증
        // List<EventLog> eventLogs = eventLogRepository.findAll();
        // Assert.assertEquals(targetEvent.getMembersOfMan().size(), eventLogs.size());
        // for (EventLog eventLog : eventLogs) {
        //     System.out.println();
        // }
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


    @Test
    void getAllEvents() {
    }
}