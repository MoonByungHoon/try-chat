package study.trychat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.MemberRequest;
import study.trychat.dto.MemberResponse;

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

  public void update(MemberResponse memberResponse) {
    this.nickname = memberResponse.getNickname();
    this.greetings = memberResponse.getGreetings();
    this.profileImg = memberResponse.getProfileImg();
    this.profileImgPath = memberResponse.getProfileImgPath();
  }

  public MemberRequest toDto() {
    return MemberRequest.builder()
            .id(id)
            .nickname(nickname)
            .greetings(greetings)
            .profileImg(profileImg)
            .profileImgPath(profileImgPath)
            .build();
  }
}
