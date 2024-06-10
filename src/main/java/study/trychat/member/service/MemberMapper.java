package study.trychat.member.service;

import study.trychat.member.dto.MemberBase.MemberProfileResponse;
import study.trychat.member.dto.MemberBase.MemberResponse;
import study.trychat.member.dto.SignBase.SingInResponse;
import study.trychat.member.domain.Member;
import study.trychat.member.domain.MemberInfo;

public class MemberMapper {

  public static SingInResponse toSingInResponse(Member member) {
    return new SingInResponse(member.getId(), member.getMemberInfo().getNickname(),
            member.getMemberInfo().getGreetings(), member.getMemberInfo().getProfileImg(),
            member.getMemberInfo().getBackgroundImg(), member.getMemberInfo().getProfileImgPath(),
            member.getRole().name());
  }

  public static Member toMemberEntity(String email, String password, String nickname, String username) {
    return Member.init(email, password, nickname, username);
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
