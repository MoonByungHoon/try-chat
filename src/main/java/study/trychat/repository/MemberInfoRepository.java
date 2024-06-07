package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.trychat.entity.MemberInfo;

import java.util.Optional;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {
  boolean existsByUsername(String username);

  Optional<MemberInfo> findByUsername(String username);

  boolean existsByIdAndUsername(Long memberInfoId, String username);

  Optional<MemberInfo> findById(Long memberId);
}
