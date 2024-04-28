package study.trychat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
public class Friend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  private Long FriendId;
  private String friendNickName;
  private String friendProfileImg;
  private String friendProfileImgPath;

  public Friend(Long userId, String friendNickName) {
    this.userId = userId;
    this.friendNickName = friendNickName;
  }
}
