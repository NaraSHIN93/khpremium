<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- <script type="text/javascript" src="js/sockjs.js"></script> HTML5 부터 사용안함-->
<script type="text/javascript">
	//웹 소켓 객체를 저장할 변수를 선언
	var websocket;
	$(document).ready(function(){
		//입장 버튼을 클릭했을때 이벤트 처리
		$("#enterBtn").on('click',function(){
			//웹 소켓 연결
			websocket = new WebSocket("ws://localhost:8090/mywebsocket/chatws.do");
			//웹 소켓 이벤트 처리
			websocket.onopen = onOpen;
			websocket.onmessage = onMessage;
			websocket.onclose = onClose;
		});
		
		//전송 버튼을 누를 때 이벤트 처리
		$('#sendBtn').on('click',function(){
			var nick = $('#nickname').val();
			var msg = $('#message').val();
			
			//메세지 전송
			websocket.send(nick + ":" + msg);
			
			//메세지 입력창 초기화
			$('#message').val('');
		});
		
		//message 창에서 enter를 눌렀을 때도 메시지를 전송
		//키보드를 누를 때 이벤트 처리
		$('#message').keypress(function(e){
			if(e.keyCode == 13){
				$('#sendBtn').click();
			}
		});
	});
	
	//WebSocket이 연결된 경우 호출되는 함수
	function onOpen(evt){
		console.log("웹 소켓에 연결 성공");
	}
	
	//WebSocket 이 연결 해제된 경우 호출되는 함수
	function onClose(evt){
		console.log("웹소켓에 연결 해제");
	}
	
	//서베어서 메시지가 왔을 때 호출되는 함수
	function onMessage(evt){
		//서버가 전송한 메시지 가져오기
		var data = evt.data;
		$("#chatMessageArea").append(data + "<br/>");
	}
</script>
</head>
<body>
<body>
	<span>이름:</span>
	<input type="text" id="nickname" />
	<input type="button" id="enterBtn" value="입장" />
	<input type="button" id="exitBtn" value="나가기" />

	<h1>채팅 영역</h1>
	<div id="chatArea">
		<div id="chatMessageArea"></div>
	</div>
	<span>전송할 메시지:</span>
	<input type="text" id="message" />
	<input type="button" id="sendBtn" value="전송" />
</body>
</html>