package study.trychat.entity;

import jakarta.persistence.*;
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
  @Column(nullable = false)
  private Long memberId;
  @Column(nullable = false)
  private Long FriendId;
  @Column(nullable = false)
  private String friendNickname;
  @Column(nullable = false)
  private String friendProfileImg;
  @Column(nullable = false)
  private String friendProfileImgPath;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FriendStatus friendStatus;

  public Friend(String friendNickname) {
    this.friendNickname = friendNickname;
  }
}
