package study.trychat.friend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.friend.domain.FriendStatus;
import study.trychat.friend.dto.FriendBase.FriendNicknameUpdateRequest;
import study.trychat.member.dto.SignBase;
import study.trychat.member.dto.UsernameParam;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static study.trychat.member.domain.MemberInfoDefault.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class FriendShipControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void 친구_추가() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";
    String friendNickname = friendEmail1.split("@")[0];
    UsernameParam usernameParam = new UsernameParam(friendEmail1.split("@")[0]);
    String friendUsername = objectMapper.writeValueAsString(usernameParam);

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    //    then
    mockMvc.perform(post("/api/friends/add")
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(friendUsername))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$[0].memberId").value(userId),
                    jsonPath("$[0].friendId").value(friendId),
                    jsonPath("$[0].nickname").value(friendNickname),
                    jsonPath("$[0].greetings").value(" "),
                    jsonPath("$[0].profileImg").value(PROFILE_IMG.getValue()),
                    jsonPath("$[0].backgroundImg").value(BACKGROUND_IMG.getValue()),
                    jsonPath("$[0].profileImgPath").value(PROFILE_PATH.getValue()),
                    jsonPath("$[0].friendStatus").value(FriendStatus.FRIEND.name())
            );
  }

  @Test
  void 친구추가_실패() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";
    String failFriendUsername = objectMapper.writeValueAsString("failFriendUsername");

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    Long userId = id추출(resultActions);

    //    then
    mockMvc.perform(post("/api/friends/add")
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(failFriendUsername))
            .andExpect(status().isBadRequest());
  }

  @Test
  void 친구_프로필_조회() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";
    String friendNickname = friendEmail1.split("@")[0];

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    친구추가(userId, friendEmail1);

    //    then
    mockMvc.perform(get("/api/friends/{friendId}/profile", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.memberId").value(userId),
                    jsonPath("$.friendId").value(friendId),
                    jsonPath("$.nickname").value(friendNickname),
                    jsonPath("$.greetings").value(" "),
                    jsonPath("$.profileImg").value(PROFILE_IMG.getValue()),
                    jsonPath("$.backgroundImg").value(BACKGROUND_IMG.getValue()),
                    jsonPath("$.profileImgPath").value(PROFILE_PATH.getValue()),
                    jsonPath("$.friendStatus").value(FriendStatus.FRIEND.name())
            );
  }

  @Test
  void 친구_프로필_조회_실패_친구가_아님() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    //    then
    mockMvc.perform(get("/api/friends/{friendId}/profile", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  @Test
  void 친구_닉네임_수정() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";
    String afterNickname = friendEmail1.split("@")[0];

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    친구추가(userId, friendEmail1);

    FriendNicknameUpdateRequest friendNicknameUpdateRequest =
            new FriendNicknameUpdateRequest(friendId, friendEmail1.split("@")[0]);
    String afterFriendNickname = objectMapper.writeValueAsString(friendNicknameUpdateRequest);

    //    then
    mockMvc.perform(get("/api/friends/{friendId}/profile", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(afterFriendNickname))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.nickname").value(afterNickname)
            );
  }

  @Test
  void 친구_프로필_수정_실패_친구아님() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);

    Long userId = id추출(resultActions);
    Long friendId = 10000000L;

    FriendNicknameUpdateRequest friendNicknameUpdateRequest =
            new FriendNicknameUpdateRequest(friendId, friendEmail1.split("@")[0]);
    String afterFriendNickname = objectMapper.writeValueAsString(friendNicknameUpdateRequest);

    //    then
    mockMvc.perform(get("/api/friends/{friendId}/profile", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(afterFriendNickname))
            .andExpect(status().isBadRequest());
  }

  @Test
  void Best친구_변경() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    친구추가(userId, friendEmail1);

    //    then
    mockMvc.perform(patch("/api/friends/{friendId}/best", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$[0].friendStatus").value(FriendStatus.BEST_FRIEND.name())
            );
  }

  @Test
  void Best친구_변경_실패_이미_Best_관계() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    친구추가(userId, friendEmail1);
    Best관계_변경(userId, friendId);

    //    then
    mockMvc.perform(patch("/api/friends/{friendId}/best", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                    status().is(409));
  }

  @Test
  void 친구_차단_변경() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    친구추가(userId, friendEmail1);

    //    then
    mockMvc.perform(patch("/api/friends/{friendId}/block", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$[0].friendStatus").value(FriendStatus.BLOCK.name())
            );
  }

  @Test
  void 친구_차단_변경_실패_이미_차단_상태() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    친구추가(userId, friendEmail1);

    친구차단(userId, friendId);

    //    then
    mockMvc.perform(patch("/api/friends/{friendId}/block", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(409));
  }

  @Test
  void 친구삭제() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = id추출(friendResultActions);

    친구추가(userId, friendEmail1);

    //    then
    mockMvc.perform(delete("/api/friends/{friendId}", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  void 친구삭제_실패() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend = sign_객체_생성(friendEmail1, password);
    회원가입(mainMember);
    회원가입(friend);

    ResultActions resultActions = 로그인(mainMember);
    ResultActions friendResultActions = 로그인(friend);

    Long userId = id추출(resultActions);
    Long friendId = 100000L;

    친구추가(userId, friendEmail1);

    //    then
    mockMvc.perform(delete("/api/friends/{friendId}", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  @Test
  void 친구리스트_조회() throws Exception {
    //    given
    String mainEmail = "mainemail@test.co.kr";
    String password = "testSignup@1";
    String friendEmail1 = "friendmail1@test.co.kr";
    String friendEmail2 = "friendmail2@test.co.kr";
    String friendEmail3 = "friendmail3@test.co.kr";
    String friendEmail4 = "friendmail4@test.co.kr";

    //    when
    String mainMember = sign_객체_생성(mainEmail, password);
    String friend1 = sign_객체_생성(friendEmail1, password);
    String friend2 = sign_객체_생성(friendEmail2, password);
    String friend3 = sign_객체_생성(friendEmail3, password);
    String friend4 = sign_객체_생성(friendEmail4, password);
    회원가입(mainMember);
    회원가입(friend1);
    회원가입(friend2);
    회원가입(friend3);
    회원가입(friend4);

    ResultActions resultActions = 로그인(mainMember);
    Long userId = id추출(resultActions);

    친구추가(userId, friendEmail1);
    친구추가(userId, friendEmail2);
    친구추가(userId, friendEmail3);
    친구추가(userId, friendEmail4);

    //    then
    mockMvc.perform(get("/api/users/{userId}/friends", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                    status().isOk(),
                    jsonPath("$.length()").value(4)
            );
  }

  private void 친구추가(Long userId, String friendEmail) throws Exception {
    UsernameParam usernameParam = new UsernameParam(friendEmail.split("@")[0]);
    String friendUsername = objectMapper.writeValueAsString(usernameParam);

    mockMvc.perform(post("/api/friends/add")
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(friendUsername))
            .andExpect(status().isOk());
  }

  private ResultActions 회원가입(String request) throws Exception {
    return mockMvc.perform(post("/api/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isOk());
  }

  private String sign_객체_생성(String testEmail, String testPassword) throws JsonProcessingException {
    SignBase.SignUpRequest signUpRequest = new SignBase.SignUpRequest(testEmail, testPassword);

    return objectMapper.writeValueAsString(signUpRequest);
  }

  private ResultActions 로그인(String request) throws Exception {
    return mockMvc.perform(post("/api/users/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isOk());
  }

  private Long id추출(ResultActions resultActions) throws JsonProcessingException, UnsupportedEncodingException {
    String responseJson = resultActions.andReturn().getResponse().getContentAsString();
    return objectMapper.readTree(responseJson).get("id").asLong();
  }

  private void 친구차단(Long userId, Long friendId) throws Exception {
    mockMvc.perform(patch("/api/friends/{friendId}/block", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  private void Best관계_변경(Long userId, Long friendId) throws Exception {
    mockMvc.perform(patch("/api/friends/{friendId}/best", friendId)
                    .header("userId", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }
}
