package study.trychat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.MemberRequest;
import study.trychat.dto.MemberRequestt;

@Entity
@Getter
@NoArgsConstructor
public class MemberInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_info_id")
  private Long id;
  private String nickname;
  private String greetings;
  private String profileImg;
  private String profileImgPath;

  public MemberInfo(String nickname, String greetings, String profileImg, String profileImgPath) {
    this.nickname = nickname;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.profileImgPath = profileImgPath;
  }

  public void update(MemberRequest memberRequest) {
    this.nickname = memberRequest.getNickname();
    this.greetings = memberRequest.getGreetings();
    this.profileImg = memberRequest.getProfileImg();
    this.profileImgPath = memberRequest.getProfileImgPath();
  }

  public MemberRequestt toDto() {
    return MemberRequestt.builder()
            .id(id)
            .nickname(nickname)
            .greetings(greetings)
            .profileImg(profileImg)
            .profileImgPath(profileImgPath)
            .build();
  }
}
