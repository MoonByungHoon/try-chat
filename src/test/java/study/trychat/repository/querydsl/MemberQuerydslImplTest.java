package study.trychat.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
import study.trychat.dto.QMemberRequest;
import study.trychat.entity.Member;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static study.trychat.entity.QMember.member;
import static study.trychat.entity.QMemberInfo.memberInfo;

@SpringBootTest
@Transactional
class MemberQuerydslImplTest {

  @Autowired
  private EntityManager em;
  private JPAQueryFactory queryFactory;
  private Validator validator;

  private static String TEST_USERNAME = "test@try-chat.co.kr";
  private static String TEST_PASSWORD = "try-chat";

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
  void 회원가입_통과_테스트() {
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
            () -> assertEquals(findMember.getUsername(), "signUpTest@try-chat.co.kr"),
            () -> assertEquals(findMember.getPassword(), "try-chat")
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "이메일_양식이_아닌_문자열"})
  @DisplayName("signUp username unSuccess test and validate test")
  void 잘못된_Username으로_회원가입_실패_및_검증_테스트(String username) {
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
  void 잘못된_Password로_회원가입_실패_및_검증_테스트(String password) {
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
  void 로그인_성공_테스트() {
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
                    member.username
            ))
            .from(member)
            .where((member.username.eq(username)
                    .and(member.password.eq(password))
                    .and(member.id.eq(memberInfo.id))))
            .fetchOne();

    //then

    assertAll(
            () -> assertEquals(memberRequest.getUsername(), username),
            () -> assertEquals(memberRequest.getPassword(), null)
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

    MemberRequest memberRequest = queryFactory.select(new QMemberRequest(
                    member.id,
                    memberInfo.nickname,
                    memberInfo.greetings,
                    memberInfo.profileImg,
                    memberInfo.profileImgPath,
                    member.username
            ))
            .from(member)
            .where(member.id.eq(findMember.getId()))
            .fetchOne();

    //then
    assertAll(
            () -> assertEquals(memberRequest.getUsername(), TEST_USERNAME),
            () -> assertEquals(memberRequest.getPassword(), null)
    );
  }
}
