package study.trychat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.MemberRequest;
import study.trychat.exception.custom.PrimaryKeyMismatchException;

@Entity
@Getter
@NoArgsConstructor
public class MemberInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_info_id")
  private Long id;
  @Column(nullable = false, length = 20)
  private String nickname;
  @Column(nullable = false, length = 20)
  private String uniqueName;
  @Column(nullable = false, length = 40)
  private String greetings;
  @Column(nullable = false)
  private String profileImg;
  @Column(nullable = false)
  private String profileImgPath;

  public MemberInfo(String nickname, String uniqueName, String greetings, String profileImg, String profileImgPath) {
    this.nickname = nickname;
    this.uniqueName = uniqueName;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.profileImgPath = profileImgPath;
  }

  public void updateProfile(MemberRequest memberRequest) {
    this.nickname = memberRequest.getNickname();
    this.greetings = memberRequest.getGreetings();
    this.profileImg = memberRequest.getProfileImg();
    this.profileImgPath = memberRequest.getProfileImgPath();
  }

  public void checkId(Long userId) {
    if (!(this.id == id)) {
      throw new PrimaryKeyMismatchException();
    }
  }
}
