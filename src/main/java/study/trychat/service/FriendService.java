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
  public List<FriendResponse> removeFriend(Long memberId, Long friendId) {

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

    List<Friend> findFriends = friendRepository.findByMemberId();

    boolean presentFriend = findFriends.stream()
            .filter(friend -> friend.getFriendId().equals(friendId))
            .findFirst().isPresent();

    if (!presentFriend) {
      throw new NoSuchElementException(ErrorMessage.FRIEND_NO_SUCH.getMessage());
    }

    if (findFriends.size() >= 5) {
      throw new BestFriendMaxException();
    }

    Friend findFriend = findByMemberIdAndFriendId(memberId, friendId);

    findFriend.updateBestStatus();

    return findFriendsByMemberId(memberId);
  }

  @Transactional
  public FriendResponse updateFriendNickname(Long memberId, FriendNicknameUpdateRequest nicknameUpdateRequest) {

    Friend findFriend = findByMemberIdAndFriendId(memberId, nicknameUpdateRequest.friendId());

    findFriend.updateProfile(nicknameUpdateRequest);

    return FriendResponse.changeRequest(findFriend);
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
