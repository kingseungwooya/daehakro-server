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
import project.cnu.daehakro.domain.chat.dto.MemberApplyForm;
import project.cnu.daehakro.domain.chat.repository.*;
import project.cnu.daehakro.domain.chat.service.matcher.OneToOneEventMatcher;
import project.cnu.daehakro.domain.entity.*;
import project.cnu.daehakro.domain.enums.Department;
import project.cnu.daehakro.domain.enums.EventType;
import project.cnu.daehakro.domain.enums.MemberSex;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
@Transactional
@Rollback(false)
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
    @Autowired
    private MemberService memberService;

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
    @DisplayName("1대1 매칭이 이루어질 수 있다. " +
            "단 같은 과끼리는 매칭되지 않는다." +
            "인원수가 맞지 않더라도 매칭된다." +
            "남은 인원들이 모두 같은과 일 경우 매칭되지 않는다.")
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
                .department(Department.NAOE)
                .age(20)
                .build();


        Long eventId = event.getEventId();

        memberRepository.saveAll(List.of(man1, man2, man3, man4, woman1, woman2, woman3, woman4));

        memberService.applyEvent(new MemberApplyForm(man1.getMemberId(), eventId));
        memberService.applyEvent(new MemberApplyForm(man2.getMemberId(), eventId));
        memberService.applyEvent(new MemberApplyForm(man3.getMemberId(), eventId));
        memberService.applyEvent(new MemberApplyForm(man4.getMemberId(), eventId));
        memberService.applyEvent(new MemberApplyForm(woman1.getMemberId(), eventId));
        memberService.applyEvent(new MemberApplyForm(woman2.getMemberId(), eventId));
        memberService.applyEvent(new MemberApplyForm(woman3.getMemberId(), eventId));
        memberService.applyEvent(new MemberApplyForm(woman4.getMemberId(), eventId));

        adminService.randomMatch(targetEvent.getEventId());

        // ChatRoom 검증
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        for (ChatRoom chatRoom : chatRooms) {
            Assert.assertEquals(2, chatRoom.getMembers().size());
            System.out.println(chatRoom.getRoomId());
            System.out.println(chatRoom.getEventId());
            chatRoom.getMembers()
                    .forEach(s -> System.out.println(s.getMemberName()));
            // 각 Member의 department를 담을 Set 생성
            Set<Department> departments = chatRoom.getMembers().stream()
                    .map(Member::getDepartment)
                    .collect(Collectors.toSet());
            assertTrue(departments.size() == chatRoom.getMembers().size(), "Members have different departments.");

        }

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