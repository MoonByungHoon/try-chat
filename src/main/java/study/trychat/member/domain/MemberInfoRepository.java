package study.trychat.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
  boolean existsByUsername(String username);

  Optional<MemberInfo> findByUsername(String username);

  boolean existsByIdAndUsername(Long memberInfoId, String username);

  Optional<MemberInfo> findById(Long memberId);
}
