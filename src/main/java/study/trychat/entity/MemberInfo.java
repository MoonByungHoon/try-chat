package study.trychat.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.MemberRequest;
import study.trychat.exception.custom.PrimaryKeyMismatchException;

import static study.trychat.vo.MemberInfoVo.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "member_info_id"))
public class MemberInfo extends BaseEntity {
  @Column(nullable = false, length = 20)
  private String nickname;
  @Column(nullable = false, length = 20)
  private String username;
  @Column(nullable = false, length = 40)
  private String greetings;
  @Column(nullable = false)
  private String profileImg;
  @Column(nullable = false)
  private String backgroundImg;
  @Column(nullable = false)
  private String profileImgPath;

  public static MemberInfo init(String nickname, String uniqueName) {
    return new MemberInfo(nickname, uniqueName, "", PROFILE_IMG.getValue(), BACKGROUND_IMG.getValue(), PROFILE_PATH.getValue());
  }

  public void update(MemberRequest memberRequest) {
    this.nickname = memberRequest.getNickname();
    this.greetings = memberRequest.getGreetings();
    this.profileImg = memberRequest.getProfileImg();
    this.backgroundImg = memberRequest.getBackgroundImg();
    this.profileImgPath = memberRequest.getProfileImgPath();
  }

  public void checkId(Long userId) {
    if (!(userId.equals(super.getId()))) {
      throw new PrimaryKeyMismatchException();
    }
  }
}
