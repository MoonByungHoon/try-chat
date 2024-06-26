package study.trychat.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByEmailAndPassword(String email, String password);

  boolean existsByEmail(String email);

  Optional<Member> findByEmail(String email);
}
