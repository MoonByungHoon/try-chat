package study.trychat.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * study.trychat.dto.QMemberRequest is a Querydsl Projection type for MemberRequest
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberRequest extends ConstructorExpression<MemberRequest> {

    private static final long serialVersionUID = 137388838L;

    public QMemberRequest(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> greetings, com.querydsl.core.types.Expression<String> profileImg, com.querydsl.core.types.Expression<String> profileImgPath, com.querydsl.core.types.Expression<String> username) {
        super(MemberRequest.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class}, id, nickname, greetings, profileImg, profileImgPath, username);
    }

    public QMemberRequest(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> greetings, com.querydsl.core.types.Expression<String> profileImg, com.querydsl.core.types.Expression<String> profileImgPath, com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<String> password) {
        super(MemberRequest.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class}, id, nickname, greetings, profileImg, profileImgPath, username, password);
    }

}

