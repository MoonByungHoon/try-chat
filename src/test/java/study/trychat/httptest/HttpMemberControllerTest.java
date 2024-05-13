package study.trychat.httptest;


import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Transactional
@TestMethodOrder(OrderAnnotation.class)
public class HttpMemberControllerTest {

//  현재 테스트는 직접 서비스 되는 DB에 접근하게 된다.
//  지금 당장은 다른 방법을 찾기에는 진행이 더뎌지니 나중에 다른 방법을 찾아보자.

  private static MemberTest memberTest;

  @BeforeAll
  static void 테스트_환경_설정() {
    memberTest = new MemberTest();
  }

  @AfterAll
  @DisplayName("Remove Member")
  static void 회원탈퇴() {
    HashMap<String, Object> requestData = new HashMap<>();
    requestData.put("username", memberTest.getUsername());
    requestData.put("password", memberTest.getPassword());

    given().log().all()
            .contentType("application/json")
            .body(requestData)
            .when()
            .delete("/users/" + memberTest.getUserId())
            .then().log().all()
            .statusCode(200);
  }

  @Test
  @Order(1)
  void 회원가입_테스트() {
//    given
//    when
    HashMap<String, Object> requestData = new HashMap<>();
    requestData.put("username", memberTest.getUsername());
    requestData.put("password", memberTest.getPassword());

//    then
    given().log().all()
            .contentType("application/json")
            .body(requestData)
            .when()
            .post("/users/signup")
            .then().log().all()
            .statusCode(200)
            .extract().jsonPath();
  }

  @Test
  @Order(2)
  void 로그인_테스트() {
    //    given
    //    when
    HashMap<String, Object> requestData = new HashMap<>();
    requestData.put("username", memberTest.getUsername());
    requestData.put("password", memberTest.getPassword());

    //    then
    JsonPath jsonPath = given().log().all()
            .contentType("application/json")
            .body(requestData)
            .when()
            .post("/users/signin")
            .then().log().all()
            .statusCode(200)
            .extract().response().jsonPath();

    memberTest.setUserId(jsonPath.getLong("id"));
  }

  @Test
  @Order(3)
  void 회원_프로필_열람() {
    //    given
    String username = memberTest.getUsername();
    Long userId = memberTest.getUserId();

    //    when
    String splitName = Arrays.stream(username.split("@"))
            .findFirst().orElseThrow(() -> new NoSuchElementException("username split 배열의 첫번째 요소가 없습니다."));

    //    then
    given().log().all()
            .pathParam("userId", userId)
            .when()
            .get("/users/{userId}/profile")
            .then().log().all()
            .statusCode(200)
            .assertThat().body("id", equalTo(userId.intValue()))
            .assertThat().body("nickname", equalTo(splitName))
            .assertThat().body("uniqueName", equalTo(splitName))
            .assertThat().body("greetings", equalTo(""))
            .assertThat().body("profileImg", equalTo(splitName + ".jpg"))
            .assertThat().body("profileImgPath", equalTo("/local/" + splitName + "/"));

  }

  @Test
  @Order(4)
  void 유니크네임으로_프로필조회() {
    //    given
    String username = memberTest.getUsername();
    Long userId = memberTest.getUserId();

    //    when
    String splitName = Arrays.stream(username.split("@"))
            .findFirst().orElseThrow(() -> new NoSuchElementException("username split 배열의 첫번째 요소가 없습니다."));

    //    then
    given().log().all()
            .pathParam("uniqueName", splitName)
            .when()
            .get("/users/{uniqueName}/profile")
            .then().log().all()
            .statusCode(200)
            .assertThat().body("id", equalTo(userId.intValue()))
            .assertThat().body("nickname", equalTo(splitName))
            .assertThat().body("uniqueName", equalTo(splitName))
            .assertThat().body("greetings", equalTo(""))
            .assertThat().body("profileImg", equalTo(splitName + ".jpg"))
            .assertThat().body("profileImgPath", equalTo("/local/" + splitName + "/"));
  }

  @Test
  @Order(5)
  void 회원정보_수정_및_열람() {
    //    given
    String afterUsername = "TestName101@Test.co.kr";
    String afterPassword = "TestAll@1";
    Long userId = memberTest.getUserId();

    //    when
    HashMap<String, Object> requestData = new HashMap<>();
    requestData.put("username", afterUsername);
    requestData.put("password", afterPassword);

    //    then
    given().log().all()
            .contentType("application/json")
            .pathParam("userId", userId)
            .body(requestData)
            .when()
            .put("/users/{userId}")
            .then().log().all()
            .statusCode(200);

    JsonPath jsonPath = given().log().all()
            .contentType("application/json")
            .pathParam("userId", userId)
            .body(requestData)
            .when()
            .get("/users/{userId}")
            .then().log().all()
            .statusCode(200)
            .assertThat().body("username", equalTo(afterUsername))
            .assertThat().body("password", equalTo(afterPassword))
            .extract().jsonPath();

    memberTest.setUsername(jsonPath.get("username"));
    memberTest.setPassword(jsonPath.get("password"));
  }

  @Test
  @Order(6)
  void 마이_프로필_수정() {
    //    given
    Long userId = memberTest.getUserId();
    HashMap<String, Object> requestData = getStringObjectHashMap(userId);

    //    then
    given().log().all()
            .contentType("application/json")
            .pathParam("userId", userId)
            .body(requestData)
            .when()
            .put("/users/{userId}/profile")
            .then().log().all()
            .statusCode(200)
            .assertThat().body("id", equalTo(userId.intValue()))
            .assertThat().body("nickname", equalTo(requestData.get("nickname")))
            .assertThat().body("uniqueName", equalTo(requestData.get("uniqueName")))
            .assertThat().body("greetings", equalTo(requestData.get("greetings")))
            .assertThat().body("profileImg", equalTo(requestData.get("profileImg")))
            .assertThat().body("profileImgPath", equalTo(requestData.get("profileImgPath")));
  }

  private static HashMap<String, Object> getStringObjectHashMap(Long userId) {
    String nickname = "testNickname";
    String uniqueName = "testUniqueName";
    String greetings = "testGreetings";
    String profileImg = "testProfileImg";
    String profileImgPath = "testProfileImgPath";

    //    when
    HashMap<String, Object> requestData = new HashMap<>();
    requestData.put("id", userId);
    requestData.put("nickname", nickname);
    requestData.put("uniqueName", uniqueName);
    requestData.put("greetings", greetings);
    requestData.put("profileImg", profileImg);
    requestData.put("profileImgPath", profileImgPath);
    return requestData;
  }
}
