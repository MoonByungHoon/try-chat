package study.trychat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.trychat.entity.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
  private Long id;
  private String username;
  private String password;
  private List<FriendDto> friendDtoList = new ArrayList<>();

  public MemberDto(String username) {
    this.username = username;
  }

  public MemberDto(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public void addFriendDto(FriendDto... friendDtos) {
    Collections.addAll(friendDtoList, friendDtos);
  }

  public Member toEntity() {
    return Member.builder()
            .username(this.username)
            .password(this.password)
            .build();
  }
}
