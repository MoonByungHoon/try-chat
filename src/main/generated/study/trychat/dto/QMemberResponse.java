package study.trychat.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * study.trychat.dto.QMemberResponse is a Querydsl Projection type for MemberResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberResponse extends ConstructorExpression<MemberResponse> {

    private static final long serialVersionUID = 17020554L;

    public QMemberResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> nickname, com.querydsl.core.types.Expression<String> uniqueName, com.querydsl.core.types.Expression<String> greetings, com.querydsl.core.types.Expression<String> profileImg, com.querydsl.core.types.Expression<String> profileImgPath) {
        super(MemberResponse.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class}, id, nickname, uniqueName, greetings, profileImg, profileImgPath);
    }

}

