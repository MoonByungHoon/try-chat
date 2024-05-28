package study.trychat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public class MemberAuthenticationDto {

  private Long id;

  @NotBlank(message = "이메일을 입력해주세요.")
  @Email(message = "올바른 이메일 주소를 입력해주세요.", regexp = "^[^@]{5,64}@[^@]{8,255}")
  private String username;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$")
  private String password;

  @QueryProjection
  public MemberAuthenticationDto(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public static void of(String username, String password) {
    new MemberAuthenticationDto(username, password);
  }

  @JsonCreator
  public MemberAuthenticationDto(@JsonProperty("username") String username,
                                 @JsonProperty("password") String password) {
    this.username = username;
    this.password = password;
  }

  public Member toEntity() {

    return Member.builder()
            .username(username)
            .password(password)
            .memberInfo(new MemberInfo("unknownNickName", "unknownUniqueName",
                    "", "defaultProfile.jpg", "defaultBackground.jpg",
                    "https://my-side-project-bucket.s3.ap-northeast-2.amazonaws.com/"))
            .build();
  }

  public Member toEntityForSignUp(String uniqueName) {

    String nickname = Arrays.stream(username.split("@"))
            .findFirst().orElseThrow(() -> new NoSuchElementException("username split 배열의 첫번째 요소가 없습니다."));

    return Member.builder()
            .username(username)
            .password(password)
            .memberInfo(new MemberInfo("unknownNickName", "unknownUniqueName",
                    "", "defaultProfile.jpg", "defaultBackground.jpg",
                    "https://my-side-project-bucket.s3.ap-northeast-2.amazonaws.com/"))
            .build();
  }
}
