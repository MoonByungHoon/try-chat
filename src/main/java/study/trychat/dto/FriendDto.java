package study.trychat.dto;

public class FriendDto {

  private Long id;
  private Long userId;
  private Long friendId;
  private String friendNickName;
  private String friendProfileImg;
  private String friendProfileImgPath;

  public FriendDto(String friendNickName) {
    this.friendNickName = friendNickName;
  }
}
