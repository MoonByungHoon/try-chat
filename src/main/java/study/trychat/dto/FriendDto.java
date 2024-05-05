package study.trychat.dto;

public class FriendDto {

  private Long id;
  private Long userId;
  private Long friendId;
  private String friendNickname;
  private String friendProfileImg;
  private String friendProfileImgPath;

  public FriendDto(String friendNickname) {
    this.friendNickname = friendNickname;
  }
}
