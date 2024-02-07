package com.example.websocket.idrop;

import com.example.websocket.IdropUtils;
import com.example.websocket.entity.User;
import com.example.websocket.repository.DriverRepository;
import com.example.websocket.repository.ParentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class IdropWebSocketHandler extends TextWebSocketHandler {

    private final ParentRepository parentRepository = ParentRepository.getInstance();
    private final DriverRepository driverRepository = DriverRepository.getInstance();

    private final Map<String, WebSocketSession> sessions; //세션아이디, 세션
    private final Map<String, User> users; //세션아이디, 유저객체
    private final Map<String, String> parentSessionIds; //부모아이디, 부모세션아이디

    private final Map<String, String> set; //짝인 부모와 기사 테스트용


    public IdropWebSocketHandler() {
        sessions = new ConcurrentHashMap<>();
        users = new ConcurrentHashMap<>();
        parentSessionIds = new ConcurrentHashMap<>();
        set = new ConcurrentHashMap<>();

        set.put("driver1", "parent1");
        set.put("driver2", "parent2");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.warn("socket connected {}", session.getId());

        sessions.put(session.getId(), session); //세션 저장

    }

    //양방향 데이터 통신
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        log.warn(textMessage.getPayload());

        IdropMessage message = IdropUtils.getObject(textMessage.getPayload());
        String sender = message.getSender();

        if (sender.startsWith("parent")) {
            log.warn("put parent info {} {}",sender,session.getId());
            parentSessionIds.put(sender, session.getId());
            return;
        }

        String parent = set.get(sender);
        String parentSid = parentSessionIds.get(parent);

        //메시지 전달할 타겟 찾기
        WebSocketSession receiver = sessions.get(parentSid);

        if (receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(IdropUtils.getString(message)));
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

}
