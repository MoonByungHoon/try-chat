package study.trychat.member.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.common.BaseEntity;
import study.trychat.member.dto.MemberBase.MemberProfileUpdateRequest;

import static study.trychat.member.domain.MemberInfoDefault.*;

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

  public MemberInfo(String name, String profileImgUrl) {
//    todo profileImgUrl 경로 확인 후 수정 필요.
    this.nickname = name;
    this.username = name;
    this.greetings = " ";
    this.profileImg = profileImgUrl;
    this.backgroundImg = BACKGROUND_IMG.getValue();
    this.profileImgPath = "수정";
  }

  public static MemberInfo init(String nickname, String username) {
    return new MemberInfo(nickname, username, " ", PROFILE_IMG.getValue(),
            BACKGROUND_IMG.getValue(), PROFILE_PATH.getValue());
  }

  public void update(String nickname, String greetings) {
    this.nickname = nickname;
    this.greetings = greetings;
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
