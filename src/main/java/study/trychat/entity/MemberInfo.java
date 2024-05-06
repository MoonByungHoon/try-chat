package study.trychat.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class MemberInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_info_id")
  private Long id;
  private String nickname;
  private String greetings;
  private String profileImg;
  private String profileImgPath;
}