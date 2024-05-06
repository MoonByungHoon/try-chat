package study.trychat.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import study.trychat.dto.MemberRequest;
import study.trychat.dto.QMemberRequest;

import static study.trychat.entity.QMember.member;
import static study.trychat.entity.QMemberInfo.memberInfo;

public class MemberQuerydslImpl implements MemberQuerydsl {
  private JPAQueryFactory queryFactory;

  public MemberQuerydslImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public MemberRequest findByUsernameAndPasswordQuerydsl(String username, String password) {
    return queryFactory.select(new QMemberRequest(
                    member.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.profileImgPath,
                    member.username
            ))
            .from(member)
            .where((member.username.eq(username)
                    .and(member.password.eq(password))
                    .and(member.id.eq(memberInfo.id))))
            .fetchOne();
  }

  @Override
  public MemberRequest findByIdQuerydsl(Long userId) {
    return queryFactory.select(new QMemberRequest(
                    member.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.profileImgPath,
                    member.username
            ))
            .from(member)
            .where(member.id.eq(userId))
            .fetchOne();
  }
}
