package study.trychat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFriend is a Querydsl query type for Friend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriend extends EntityPathBase<Friend> {

    private static final long serialVersionUID = -1485944407L;

    public static final QFriend friend = new QFriend("friend");

    public final NumberPath<Long> FriendId = createNumber("FriendId", Long.class);

    public final StringPath friendNickname = createString("friendNickname");

    public final StringPath friendProfileImg = createString("friendProfileImg");

    public final StringPath friendProfileImgPath = createString("friendProfileImgPath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QFriend(String variable) {
        super(Friend.class, forVariable(variable));
    }

    public QFriend(Path<? extends Friend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFriend(PathMetadata metadata) {
        super(Friend.class, metadata);
    }

}

