package study.trychat.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import study.trychat.dto.FriendResponse;
import study.trychat.dto.QFriendResponse;

import java.util.List;

import static study.trychat.entity.QFriend.friend;
import static study.trychat.entity.QMemberInfo.memberInfo;

@RequiredArgsConstructor
public class FriendQueryImpl implements FriendQuery {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<FriendResponse> findFriendsByUserId(Long userId) {

    return queryFactory.select(new QFriendResponse(
                    friend.id,
                    friend.friendId,
                    friend.friendNickname,
                    friend.friendProfileImg,
                    friend.friendProfileImgPath,
                    friend.friendStatus
            ))
            .from(friend)
            .where(friend.memberId.eq(userId))
            .fetch();
  }

  @Override
  public boolean existsByUniqueName(String uniqueName) {

    return queryFactory.selectFrom(friend)
            .join(memberInfo)
            .on(friend.memberId.eq(memberInfo.id))
            .where(memberInfo.uniqueName.eq(uniqueName))
            .fetchOne() != null;
  }
}
