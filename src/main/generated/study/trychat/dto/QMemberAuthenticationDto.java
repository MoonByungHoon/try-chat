package study.trychat.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * study.trychat.dto.QMemberAuthenticationDto is a Querydsl Projection type for MemberAuthenticationDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberAuthenticationDto extends ConstructorExpression<MemberAuthenticationDto> {

    private static final long serialVersionUID = -626567138L;

    public QMemberAuthenticationDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<String> password) {
        super(MemberAuthenticationDto.class, new Class<?>[]{long.class, String.class, String.class}, id, username, password);
    }

}

