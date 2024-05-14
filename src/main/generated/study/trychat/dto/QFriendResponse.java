package study.trychat.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * study.trychat.dto.QFriendResponse is a Querydsl Projection type for FriendResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFriendResponse extends ConstructorExpression<FriendResponse> {

    private static final long serialVersionUID = 190378638L;

    public QFriendResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Long> friendId, com.querydsl.core.types.Expression<String> friendNickname, com.querydsl.core.types.Expression<String> friendProfileImg, com.querydsl.core.types.Expression<String> friendProfileImgPath, com.querydsl.core.types.Expression<study.trychat.entity.FriendStatus> friendStatus) {
        super(FriendResponse.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, study.trychat.entity.FriendStatus.class}, id, friendId, friendNickname, friendProfileImg, friendProfileImgPath, friendStatus);
    }

}

