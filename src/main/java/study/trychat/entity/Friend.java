package study.trychat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long FriendId;
  private String friendNickname;
  private String friendProfileImg;
  private String friendProfileImgPath;

  public Friend(String friendNickname) {
    this.friendNickname = friendNickname;
  }
}
