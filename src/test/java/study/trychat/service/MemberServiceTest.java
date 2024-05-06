package study.trychat.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberRequest;
import study.trychat.dto.QMemberAuthenticationDto;
import study.trychat.dto.QMemberRequest;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.custom.CustomDuplicateUsernameException;
import study.trychat.exception.custom.CustomPrimaryKeyMismatchException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static study.trychat.entity.QMember.member;
import static study.trychat.entity.QMemberInfo.memberInfo;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  private EntityManager em;
  private JPAQueryFactory queryFactory;
  private Validator validator;

  private static String TEST_USERNAME = "test@try-chat.co.kr";
  private static String TEST_PASSWORD = "try-chat";
  private static String ENTITY_NOT_FOUND = "일치하는 회원이 없습니다.";
  private static String DUPLICATE_USER = "이미 가입된 회원입니다.";
  private static String PRIMARY_KEY_MISMATCH = "대상이 일치하지 않습니다.";

  @BeforeEach
  public void before() {
    queryFactory = new JPAQueryFactory(em);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  public void init() {
    MemberAuthenticationDto authenticationDto =
            new MemberAuthenticationDto(TEST_USERNAME, TEST_PASSWORD);

    em.persist(authenticationDto.toEntity());
  }

  @Test
  @DisplayName("signUp success test")
  void 회원가입_통과() {
    //    given
    String username = "signUpTest@try-chat.co.kr";
    String password = "try-chat";

    //    when
    MemberAuthenticationDto authenticationDto = new MemberAuthenticationDto(username, password);
    Member member = authenticationDto.toEntity();

    em.persist(member);

    Member findMember = em.createQuery("select m from Member m " +
                    "where m.username = :username and m.password = :password", Member.class)
            .setParameter("username", username)
            .setParameter("password", password)
            .getSingleResult();

    //    then
    assertAll(
            () -> assertEquals(findMember.getUsername(), username),
            () -> assertEquals(findMember.getPassword(), password)
    );
  }

  @Test
  @DisplayName("signUp duplicate user test")
  void 중복_회원가입_실패() {
    //    given
    String duplicateUsername = TEST_USERNAME;

    //    when
    init();

    Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
            .setParameter("username", TEST_USERNAME)
            .getSingleResult();

    //    then
    assertThrows(CustomDuplicateUsernameException.class,
            () -> checkForDuplicateUsername(duplicateUsername, findMember.getUsername()));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "이메일_양식이_아닌_문자열"})
  @DisplayName("signUp username unSuccess test and validate test")
  void 잘못된_Username으로_회원가입_실패_및_검증(String username) {
    //    given
    String emailNotMatch = "올바른 이메일 주소를 입력해주세요.";
    String successPassword = "testPassword@1";

    //    when
    MemberAuthenticationDto authenticationDto = new MemberAuthenticationDto(username, successPassword);

    Set<ConstraintViolation<MemberAuthenticationDto>> violations = validator.validate(authenticationDto);

    //    then
    assertAll(
            () -> assertFalse(violations.isEmpty()),
            () -> assertEquals(violations.size(), 1)
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "You can tell them by the twitch in the left hand side of the mouth " +
          "when they see a getter method, there's swift pull on their battleaxe and " +
          "a satisfied cry as another getter is hewn unmercifully from a class which immediately swoons in "})
  @DisplayName("signUp password unSuccess test and validate test")
  void 잘못된_Password로_회원가입_실패_및_검증(String password) {
    //    given
    String successUsername = "try-chat@try-chat.co.kr";

    //    when
    MemberAuthenticationDto authenticationDto = new MemberAuthenticationDto(successUsername, password);

    Set<ConstraintViolation<MemberAuthenticationDto>> violations = validator.validate(authenticationDto);

    //    then
    assertFalse(violations.isEmpty());
  }

  @Test
  @DisplayName("signIn success test")
  void 로그인_성공() {
    //give
    String username = "test1@try-chat.co.kr";
    String password = "try-chat";

    //when
    MemberAuthenticationDto authenticationDto = new MemberAuthenticationDto(username, password);
    Member entity = authenticationDto.toEntity();

    em.persist(entity);

    MemberRequest memberRequest = queryFactory.select(new QMemberRequest(
                    member.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.profileImgPath,
                    member.username,
                    member.password
            ))
            .from(member)
            .where((member.username.eq(username)
                    .and(member.password.eq(password))
                    .and(member.id.eq(memberInfo.id))))
            .fetchOne();

    //then

    assertAll(
            () -> assertEquals(memberRequest.getUsername(), username),
            () -> assertEquals(memberRequest.getPassword(), password)
    );
  }

  @Test
  @DisplayName("findById Querydsl")
  void PK로_회원_조회() {
    //given
    //when
    init();

    Member findMember = em.createQuery("select m from Member m where m.username = :username and m.password = :password", Member.class)
            .setParameter("username", TEST_USERNAME)
            .setParameter("password", TEST_PASSWORD)
            .getSingleResult();

    queryFactory = new JPAQueryFactory(em);

    MemberAuthenticationDto authenticationDto = queryFactory.select(new QMemberAuthenticationDto(
                    member.id,
                    member.username,
                    member.password
            ))
            .from(member)
            .where(member.id.eq(findMember.getId()))
            .fetchOne();

    //then
    assertAll(
            () -> assertNotNull(authenticationDto.getId()),
            () -> assertEquals(authenticationDto.getUsername(), TEST_USERNAME),
            () -> assertEquals(authenticationDto.getPassword(), TEST_PASSWORD)
    );
  }

  @Test
  @DisplayName("updateUser success test")
  void 유저정보_수정() {
    //    given
    String username = "updateTest";
    String password = "updatePassword";

    //    when
    MemberAuthenticationDto authenticationDto = new MemberAuthenticationDto(username, password);

    init();

    Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
            .setParameter("username", TEST_USERNAME)
            .getSingleResult();

    findMember.update(authenticationDto);

    //    then
    assertAll(
            () -> assertEquals(findMember.getUsername(), username),
            () -> assertEquals(findMember.getPassword(), password)
    );
  }

  @Test
  @DisplayName("user success remove test")
  void 유저_탈퇴_성공() {
    //    given
    //    when
    init();

    Member beforeMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
            .setParameter("username", TEST_USERNAME)
            .getSingleResult();

    em.remove(beforeMember);

    //    then
    assertThrows(NoResultException.class,
            () -> em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", TEST_USERNAME)
                    .getSingleResult());
  }

  @Test
  @DisplayName("user pk mismatch unSuccess remove test")
  void PK불일치_회원_탈퇴_실패() {
    //    given
    Long testId = 100L;

    //    when
    init();
    Member findMember =
            em.createQuery("select m from Member m where m.username = :username and m.password = :password", Member.class)
                    .setParameter("username", TEST_USERNAME)
                    .setParameter("password", TEST_PASSWORD)
                    .getSingleResult();

    //    then
    assertThrows(CustomPrimaryKeyMismatchException.class,
            () -> compareUserId(testId, findMember.getId()));
  }

  @Test
  @DisplayName("find user profile test")
  void 회원_프로필_열람() {
    //    given
    //    when
    init();

    MemberAuthenticationDto authenticationDto = new MemberAuthenticationDto(TEST_USERNAME, TEST_USERNAME);
    MemberInfo compareMember = authenticationDto.toEntity().getMemberInfo();

    MemberRequest memberRequest = queryFactory.select(new QMemberRequest(
                    member.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.profileImgPath,
                    member.username,
                    member.password
            ))
            .from(member)
            .where((member.username.eq(TEST_USERNAME)
                    .and(member.password.eq(TEST_PASSWORD))))
            .fetchOne();


    //    then
    assertAll(
            () -> assertEquals(compareMember.getNickname(), memberRequest.getNickname()),
            () -> assertEquals(compareMember.getGreetings(), memberRequest.getGreetings()),
            () -> assertEquals(compareMember.getProfileImg(), memberRequest.getProfileImg()),
            () -> assertEquals(compareMember.getProfileImgPath(), memberRequest.getProfileImgPath())
    );
  }

  private void checkForDuplicateUsername(String username, String duplicateUsername) {
    if (username.equals(duplicateUsername)) {
      throw new CustomDuplicateUsernameException(DUPLICATE_USER);
    }
  }

  private void compareUserId(Long userId, Long findId) {
    if (!(userId.equals(findId))) {
      throw new CustomPrimaryKeyMismatchException(PRIMARY_KEY_MISMATCH);
    }
  }
}
