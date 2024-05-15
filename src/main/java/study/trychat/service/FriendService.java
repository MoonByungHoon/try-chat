package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.FriendResponse;
import study.trychat.entity.Friend;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.custom.DeleteFalseByMemberIdAndFriendId;
import study.trychat.exception.custom.DuplicateFriendByUniqueNameException;
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
  public List<FriendResponse> addFriendByNickname(Long userId, String uniqueName) {
    if (friendRepository.existsByUniqueName(uniqueName)) {
      throw new DuplicateFriendByUniqueNameException();
    }
    if (memberInfoRepository.existsByUniqueName(uniqueName)) {
      MemberInfo fineMemberInfo = memberInfoRepository.findByUniqueName(uniqueName);

      friendRepository.save(new Friend().changeFromMemberInfo(userId, fineMemberInfo));

      return findByUserId(userId);
    }

    throw new NoSuchElementException("찾으시는 회원이 없습니다.");
  }

  public List<FriendResponse> findByUserId(Long userId) {

    return friendRepository.findFriendsByUserId(userId);
  }

  @Transactional
  public List<FriendResponse> removeFriend(Long userId, Long friendId) {

    if (friendRepository.deleteByMemberIdAndFriendId(userId, friendId) == 0) {
      throw new DeleteFalseByMemberIdAndFriendId();
    }

    return friendRepository.findFriendsByUserId(userId);
  }
}
