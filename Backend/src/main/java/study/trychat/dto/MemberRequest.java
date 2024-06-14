package study.trychat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberRequest {

  private Long id;
  @NotEmpty(message = "공백은 혀용하지 않습니다.")
  @Size(min = 1, max = 20)
  private String nickname;
  @Size(min = 4, max = 20)
  private String uniqueName;
  @NotNull
  @Size(max = 60)
  private String greetings;
  @NotBlank
  @Size(min = 1)
  private String profileImg;
  @NotBlank
  @Size(min = 1)
  private String backgroundImg;
  @NotBlank
  @Size(min = 1)
  private String profileImgPath;

  @JsonCreator
  public MemberRequest(@JsonProperty("id") Long id,
                       @JsonProperty("nickname") String nickname,
                       @JsonProperty("uniqueName") String uniqueName,
                       @JsonProperty("greetings") String greetings,
                       @JsonProperty("profileImg") String profileImg,
                       @JsonProperty("backgroundImg") String backgroundImg,
                       @JsonProperty("profileImgPath") String profileImgPath) {

    this.id = id;
    this.nickname = nickname;
    this.uniqueName = uniqueName;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.backgroundImg = backgroundImg;
    this.profileImgPath = profileImgPath;
  }
}