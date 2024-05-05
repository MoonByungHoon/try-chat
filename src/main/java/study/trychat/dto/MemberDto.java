package study.trychat.dto;

import lombok.*;
import study.trychat.entity.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
  private Long id;
  private String username;
  private String password;
  private String nickName;
  private String greetings;
  private String profileImg;
  private String profileImgPath;

  private final List<FriendDto> friendDtoList = new ArrayList<>();

  public MemberDto(String username) {
    this.username = username;
  }

  public MemberDto(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public MemberDto(String nickName, String profileImg, String profileImgPath) {
    this.nickName = nickName;
    this.profileImg = profileImg;
    this.profileImgPath = profileImgPath;
  }

  public MemberDto(Long userId, String nickName, FriendDto... friendDtos) {
    this.id = userId;
    this.nickName = nickName;
    Collections.addAll(friendDtoList, friendDtos);
  }

  public Member toEntity() {
    return Member.builder()
            .username(this.username)
            .password(this.password)
            .build();
  }
}
