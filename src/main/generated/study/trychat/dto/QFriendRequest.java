package study.trychat.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * study.trychat.dto.QFriendRequest is a Querydsl Projection type for FriendRequest
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFriendRequest extends ConstructorExpression<FriendRequest> {

    private static final long serialVersionUID = -134113630L;

    public QFriendRequest(com.querydsl.core.types.Expression<Long> friendId, com.querydsl.core.types.Expression<String> friendNickname, com.querydsl.core.types.Expression<study.trychat.entity.FriendStatus> friendStatus) {
        super(FriendRequest.class, new Class<?>[]{long.class, String.class, study.trychat.entity.FriendStatus.class}, friendId, friendNickname, friendStatus);
    }

}

