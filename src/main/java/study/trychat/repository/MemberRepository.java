package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.trychat.entity.Member;
import study.trychat.repository.querydsl.MemberQuerydsl;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuerydsl {
  boolean existsByUsername(String username);

  Member findByUsernameAndPassword(String username, String password);
}
