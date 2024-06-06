package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.FriendBase.FriendResponse;
import study.trychat.dto.convert.FriendMapper;
import study.trychat.entity.Friend;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.ErrorMessage;
import study.trychat.exception.custom.BestFriendMaxException;
import study.trychat.exception.custom.DeleteFalseByMemberIdAndFriendId;
import study.trychat.exception.custom.DuplicateFriendByUserNameException;
import study.trychat.exception.custom.EntityNotFoundException;
import study.trychat.repository.FriendRepository;
import study.trychat.repository.MemberInfoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

  private final MemberInfoRepository memberInfoRepository;
  private final FriendRepository friendRepository;

  @Transactional
  public List<FriendResponse> addFriendByUsername(Long memberId, String username) {
    if (memberInfoRepository.existsByUsername(username)) {
      duplicateFriend(memberId, username);

      MemberInfo findMemberInfo = memberInfoRepository.findByUsername(username)
              .orElseThrow(EntityNotFoundException::new);

      friendRepository.save(Friend.init(memberId, findMemberInfo));

      return findFriendsByMemberId(memberId);
    }

    throw new NoSuchElementException("찾으시는 회원이 없습니다.");
  }

  @Transactional
  public List<FriendResponse> removeFriendByMemberIdAndFriendId(Long memberId, Long friendId) {

    if (friendRepository.deleteByMemberIdAndFriendId(memberId, friendId) == 0) {
      throw new DeleteFalseByMemberIdAndFriendId();
    }

    return findFriendsByMemberId(memberId);
  }

  public FriendResponse findFriendsByMemberIdAndFriendId(Long memberId, Long friendId) {

    Friend findFriend = friendRepository.findByMemberIdAndFriendId(memberId, friendId)
            .orElseThrow(EntityNotFoundException::new);

    return FriendMapper.toFriendResponse(findFriend);
  }

  @Transactional
  public List<FriendResponse> updateBestFriend(Long memberId, Long friendId) {

    List<Friend> findFriends = friendRepository.findByMemberId(memberId)
            .orElseThrow(EntityNotFoundException::new);

    int findIndex = findTargetIndex(findFriends, friendId);

    validateFindFriends(findFriends);

    findFriends.get(findIndex).bestFriend();

    return findFriends.stream()
            .map(FriendMapper::toFriendResponse)
            .collect(Collectors.toList());
  }

  public List<FriendResponse> updateBlockFriend(Long memberId, Long friendId) {

    List<Friend> findFriends = friendRepository.findByMemberId(memberId)
            .orElseThrow(EntityNotFoundException::new);

    int findIndex = findTargetIndex(findFriends, friendId);

    validateFindFriends(findFriends);

    findFriends.get(findIndex).block();

    return findFriends.stream()
            .map(FriendMapper::toFriendResponse)
            .collect(Collectors.toList());
  }

  @Transactional
  public FriendResponse updateFriendNickname(Long memberId, Long friendId, String nickname) {

    Friend findFriend = friendRepository.findByMemberIdAndFriendId(memberId, friendId)
            .orElseThrow(EntityNotFoundException::new);

    findFriend.updateProfile(nickname);

    return FriendMapper.toFriendResponse(findFriend);
  }

  public List<FriendResponse> findFriendsByMemberId(Long memberId) {
    List<Friend> findFriends = friendRepository.findByMemberId(memberId)
            .orElseThrow(EntityNotFoundException::new);

    return findFriends.stream()
            .map(FriendMapper::toFriendResponse)
            .collect(Collectors.toList());
  }

  private void validateFindFriends(List<Friend> findFriends) {
    if (findFriends.size() >= 5) {
      throw new BestFriendMaxException();
    }
  }

  private int findTargetIndex(List<Friend> findFriends, Long friendId) {
    int findIndex = IntStream.range(0, findFriends.size())
            .filter(i -> findFriends.get(i).getFriendId().equals(friendId))
            .findFirst().orElse(-1);

    if (findIndex == -1) {
      throw new NoSuchElementException(ErrorMessage.FRIEND_NO_SUCH.getMessage());
    }

    return findIndex;
  }

  private void duplicateFriend(Long memberId, String username) {
    if (friendRepository.existsByMemberIdAndUsername(memberId, username)) {
      throw new DuplicateFriendByUserNameException();
    }
  }
}
