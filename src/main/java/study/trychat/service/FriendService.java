package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.FriendNicknameUpdateRequest;
import study.trychat.dto.FriendResponse;
import study.trychat.entity.Friend;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.ErrorMessage;
import study.trychat.exception.custom.BestFriendMaxException;
import study.trychat.exception.custom.DeleteFalseByMemberIdAndFriendId;
import study.trychat.exception.custom.DuplicateFriendByUserNameException;
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
    duplicateFriend(memberId, username);

    if (memberInfoRepository.existsByUsername(username)) {
      MemberInfo findMemberInfo = memberInfoRepository.findByUsername(username);

      friendRepository.save(new Friend().init(memberId, findMemberInfo));

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

    return friendRepository.findFriendsByMemberIdAndFriendId(memberId, friendId);
  }

  @Transactional
  public List<FriendResponse> updateBestFriend(Long memberId, Long friendId) {

    List<Friend> findFriends = friendRepository.findByMemberId(memberId);

    int findIndex = findTargetIndex(findFriends, friendId);

    validateFindFriends(findFriends);

    findFriends.get(findIndex).bestFriend();

    return findFriends.stream()
            .map(FriendResponse::changeResponse)
            .collect(Collectors.toList());
  }

  public List<FriendResponse> updateBlockFriend(Long memberId, Long friendId) {
    List<Friend> findFriends = friendRepository.findByMemberId(memberId);

    int findIndex = findTargetIndex(findFriends, friendId);

    validateFindFriends(findFriends);

    findFriends.get(findIndex).block();

    return findFriends.stream()
            .map(FriendResponse::changeResponse)
            .collect(Collectors.toList());
  }

  @Transactional
  public FriendResponse updateFriendNickname(Long memberId, FriendNicknameUpdateRequest nicknameUpdateRequest) {

    Friend findFriend = findByMemberIdAndFriendId(memberId, nicknameUpdateRequest.friendId());

    findFriend.updateProfile(nicknameUpdateRequest);

    return FriendResponse.changeResponse(findFriend);
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

  private Friend findByMemberIdAndFriendId(Long memberId, Long friendId) {

    return friendRepository.findByMemberIdAndFriendId(memberId, friendId);
  }

  public List<FriendResponse> findFriendsByMemberId(Long memberId) {

    return friendRepository.findFriendsByMemberId(memberId);
  }

  private void duplicateFriend(Long memberId, String username) {
    if (friendRepository.duplicateFriend(memberId, username)) {
      throw new DuplicateFriendByUserNameException();
    }
  }
}
