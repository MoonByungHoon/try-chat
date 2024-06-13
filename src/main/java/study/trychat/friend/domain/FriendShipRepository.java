package study.trychat.friend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
  long deleteByMemberIdAndFriendId(Long memberId, Long friendId);

  Optional<FriendShip> findByMemberIdAndFriendId(Long memberId, Long friendId);

  Optional<FriendShip> findByFriendId(Long friendId);


  boolean existsByMemberIdAndFriendId(Long memberId, Long friendId);
}
