package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.FriendRequest;
import study.trychat.dto.FriendResponse;
import study.trychat.entity.Friend;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.custom.DeleteFalseByMemberIdAndFriendId;
import study.trychat.exception.custom.DuplicateFriendByUniqueNameException;
import study.trychat.repository.FriendRepository;
import study.trychat.repository.MemberInfoRepository;
import study.trychat.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

  private final MemberInfoRepository memberInfoRepository;
  private final FriendRepository friendRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public List<FriendResponse> addFriendByNickname(Long memberId, String uniqueName) {
    if (friendRepository.existsByUniqueName(memberId, uniqueName)) {
      throw new DuplicateFriendByUniqueNameException();
    }
    
    if (memberInfoRepository.existsByUniqueName(uniqueName)) {
      MemberInfo fineMemberInfo = memberInfoRepository.findByUniqueName(uniqueName);

      friendRepository.save(new Friend().fromMemberInfo(memberId, fineMemberInfo));

      return findByMemberId(memberId);
    }

    throw new NoSuchElementException("찾으시는 회원이 없습니다.");
  }

  @Transactional
  public List<FriendResponse> removeFriend(Long memberId, Long friendId) {

    if (friendRepository.deleteByMemberIdAndFriendId(memberId, friendId) == 0) {
      throw new DeleteFalseByMemberIdAndFriendId();
    }

    return findByMemberId(memberId);
  }

  public FriendResponse findFriendByFriendId(Long memberId, Long friendId) {

    return friendRepository.findFriendsByMemberIdAndFriendId(memberId, friendId);
  }

  @Transactional
  public List<FriendResponse> updateFriendStatus(Long memberId, FriendRequest friendRequest) {

    Friend findFriend = findFriendEntityByMemberIdAndFriendId(memberId, friendRequest.getFriendId());

    findFriend.updateStatus(friendRequest.getFriendStatus());

    return findByMemberId(memberId);
  }

  @Transactional
  public FriendResponse updateFriendProfile(Long memberId, FriendRequest friendRequest) {

    Friend findFriend = findFriendEntityByMemberIdAndFriendId(memberId, friendRequest.getFriendId());

    findFriend.updateProfile(friendRequest);

    return FriendResponse.fromRequest(findFriend);
  }

  private Friend findFriendEntityByMemberIdAndFriendId(Long memberId, Long friendId) {

    return friendRepository.findByMemberIdAndFriendId(memberId, friendId);
  }

  public List<FriendResponse> findByMemberId(Long memberId) {

    return friendRepository.findFriendsByMemberId(memberId);
  }
}
