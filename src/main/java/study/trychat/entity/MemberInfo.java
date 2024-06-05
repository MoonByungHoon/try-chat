package study.trychat.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.MemberProfileUpdateRequest;

import static study.trychat.init.MemberInfoInitValue.*;

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

  public static MemberInfo init(String nickname, String username) {
    return new MemberInfo(nickname, username, " ", PROFILE_IMG.getValue(), BACKGROUND_IMG.getValue(), PROFILE_PATH.getValue());
  }

  public void update(MemberProfileUpdateRequest profileUpdateRequest) {
    this.nickname = profileUpdateRequest.nickname();
    this.greetings = profileUpdateRequest.greetings();
  }

  public void updateAll(MemberProfileUpdateRequest profileUpdateRequest) {
    this.nickname = profileUpdateRequest.nickname();
    this.greetings = profileUpdateRequest.greetings();
//    this.profileImg = profileUpdateRequest.profileImg();
//    this.backgroundImg = profileUpdateRequest.backgroundImg();
//    this.profileImgPath = profileUpdateRequest.profileImgPath();
  }

  public void updateUsername(String username) {
    this.username = username;
  }
}
