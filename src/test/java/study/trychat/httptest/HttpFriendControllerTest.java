package study.trychat.httptest;

import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.entity.FriendStatus;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HttpFriendControllerTest {

  private static MemberTest memberTest;
  private static MemberInfoTest memberInfoTest;
  private static MemberInfoTest friendInfoTest1;
  private static MemberInfoTest friendInfoTest2;


  @BeforeAll
  static void 테스트_환경_설정() {
    memberTest = new MemberTest("memberTest@test.co.kr", "Testtest@0");
    MemberTest friendTest1 = new MemberTest("friendTest1@test.co.kr", "Testtest@1");
    MemberTest friendTest2 = new MemberTest("friendTest2@test.co.kr", "Testtest@2");

    회원가입(memberTest);
    회원가입(friendTest1);
    회원가입(friendTest2);

    memberInfoTest = 회원프로필조회(memberTest.getUserId());
    friendInfoTest1 = 회원프로필조회(friendTest1.getUserId());
    friendInfoTest2 = 회원프로필조회(friendTest2.getUserId());
  }

  @AfterAll
  static void 친구삭제() {
    List<FriendTest> friendTests = given().log().all()
            .header("userId", memberTest.getUserId())
            .pathParam("friendId", friendInfoTest1.getId())
            .when()
            .delete("/friends/{friendId}")
            .then().log().all()
            .statusCode(200)
            .extract().as(new TypeRef<List<FriendTest>>() {
            });

    assertEquals(friendTests.size(), 1);
  }

  @Test
  @Order(1)
  void 친구_추가() {
    //    given
    //    when
    List<FriendTest> friendTests = given().log().all()
            .header("userId", memberTest.getUserId())
            .pathParam("uniqueName", friendInfoTest1.getUniqueName())
            .when()
            .post("/friends/{uniqueName}")
            .then().log().all()
            .statusCode(200)
            .extract().as(new TypeRef<List<FriendTest>>() {
            });

    //    then
    friendTests.stream().forEach(friendTest -> {
      assertAll(
              () -> assertEquals(friendTest.getFriendId(), friendInfoTest1.getId()),
              () -> assertEquals(friendTest.getFriendNickname(), friendInfoTest1.getNickname()),
              () -> assertEquals(friendTest.getFriendProfileImg(), friendInfoTest1.getProfileImg()),
              () -> assertEquals(friendTest.getFriendProfileImgPath(), friendInfoTest1.getProfileImgPath()),
              () -> assertEquals(friendTest.getFriendStatus(), FriendStatus.FRIEND)
      );
    });
  }

  @Test
  @Order(2)
  void 친구_리스트_조회() {
    //    given
    //    when
    List<FriendTest> friendTests = given().log().all()
            .header("userId", memberTest.getUserId())
            .pathParam("uniqueName", friendInfoTest2.getUniqueName())
            .when()
            .post("/friends/{uniqueName}")
            .then().log().all()
            .statusCode(200)
            .extract().as(new TypeRef<List<FriendTest>>() {
            });

    //    then
    assertEquals(friendTests.size(), 2);
  }

  private static void 회원가입(MemberTest signUpMember) {
    HashMap<String, Object> requestData = new HashMap<>();
    requestData.put("username", signUpMember.getUsername());
    requestData.put("password", signUpMember.getPassword());

    given().log().all()
            .contentType("application/json")
            .body(requestData)
            .when()
            .post("/users/signup")
            .then().log().all()
            .statusCode(200);

    JsonPath jsonPath = given().log().all()
            .contentType("application/json")
            .body(requestData)
            .when()
            .post("/users/signin")
            .then().log().all()
            .statusCode(200)
            .extract().response().jsonPath();

    signUpMember.setUserId(jsonPath.getLong("id"));
  }

  private static MemberInfoTest 회원프로필조회(Long userId) {

    return given().log().all()
            .pathParam("userId", userId)
            .when()
            .get("/users/{userId}/profile")
            .then().log().all()
            .statusCode(200)
            .extract().as(MemberInfoTest.class);
  }
}
