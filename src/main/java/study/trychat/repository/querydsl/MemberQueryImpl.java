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
  public MemberAuthenticationDto findAuthenticationTypeById(Long memberId) {
    return queryFactory.select(new QMemberAuthenticationDto(
                    member.id,
                    member.username,
                    member.password
            ))
            .from(member)
            .where(member.id.eq(memberId))
            .fetchOne();
  }

  @Override
  public MemberResponse findUserProfileByUniqueName(String uniqueName) {
    return queryFactory.select(new QMemberResponse(
                    memberInfo.id,
                    memberInfo.nickname,
                    memberInfo.uniqueName,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.backgroundImg,
                    memberInfo.profileImgPath
            ))
            .from(memberInfo)
            .where(memberInfo.uniqueName.eq(uniqueName))
            .fetchOne();
  }

  @Override
  public SignInResponse findSignInByUsernameAndPassword(String email, String password) {
    return queryFactory.select(new QSignInResponse(
                    memberInfo.id,
                    memberInfo.nickname,
                    memberInfo.username,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.backgroundImg,
                    memberInfo.profileImgPath
            ))
            .from(member)
            .join(member.memberInfo, memberInfo)
            .on(member.id.eq(memberInfo.id))
            .where(member.email.eq(email).and(member.password.eq(password)))
            .fetchOne();
  }

  @Override
  public MemberResponse findProfileById(Long memberId) {
    return queryFactory.select(new QMemberResponse(
                    memberInfo.id,
                    memberInfo.nickname,
                    memberInfo.uniqueName,
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
