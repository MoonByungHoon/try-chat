package study.trychat.httptest;

public class MemberTest {

  private String username;
  private String password;
  private Long userId;

  public MemberTest() {
    this.username = "TestName100@Test.co.kr";
    this.password = "TestAll@1";
    this.userId = 0L;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Long getUserId() {
    return userId;
  }
}
