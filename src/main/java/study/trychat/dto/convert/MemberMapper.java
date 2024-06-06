package study.trychat.dto.convert;

import study.trychat.dto.MemberBase.MemberProfileResponse;
import study.trychat.dto.MemberBase.MemberResponse;
import study.trychat.dto.SignBase.SignUpRequest;
import study.trychat.dto.SignBase.SingInResponse;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;

public class MemberMapper {

  public static SingInResponse toSingInResponse(Member member) {
    return new SingInResponse(member.getId(), member.getMemberInfo().getNickname(),
            member.getMemberInfo().getGreetings(), member.getMemberInfo().getProfileImg(),
            member.getMemberInfo().getBackgroundImg(), member.getMemberInfo().getProfileImgPath());
  }

  public static Member toMemberEntity(SignUpRequest signUpRequest,
                                      String nickname,
                                      String username) {
    return Member.init(signUpRequest.email(), signUpRequest.password(), nickname, username);
  }

  public static MemberResponse toMemberResponse(Member findMember) {
    return new MemberResponse(findMember.getId(), findMember.getEmail());
  }

  public static MemberProfileResponse toMemberProfileResponse(MemberInfo findMemberInfo) {
    return new MemberProfileResponse(findMemberInfo.getId(), findMemberInfo.getNickname(),
            findMemberInfo.getGreetings(), findMemberInfo.getProfileImg(), findMemberInfo.getBackgroundImg(),
            findMemberInfo.getProfileImgPath());
  }
}
