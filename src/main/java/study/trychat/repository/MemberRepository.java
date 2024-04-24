package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.trychat.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  boolean existsByUsername(String username);
}
