package project.cnu.daehakro.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    private Long eventId;

    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    @Builder
    public ChatRoom(Long eventId, String title, List<Member> members, List<ChatMessage> messages) {
        this.eventId = eventId;
        this.title = title;
        this.members = members;
        this.messages = messages;
    }

    public void join(Member member) {
        members.add(member);
    }

}
