package com.d1m.wechat.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

/**
 * @desc：Websocket处理类
 */
public class WebSocketEndPoint implements WebSocketHandler {

	private static Logger log = LoggerFactory.getLogger(WebSocketEndPoint.class);

    public static final Map<String, WebSocketSession> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<String, WebSocketSession>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        String openId = (String) session.getAttributes().get("openId");
        if (userSocketSessionMap.get(openId) == null) {
            userSocketSessionMap.put(openId, session);
        }
    }

	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		if (message.getPayloadLength() == 0)
			return;
		log.info("message:" + message.getPayload().toString());
	}

	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
        cleanupSession(session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
        cleanupSession(session.getId());
	}

	private void cleanupSession(String sessionId) {
        for (Entry<String, WebSocketSession> entry : userSocketSessionMap.entrySet()) {
            WebSocketSession current = entry.getValue();
            if (current != null && current.getId().equals(sessionId)) {
                userSocketSessionMap.remove(entry.getKey());
                break;
            }
        }
    }

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	public static void sendMessageToUser(String openId, String message)
			throws IOException {
		WebSocketSession session = userSocketSessionMap.get(openId);
		TextMessage msg = new TextMessage(message);
		if (session != null && session.isOpen()) {
			session.sendMessage(msg);
		}
	}
}
