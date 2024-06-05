package study.trychat.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import study.trychat.dto.*;

import static study.trychat.entity.QMember.member;
import static study.trychat.entity.QMemberInfo.memberInfo;

@RequiredArgsConstructor
public class MemberQueryImpl implements MemberQuery {

  private final JPAQueryFactory queryFactory;

  @Override
  public MemberResponse findMemberQueryById(Long memberId) {
    return queryFactory.select(new QMemberResponse(
                    member.id,
                    member.email
            ))
            .from(member)
            .where(member.id.eq(memberId))
            .fetchOne();
  }

  @Override
  public MemberProfileResponse findMemberProfileByUsername(String username) {
    return queryFactory.select(new QMemberProfileResponse(
                    memberInfo.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.backgroundImg,
                    memberInfo.profileImgPath
            ))
            .from(memberInfo)
            .where(memberInfo.username.eq(username))
            .fetchOne();
  }

  @Override
  public SignInResponse findSignInByEmailAndPassword(String email, String password) {
    return queryFactory.select(new QSignInResponse(
                    memberInfo.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.backgroundImg,
                    memberInfo.profileImgPath
            ))
            .from(member)
            .join(member.memberInfo, memberInfo)
            .on(member.id.eq(memberInfo.id))
            .where(member.email.eq(email)
                    .and(member.password.eq(password)))
            .fetchOne();
  }

  @Override
  public MemberProfileResponse findMyProfileById(Long memberId) {
    return queryFactory.select(new QMemberProfileResponse(
                    memberInfo.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.backgroundImg,
                    memberInfo.profileImgPath
            ))
            .from(memberInfo)
            .where(memberInfo.id.eq(memberId))
            .fetchOne();
  }
}
