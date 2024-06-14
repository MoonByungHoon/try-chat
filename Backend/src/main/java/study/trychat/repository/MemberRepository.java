package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.trychat.entity.Member;
import study.trychat.repository.querydsl.MemberQuery;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuery {
  boolean existsByUsername(String username);

  Member findByUsernameAndPassword(String username, String password);
}
