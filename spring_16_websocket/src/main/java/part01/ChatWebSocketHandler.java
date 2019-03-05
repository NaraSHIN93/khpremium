package part01;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler{
	public static List<WebSocketSession> list = new ArrayList<>();
	
	public ChatWebSocketHandler() {	}
	
	// 클라이언트가 연결되었을때 호출되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println(session.getId()+"연결 됨");
		// 리스트에 추가
		list.add(session);
	}
	
	//클라이언트가 메시지를 보냈을때 호출되는 메소드
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		//전송된 메시지를 모든 클라이언트에게 전송
		String msg = (String) message.getPayload();
		for(WebSocketSession socket : list) {
			//메시지 생성
			WebSocketMessage<String> sentMsg = new TextMessage(msg);
			//각 세션에게 메시지를 전송
			socket.sendMessage(sentMsg);
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println(session.getId() + "연결 종료됨");
		list.remove(session);
	}
}
