package study.trychat.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FriendQueryImpl implements FriendQuery {

  private final JPAQueryFactory queryFactory;
}
