package study.trychat.httptest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTest {

  private String username;
  private String password;
  private Long userId;

  public MemberTest() {
    this.username = "TestName100@Test.co.kr";
    this.password = "TestAll@1";
    this.userId = 0L;
  }

  public MemberTest(String mail, String s) {
    username = mail;
    password = s;
    userId = 0L;
  }
}
