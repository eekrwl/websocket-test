package com.example.websocket.test;

import com.example.websocket.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions; //세션아이디, 세션

    public WebSocketHandler() {
        sessions = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("connection opened {}",session.getId());

        String sessionId = session.getId();
        sessions.put(sessionId, session); //세션 저장

        Message message = Message.builder()
                .sender(sessionId)
                .receiver("all")
                .build();
        message.newConnect();

        sessions.values().forEach(s -> {
            try {
                if (!s.getId().equals(sessionId)) {
                    s.sendMessage(new TextMessage(Utils.getString(message)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //양방향 데이터 통신
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {

        log.warn(textMessage.getPayload());
        Message message = Utils.getObject(textMessage.getPayload());
        message.setSender(session.getId());

        //메시지 전달할 타겟 상대방 찾기
        WebSocketSession receiver = sessions.get(message.getReceiver());

        //타겟이 존재하고 연결된 상태라면, 메시지 전송
        if (receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(Utils.getString(message)));
        }
    }

    //소켓 통신 에러
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    //소켓 연결 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String sessionId = session.getId();

        //세션 저장소에서 연결이 끊긴 사용자를 삭제한다.
        sessions.remove(sessionId);

        final Message message = new Message();
        message.closeConnect();
        message.setSender(sessionId);

        //다른 사용자들에게 누군가 접속이 끊겼다고 알려준다.
        sessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(Utils.getString(message)));
            } catch (Exception e) {
                e.getStackTrace();
            }
        });
        log.info("BYE closed {}", session.getId());
    }

}
