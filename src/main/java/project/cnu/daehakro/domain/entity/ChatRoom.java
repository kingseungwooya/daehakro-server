package project.cnu.daehakro.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    private String title;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "chatRoom")
    private List<Member> members;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "chatRoom")
    private List<ChatMessage> messages;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;


}
