package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.trychat.entity.Member;
import study.trychat.repository.querydsl.MemberQuerydsl;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuerydsl {
  boolean existsByUsername(String username);
}
