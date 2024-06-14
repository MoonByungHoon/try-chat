package study.trychat.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.chat.ChatBase.CreateChatRoomRequest;
import study.trychat.chat.ChatBase.UpdateNickname;
import study.trychat.chat.ChatBase.UpdateRoomNameRequest;
import study.trychat.chat.ChatRoom;
import study.trychat.chat.service.ChatRoomService;
import study.trychat.common.exception.ErrorResponse;

import java.util.List;

import static study.trychat.chat.ChatBase.JoinRoomRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {
  private final ChatRoomService chatRoomService;

  @Operation(summary = "채팅방 생성", description = "구독에 필요한 채팅방 생성")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "생성 성공",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChatRoom.class))),
          @ApiResponse(responseCode = "400", description = "생성 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/room")
  public ResponseEntity<ChatRoom> createChatRoom(
          @Valid @RequestBody CreateChatRoomRequest createChatRoomRequest) {

    return ResponseEntity.ok(chatRoomService.createChatRoom(createChatRoomRequest.name(),
            createChatRoomRequest.hostId(), createChatRoomRequest.nickname()));
  }

  @Operation(summary = "채팅방 참가", description = "구독에 필요할 채팅방 참가")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "참가 성공",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChatRoom.class))),
          @ApiResponse(responseCode = "400", description = "참가 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/join/room")
  public ResponseEntity<ChatRoom> joinChatRoom(
          @Valid @RequestBody JoinRoomRequest joinRoomRequest) {

    return ResponseEntity.ok(chatRoomService.joinRoom(joinRoomRequest));
  }

  @Operation(summary = "채팅방 리스트 조회", description = "참가한 채팅방 리스트")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChatRoom.class))),
          @ApiResponse(responseCode = "400", description = "조회 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/rooms/{memberId}")
  public ResponseEntity<List<ChatRoom>> getRoomList(
          @Schema(description = "로그인되어있는 대상 id", example = "1")
          @PathVariable Long memberId) {
    return ResponseEntity.ok(chatRoomService.getRoomList(memberId));
  }

  @Operation(summary = "방이름 변경", description = "생성된 방 이름 변경")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "변경 성공",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChatRoom.class))),
          @ApiResponse(responseCode = "400", description = "변경 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PatchMapping("/room")
  public ResponseEntity<List<ChatRoom>> updateRoomName(
          @RequestBody UpdateRoomNameRequest updateRoomNameRequest
  ) {
    return ResponseEntity.ok(chatRoomService.updateRoomName(updateRoomNameRequest.roomId(),
            updateRoomNameRequest.roomName(), updateRoomNameRequest.memberId()));
  }

  @Operation(summary = "채팅방 리스트 조회", description = "참가한 채팅방 리스트")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChatRoom.class))),
          @ApiResponse(responseCode = "400", description = "조회 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PatchMapping("/room/nickname")
  public ResponseEntity<ChatRoom> updateNickname(UpdateNickname updateNickname) {

    return ResponseEntity.ok(chatRoomService.updateNickname(updateNickname.roomId(), updateNickname.memberId(),
            updateNickname.nickname()));
  }

  @Operation(summary = "채팅방 삭제", description = "생성했던 채팅방 삭제")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "삭제 성공",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChatRoom.class))),
          @ApiResponse(responseCode = "400", description = "삭제 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/room/{roomId}")
  public ResponseEntity<List<ChatRoom>> removeRoom(
          @Schema(description = "삭제 대상 방 id", example = "1")
          @PathVariable Long roomId,
          @Schema(description = "생성했던 사용자 확인하기 위한 요청 사용자 id", example = "1")
          @RequestHeader Long memberId) {

    return ResponseEntity.ok(chatRoomService.removeRoom(roomId, memberId));
  }

  @Operation(summary = "참가했던 채팅방 나가기", description = "참가 중인 방 나가기")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "방 나가기 성공",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ChatRoom.class))),
          @ApiResponse(responseCode = "400", description = "방 나가기 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/room/{roomId}/out")
  public ResponseEntity<List<ChatRoom>> outRoom(
          @Schema(description = "방 나가기 대상 방 id", example = "1")
          @PathVariable Long roomId,
          @Schema(description = "참가중인 사용자 확인하기 위한 요청 사용자 id", example = "1")
          @RequestHeader Long memberId
  ) {
    return ResponseEntity.ok(chatRoomService.outRoom(roomId, memberId));
  }
}
