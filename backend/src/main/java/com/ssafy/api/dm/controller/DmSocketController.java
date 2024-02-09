package com.ssafy.api.dm.controller;

import com.ssafy.api.dm.dto.request.DmSocketRequestMessage;
import com.ssafy.api.dm.dto.response.DmSocketResponseMessage;
import com.ssafy.api.dm.service.DmSocketService;
import com.ssafy.api.dm.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DmSocketController {
  private final RedisUtils redisUtils;
  private final DmSocketService dmSocketService;

  // 디엠방 입장(socket 통신 요청)

  @MessageMapping("/room/{roomId}")
  @SendTo("/topic/room/{roomId}")
  public DmSocketRequestMessage enterDmRoom(DmSocketRequestMessage message, @RequestParam("roomId") String roomId) {
    return message;
  }


  // DM 전송
  @MessageMapping("/sendDm/{roomId}")
  @SendTo("/topic/room/{roomId}")
  public DmSocketResponseMessage sendDm(DmSocketRequestMessage message, @DestinationVariable("roomId") String roomId) {
    DmSocketResponseMessage returnMessage = dmSocketService.sendDm(message, roomId);

    return returnMessage;
  }
}