package project.cnu.daehakro.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cnu.daehakro.domain.entity.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
}
