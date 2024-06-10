//package study.trychat.chat;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.OneToMany;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class ChatLog extends BaseEntity {
//
//  @OneToMany(mappedBy = "chatLog", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<ChatMessage> chatMessages = new ArrayList<>();
//
//  @OneToMany(mappedBy = "chatLog", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<ChatMember> chatMembers = new ArrayList<>();
//
//  public void createRoom(Long userId, String nickname) {
//    this.chatMembers.add(new ChatMember(userId, nickname));
//  }
//
//  public void addMessage(ChatMessage chatMessage) {
//    chatMessages.add(chatMessage);
//    chatMessage.setChatLog(this);
//  }
//}
