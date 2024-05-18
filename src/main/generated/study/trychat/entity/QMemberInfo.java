package study.trychat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberInfo is a Querydsl query type for MemberInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberInfo extends EntityPathBase<MemberInfo> {

    private static final long serialVersionUID = 1733707763L;

    public static final QMemberInfo memberInfo = new QMemberInfo("memberInfo");

    public final StringPath backgroundImg = createString("backgroundImg");

    public final StringPath greetings = createString("greetings");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath profileImgPath = createString("profileImgPath");

    public final StringPath uniqueName = createString("uniqueName");

    public QMemberInfo(String variable) {
        super(MemberInfo.class, forVariable(variable));
    }

    public QMemberInfo(Path<? extends MemberInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberInfo(PathMetadata metadata) {
        super(MemberInfo.class, metadata);
    }

}

