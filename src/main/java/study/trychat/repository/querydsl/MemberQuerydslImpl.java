package study.trychat.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberResponse;
import study.trychat.dto.QMemberAuthenticationDto;
import study.trychat.dto.QMemberRequest;

import static study.trychat.entity.QMember.member;
import static study.trychat.entity.QMemberInfo.memberInfo;

public class MemberQuerydslImpl implements MemberQuerydsl {
  private JPAQueryFactory queryFactory;

  public MemberQuerydslImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public MemberAuthenticationDto findAuthenticationTypeById(Long userId) {
    return queryFactory.select(new QMemberAuthenticationDto(
                    member.id,
                    member.username,
                    member.password
            ))
            .from(member)
            .where(member.id.eq(userId))
            .fetchOne();
  }

  @Override
  public MemberResponse findSignInByUsernameAndPassword(String username, String password) {
    return queryFactory.select(new QMemberRequest(
                    memberInfo.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.profileImgPath
            ))
            .from(member, memberInfo)
            .where((member.username.eq(username)
                    .and(member.password.eq(password))
                    .and(member.id.eq(memberInfo.id))))
            .fetchOne();
  }

  @Override
  public MemberResponse findProfileById(Long userId) {
    return queryFactory.select(new QMemberRequest(
                    memberInfo.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.profileImgPath
            ))
            .from(member, memberInfo)
            .where(member.id.eq(userId))
            .fetchOne();
  }
}
