//package study.trychat.chat;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import static study.trychat.init.MemberInfoDefaultValue.PROFILE_IMG;
//import static study.trychat.init.MemberInfoDefaultValue.PROFILE_PATH;
//
//@Getter
//@Entity
//@Table(name = "chat_member")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class ChatMember extends BaseEntity {
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "chat_log_id")
//  private ChatLog chatLog;
//  private Long originId;
//  private String nickname;
//  private String profileImg;
//  private String profileImgPath;
//
//  public ChatMember(Long originId, String nickname) {
//    this.originId = originId;
//    this.nickname = nickname;
//    this.profileImg = PROFILE_IMG.getValue();
//    this.profileImgPath = PROFILE_PATH.getValue();
//  }
//}
