package study.trychat.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.common.exception.ErrorMessage;
import study.trychat.common.exception.custom.MemberNotFoundException;
import study.trychat.member.domain.Member;
import study.trychat.member.domain.MemberRepository;
import study.trychat.member.domain.Role;
import study.trychat.member.dto.MemberBase.MemberProfileUpdateRequest;
import study.trychat.member.dto.UsernameParam;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static study.trychat.member.domain.MemberInfoDefault.*;
import static study.trychat.member.dto.SignBase.SignUpRequest;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void 회원가입_성공() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";
    String nickname = testEmail.split("@")[0];

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);

    Member member = memberRepository.findByEmail("testsignup@test.co.kr")
            .orElseThrow(MemberNotFoundException::new);

    //    then
    assertAll(
            () -> assertEquals(member.getEmail(), testEmail),
            () -> assertEquals(member.getPassword(), testPassword),
            () -> assertEquals(member.getMemberInfo().getNickname(), nickname),
            () -> assertEquals(member.getMemberInfo().getUsername(), nickname),
            () -> assertEquals(member.getMemberInfo().getProfileImg(), PROFILE_IMG.getValue()),
            () -> assertEquals(member.getMemberInfo().getBackgroundImg(), BACKGROUND_IMG.getValue()),
            () -> assertEquals(member.getMemberInfo().getProfileImgPath(), PROFILE_PATH.getValue())
    );
  }

  @Test
  void 회원가입_중복_이메일_실패() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);
    ResultActions resultActions = 회원가입(request);

    //    then
    resultActions.andExpectAll(
            status().is(ErrorMessage.SIGN_UP_DUPLICATE_USER.getStatus().value()),
            jsonPath("$.message").value(ErrorMessage.SIGN_UP_DUPLICATE_USER.getMessage()),
            jsonPath("$.status").value(ErrorMessage.SIGN_UP_DUPLICATE_USER.getStatus().value())
    );
  }

  @Test
  void 회원가입_이메일_검증() throws Exception {
    //    given
    String testEmail = "잘못된 이메일 양식";
    String testPassword = "testSignup@1";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    ResultActions resultActions = 회원가입(request);

    //    then
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  void 회원가입_비밀번호_검증() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "잘못된 비밀번호";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    ResultActions resultActions = 회원가입(request);

    //    then
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  void 로그인_성공() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";
    String nickname = testEmail.split("@")[0];

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);
    ResultActions resultActions = 로그인(request);

    //    then
    resultActions.andExpectAll(
            status().isOk(),
            jsonPath("$.nickname").value(nickname),
            jsonPath("$.greetings").value(" "),
            jsonPath("$.profileImg").value(PROFILE_IMG.getValue()),
            jsonPath("$.backgroundImg").value(BACKGROUND_IMG.getValue()),
            jsonPath("$.profileImgPath").value(PROFILE_PATH.getValue()),
            jsonPath("$.role").value(Role.USER.name())
    );
  }

  @Test
  void 로그인_실패() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";
    String failPassword = "failPassword@";

    String request = sign_객체_생성(testEmail, testPassword);
    String failRequest = sign_객체_생성(testEmail, failPassword);

    //    when
    회원가입(request);
    ResultActions resultActions = 로그인(failRequest);

    //    then
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  void 가입_정보_조회() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);
    ResultActions resultActions = 로그인(request);

    String responseJson = resultActions.andReturn().getResponse().getContentAsString();
    long userId = objectMapper.readTree(responseJson).get("id").asLong();

    //    then
    mockMvc.perform(get("/api/users/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").value(userId),
                    jsonPath("$.email").value(testEmail)
            );
  }

  @Test
  void 가입정보_조회_실패() throws Exception {
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);

    //    then
    mockMvc.perform(get("/api/users/{userId}", 100000)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isBadRequest());
  }

  @Test
  void 마이_프로필_조회() throws Exception {
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";
    String nickname = testEmail.split("@")[0];

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);
    ResultActions resultActions = 로그인(request);

    String contentAsString = resultActions.andReturn().getResponse().getContentAsString();

    long userId = objectMapper.readTree(contentAsString).get("id").asLong();

    //    then
    mockMvc.perform(get("/api/users/{userId}/profile", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").value(userId),
                    jsonPath("$.nickname").value(nickname),
                    jsonPath("$.greetings").value(" "),
                    jsonPath("$.profileImg").value(PROFILE_IMG.getValue()),
                    jsonPath("$.backgroundImg").value(BACKGROUND_IMG.getValue()),
                    jsonPath("$.profileImgPath").value(PROFILE_PATH.getValue())
            );
  }

  @Test
  void 마이_프로필_조회_실패_대상이_본인이_아님() {
    //    given
    // todo 추후 jwt 적용 후 테스트 작성

    //    when

    //    then
  }

  @Test
  void 친구검색() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";
    String usernameAndNickname = testEmail.split("@")[0];

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);

    //    then
    mockMvc.perform(get("/api/users/profile")
                    .param("username", usernameAndNickname)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.nickname").value(usernameAndNickname),
                    jsonPath("$.greetings").value(" "),
                    jsonPath("$.profileImg").value(PROFILE_IMG.getValue()),
                    jsonPath("$.backgroundImg").value(BACKGROUND_IMG.getValue()),
                    jsonPath("$.profileImgPath").value(PROFILE_PATH.getValue())
            );
  }

  @Test
  void 친구검색_실패_대상_없음() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";
    String failNickname = "failTestNickname";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);

    //    then
    mockMvc.perform(get("/api/users/profile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(failNickname))
            .andExpect(status().isBadRequest());
  }

  @Test
  void 마이_프로필_수정() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);
    ResultActions resultActions = 로그인(request);

    String responseJson = resultActions.andReturn().getResponse().getContentAsString();
    long userId = objectMapper.readTree(responseJson).get("id").asLong();
    String beforeNickname = "beforeNickname";
    String beforeGreetings = "beforeGreetings";

    MemberProfileUpdateRequest afterProfile = new MemberProfileUpdateRequest(userId, beforeNickname, beforeGreetings);

    byte[] beforeRequest = objectMapper.writeValueAsBytes(afterProfile);

    //    then
    mockMvc.perform(multipart("/api/users/{userId}/profile", userId)
                    .file(new MockMultipartFile(
                            "profileUpdateRequest",
                            "",
                            "application/json",
                            beforeRequest))
                    .with(re -> {
                      re.setMethod("PUT");
                      return re;
                    })
                    .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").value(userId),
                    jsonPath("$.nickname").value(beforeNickname),
                    jsonPath("$.greetings").value(beforeGreetings)
            );
  }

  @Test
  void 마이_프로필_수정_실패_대상_불일치() throws Exception {
    //    given
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);

    long failUserId = 100000L;
    String beforeNickname = "beforeNickname";
    String beforeGreetings = "beforeGreetings";

    MemberProfileUpdateRequest afterProfile = new MemberProfileUpdateRequest(failUserId, beforeNickname, beforeGreetings);

    byte[] beforeRequest = objectMapper.writeValueAsBytes(afterProfile);

    //    then
    mockMvc.perform(multipart("/api/users/{userId}/profile", failUserId)
                    .file(new MockMultipartFile(
                            "profileUpdateRequest",
                            "",
                            "application/json",
                            beforeRequest))
                    .with(re -> {
                      re.setMethod("PUT");
                      return re;
                    })
                    .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isBadRequest());
  }

  @Test
  void 마이_username_수정() throws Exception {
    String testEmail = "testsignup@test.co.kr";
    String testPassword = "testSignup@1";
    String username = "afterUsername";

    String request = sign_객체_생성(testEmail, testPassword);

    //    when
    회원가입(request);
    ResultActions resultActions = 로그인(request);

    String responseJson = resultActions.andReturn().getResponse().getContentAsString();
    long userId = objectMapper.readTree(responseJson).get("id").asLong();

    UsernameParam usernameParam = new UsernameParam(username);
    String afterUsername = objectMapper.writeValueAsString(usernameParam);

    //    then
    mockMvc.perform(patch("/api/users/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(afterUsername))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.username").value(username)
            );
  }

  private ResultActions 회원가입(String request) throws Exception {
    return mockMvc.perform(post("/api/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request));
  }

  private String sign_객체_생성(String testEmail, String testPassword) throws JsonProcessingException {
    SignUpRequest signUpRequest = new SignUpRequest(testEmail, testPassword);

    return objectMapper.writeValueAsString(signUpRequest);
  }

  private ResultActions 로그인(String request) throws Exception {
    return mockMvc.perform(post("/api/users/signin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request));
  }
}
