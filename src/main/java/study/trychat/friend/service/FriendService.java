package study.trychat.friend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.common.exception.ErrorMessage;
import study.trychat.common.exception.custom.*;
import study.trychat.friend.domain.FriendShip;
import study.trychat.friend.domain.FriendShipRepository;
import study.trychat.friend.domain.FriendStatus;
import study.trychat.friend.dto.FriendBase.FriendShipResponse;
import study.trychat.member.domain.Member;
import study.trychat.member.domain.MemberInfo;
import study.trychat.member.domain.MemberInfoRepository;
import study.trychat.member.domain.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

  private final MemberRepository memberRepository;
  private final MemberInfoRepository memberInfoRepository;
  private final FriendShipRepository friendShipRepository;

  @Transactional
  public List<FriendShipResponse> addFriend(Long memberId, String username) {
    if (memberInfoRepository.existsByUsername(username)) {
      Member findMember = getMember(memberId);

      Long friendId = getFriendId(username);

      validateDuplicateFriend(memberId, friendId);

      validateAddBySelf(findMember.getMemberInfo().getUsername(), username);

      MemberInfo findFriendProfile = getFriendProfile(username);

      FriendShip newFriendShip = FriendShip.init(findMember, findFriendProfile);

      findMember.addFriend(newFriendShip);

      friendShipRepository.save(newFriendShip);

      return getFriendList(memberId);
    }

    throw new NoSuchElementException("찾으시는 회원이 없습니다.");
  }

  private Long getFriendId(String username) {
    MemberInfo memberInfo = memberInfoRepository.findByUsername(username)
            .orElseThrow(FriendNotFoundException::new);
    return memberInfo.getId();
  }

  @Transactional
  public List<FriendShipResponse> removeFriend(Long memberId, Long friendId) {

    Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

    boolean removed = member.getFriendShips().removeIf(friendShip -> friendShip.getFriendId().equals(friendId));

    if (!removed) {
      throw new FriendNotFoundException();
    }

    return getFriendList(memberId);
  }

  public FriendShipResponse getFriendProfile(Long memberId, Long friendId) {

    FriendShip findFriendShip = friendShipRepository.findByFriendId(friendId)
            .orElseThrow(FriendNotFoundException::new);

    validateFriend(memberId, findFriendShip);

    return FriendShipMapper.toFriendShipResponse(findFriendShip);
  }

  private void validateFriend(Long memberId, FriendShip findFriendShip) {
    if (!findFriendShip.checkId(memberId)) {
      throw new FriendNotFoundException();
    }
  }

  @Transactional
  public List<FriendShipResponse> updateBestFriend(Long memberId, Long friendId) {

    FriendShip friendShip = friendShipRepository.findByMemberIdAndFriendId(memberId, friendId)
            .orElseThrow(FriendNotFoundException::new);

    validateBestFriend(friendShip);

    friendShip.bestFriend();

    return getFriendList(memberId);
  }

  private void validateBestFriend(FriendShip friendShip) {
    if (friendShip.getFriendStatus().equals(FriendStatus.BEST_FRIEND)) {
      throw new NowStatusBestException();
    }
  }

  private void validateBlockFriend(FriendShip friendShip) {
    if (friendShip.getFriendStatus().equals(FriendStatus.BLOCK)) {
      throw new NowStatusBlockException();
    }
  }

  @Transactional
  public List<FriendShipResponse> updateBlockFriend(Long memberId, Long friendId) {

    FriendShip friendShip = friendShipRepository.findByMemberIdAndFriendId(memberId, friendId)
            .orElseThrow(FriendNotFoundException::new);

    validateBlockFriend(friendShip);

    friendShip.block();

    return getFriendList(memberId);
  }

  @Transactional
  public FriendShipResponse updateFriendNickname(Long memberId, Long friendId, String nickname) {

    FriendShip findFriendShip = friendShipRepository.findByFriendId(friendId)
            .orElseThrow(FriendNotFoundException::new);

    findFriendShip.updateProfile(nickname);

    return FriendShipMapper.toFriendShipResponse(findFriendShip);
  }

  public List<FriendShipResponse> getFriendList(Long memberId) {
    List<FriendShip> findFriendShips = memberRepository.findById(memberId)
            .orElseThrow(FriendNotFoundException::new)
            .getFriendShips();

    return findFriendShips.stream()
            .map(FriendShipMapper::toFriendShipResponse)
            .collect(Collectors.toList());
  }

  private void validateBestFriendMax(List<FriendShip> findFriendShips) {
    long count = findFriendShips.stream()
            .filter(friend -> friend.getFriendStatus().equals(FriendStatus.BEST_FRIEND))
            .count();

    if (count >= 5) {
      throw new BestFriendMaxException();
    }
  }

  private int findTargetIndex(List<FriendShip> findFriendShips, Long friendId) {
    int findIndex = IntStream.range(0, findFriendShips.size())
            .filter(i -> findFriendShips.get(i).getFriendId().equals(friendId))
            .findFirst().orElse(-1);

    if (findIndex == -1) {
      throw new NoSuchElementException(ErrorMessage.FRIEND_NO_SUCH.getMessage());
    }

    return findIndex;
  }

  private void validateDuplicateFriend(Long memberId, Long friendId) {
    if (friendShipRepository.existsByMemberIdAndFriendId(memberId, friendId)) {
      throw new DuplicateFriendByUserNameException();
    }
  }

  private void validateAddBySelf(String myUsername, String friendUsername) {
    if (myUsername.equals(friendUsername)) {
      throw new FriendAddTargetMeException();
    }
  }

  private MemberInfo getFriendProfile(String username) {
    return memberInfoRepository.findByUsername(username)
            .orElseThrow(FriendNotFoundException::new);
  }

  private Member getMember(Long memberId) {
    return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
  }
}
