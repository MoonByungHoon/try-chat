package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.entity.Friend;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.custom.DuplicateUniqueNameException;
import study.trychat.repository.FriendRepository;
import study.trychat.repository.MemberInfoRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

  private final MemberInfoRepository memberInfoRepository;
  private final FriendRepository friendRepository;

  public void addFriendByNickname(Long userId, String uniqueName) {

    if (memberInfoRepository.existsByUniqueName(uniqueName)) {
      throw new DuplicateUniqueNameException();
    }

    MemberInfo fineMemberInfo = memberInfoRepository.findByUniqueName(uniqueName);

    friendRepository.save(new Friend().changeFromMemberInfo(userId, fineMemberInfo));
  }
}
