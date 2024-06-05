package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.trychat.entity.Member;
import study.trychat.repository.querydsl.MemberQuery;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuery {
  Optional<Member> findByEmailAndPassword(String email, String password);

  boolean existsByEmail(String email);
}
