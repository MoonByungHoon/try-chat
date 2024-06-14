package study.trychat.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static study.trychat.chat.ChatBase.*;

public sealed interface ChatBase permits CreateChatRoomRequest, ChatMessageRequest, JoinRoomRequest,
        UpdateRoomNameRequest, UpdateNickname {
  //  이름이 포함된 메세지를 모델링 하기 위해서 만들어진 객체

  record CreateChatRoomRequest(

          @NotBlank
          @Schema(description = "채팅방 이름", example = "JAVA 공부할 사람 모여라")
          String name,

          @NotNull
          @Schema(description = "방 생성자 id", example = "1")
          Long hostId,

          @NotBlank
          @Schema(description = "채팅방에 사용할 닉네임", example = "울고있는 문병훈")
          String nickname
  ) implements ChatBase {
  }

  record JoinRoomRequest(
          @NotNull
          @Schema(description = "채팅방 id", example = "1")
          Long roomId,

          @NotNull
          @Schema(description = "채팅방 참가할 사용자 id", example = "1")
          Long memberId,

          @NotBlank
          @Schema(description = "채팅방에 사용될 id", example = "1")
          String nickname
  ) implements ChatBase {
  }

  record ChatMessageRequest(
          Long chatRoomId,
          Long writerId,
          String message
  ) implements ChatBase {
  }

  record UpdateRoomNameRequest(
          @NotNull
          @Schema(description = "대상의 Room Id", example = "1")
          Long roomId,

          @NotBlank
          @Schema(description = "변경할 방이름", example = "JAVA 어렵다")
          String roomName,

          @NotNull
          @Schema(description = "호스트 확인을 위해 사용될 변경 요청 사용자 id", example = "1")
          Long memberId
  ) implements ChatBase {
  }

  record UpdateNickname(

          @NotNull
          @Schema(description = "대상의 Room Id", example = "1")
          Long roomId,

          @NotNull
          @Schema(description = "변경할 사용자 확인할 id", example = "1")
          Long memberId,

          @NotBlank
          @Schema(description = "변경할 채팅방 닉네임", example = "변경되는 아이디")
          String nickname
  ) implements ChatBase {
  }
}
