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
  private Long FriendId;
  private String friendNickName;
  private String friendProfileImg;
  private String friendProfileImgPath;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  public Friend(String friendNickName) {
    this.friendNickName = friendNickName;
  }
}
