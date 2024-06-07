package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.FriendBase.FriendShipResponse;
import study.trychat.dto.convert.FriendShipMapper;
import study.trychat.entity.FriendShip;
import study.trychat.entity.FriendStatus;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.ErrorMessage;
import study.trychat.exception.custom.*;
import study.trychat.repository.FriendShipRepository;
import study.trychat.repository.MemberInfoRepository;
import study.trychat.repository.MemberRepository;

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

      validateDuplicateFriend(findMember, username);

      validateAddBySelf(findMember.getMemberInfo().getUsername(), username);

      MemberInfo findFriendProfile = getMemberInfo(username);

      FriendShip newFriendShip = FriendShip.init(findMember, findFriendProfile);

      findMember.addFriend(newFriendShip);

      friendShipRepository.save(newFriendShip);

      return getFriendList(memberId);
    }

    throw new NoSuchElementException("찾으시는 회원이 없습니다.");
  }

  @Transactional
  public List<FriendShipResponse> removeFriend(Long memberId, Long friendId) {

    Member fineMember = memberRepository.findById(memberId)
            .orElseThrow(EntityNotFoundException::new);

    if (friendShipRepository.deleteByMemberAndFriendId(fineMember, friendId) == 0) {
      throw new DeleteFalseByMemberIdAndFriendId();
    }

    return getFriendList(memberId);
  }

  public FriendShipResponse getFriendProfile(Long memberId, Long friendId) {

    FriendShip findFriendShip = friendShipRepository.findByFriendId(friendId)
            .orElseThrow(FriendNotFoundException::new);

    validateFriend(memberId, findFriendShip);

    return FriendShipMapper.toFriendResponse(findFriendShip);
  }

  private void validateFriend(Long memberId, FriendShip findFriendShip) {
    if (!findFriendShip.checkId(memberId)) {
      throw new FriendNotFoundException();
    }
  }

  @Transactional
  public List<FriendShipResponse> updateBestFriend(Long memberId, Long friendId) {

    List<FriendShip> findFriendShips = friendShipRepository.findByMemberId(memberId)
            .orElseThrow(FriendNotFoundException::new);

    int findIndex = findTargetIndex(findFriendShips, friendId);

    validateBestFriend(findFriendShips, Long.valueOf(findIndex));
    validateBestFriendMax(findFriendShips);

    findFriendShips.get(findIndex).bestFriend();

    return findFriendShips.stream()
            .map(FriendShipMapper::toFriendResponse)
            .collect(Collectors.toList());
  }

  private void validateBestFriend(List<FriendShip> findFriendShips, Long findIndex) {
    FriendShip targetFriendShip = findFriendShips.stream()
            .filter(friend -> friend.getFriendId().equals(findIndex))
            .findFirst()
            .orElseThrow(FriendNotFoundException::new);

    if (targetFriendShip.getFriendStatus().equals(FriendStatus.BEST_FRIEND)) {
      throw new NowStatusBestException();
    }
  }


  public List<FriendShipResponse> updateBlockFriend(Long memberId, Long friendId) {

    List<FriendShip> findFriendShips = friendShipRepository.findByMemberId(memberId)
            .orElseThrow(FriendNotFoundException::new);

    int findIndex = findTargetIndex(findFriendShips, friendId);

    findFriendShips.get(findIndex).block();

    return findFriendShips.stream()
            .map(FriendShipMapper::toFriendResponse)
            .collect(Collectors.toList());
  }

  @Transactional
  public FriendShipResponse updateFriendNickname(Long memberId, Long friendId, String nickname) {

    FriendShip findFriendShip = friendShipRepository.findByFriendId(friendId)
            .orElseThrow(FriendNotFoundException::new);

    findFriendShip.updateProfile(nickname);

    return FriendShipMapper.toFriendResponse(findFriendShip);
  }

  public List<FriendShipResponse> getFriendList(Long memberId) {
    List<FriendShip> findFriendShips = memberRepository.findById(memberId)
            .orElseThrow(FriendNotFoundException::new)
            .getFriendShips();

    return findFriendShips.stream()
            .map(FriendShipMapper::toFriendResponse)
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

  private void validateDuplicateFriend(Member member, String username) {
    if (friendShipRepository.existsByMemberAndUsername(member, username) > 0) {
      throw new DuplicateFriendByUserNameException();
    }
  }

  private void validateAddBySelf(String myUsername, String friendUsername) {
    if (myUsername.equals(friendUsername)) {
      throw new FriendAddTargetMeException();
    }
  }

  private MemberInfo getMemberInfo(String username) {
    return memberInfoRepository.findByUsername(username)
            .orElseThrow(FriendNotFoundException::new);
  }

  private Member getMember(Long memberId) {
    return memberRepository.findById(memberId)
            .orElseThrow(EntityNotFoundException::new);
  }
}
