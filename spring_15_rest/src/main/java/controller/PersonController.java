package controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.PersonDAO;
import dto.PersonDTO;




/*
API를 통해 프로그램들은 의사소통을 한다. 그리고 REST는 개발자가 API를 만들 때 준수해야 할 규칙들이다. 
이런 규칙들을 통해 특정 URL에 접근해서 데이터들을 가져올 수 있다.

REpresentational State Transfer(표현 상태 전이,REST)
REST API는 크게 리소스, 메서드, 메세지로 이루어져 있다.
예" 이름이 Terry인 사용자를 생성한다"라는 호출이 있을 때
-리소스 : "사용자" -> http://myrest/users라는 형태의 URI
-메소드 : "생성한다"라는 행위 -> HTTP POST메서드
-메세지 이름 : 이름이 Terry인 사용자 -> 생성하고자 하는 
                     사용자의 디테일한 내용은 JSON문서를 이용해서 표현
                     
GET(/myrest/person/list)     		자료의 조회용
GET(/myrest/person/list/3)   		자료의 조회용
DELETE(/myrest/person/3)     		자료의 삭제
POST(/myrest/person/)+데이터    		자료의 등록
PUT(/myrest/person/update)+데이터 	       자료의 수정  

@PathVariable-URI의 경로에서 원하는 데이터를 추출하는 용도로 사용
@RequestBody - 전송된 JSON데이터를 객체로 변환해 주는 어노테이션으로 
@ModeAndView와 유사한 역할을 하지만 JSON에서 사용된다는 점이 차이                     
 */



@Controller
public class PersonController {
	private PersonDAO dao;
	
	public PersonController() {
		
	}
	
	public void setDao(PersonDAO dao) {
		this.dao = dao;
	}
	
	//http://127.0.0.1:8090/myrest/person/list
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public @ResponseBody List<PersonDTO> listMethod(){
		return dao.list();		
	}//end listMethod()
	
	//http://127.0.0.1:8090/myrest/person/list/2
	@RequestMapping(value="/list/{ss}", method = RequestMethod.GET)
	public @ResponseBody PersonDTO listMethod(@PathVariable("ss") int num){
		return dao.list(num);		
	}//end listMethod()
	
	
	//http://127.0.0.1:8090/myrest/person/list/2
	@RequestMapping(value="/list/{num}/{name}",method = RequestMethod.GET)
	public @ResponseBody PersonDTO listMethod(@PathVariable("num") int num, @PathVariable("name") String name) {
		PersonDTO dto = new PersonDTO();
		dto.setName(name);
		dto.setNum(num);
		return dao.list(dto);
	}
	
	
	
/*	클라이언트 측에서 (JSP)에서 json타입으로 요청했을 시의 ajax구현 
	$.ajax({
		dataType:'text',
		type:'POST',
		data:JSON.srtingify({"id":"user","pass":"4253","name":"야옹이"}),
		uri:'/',
		success:function(res){}
		
	})*/
	//http://127.0.0.1:8090/myrest/person/
	@RequestMapping(value="/",method = RequestMethod.POST)
	public ResponseEntity<String> insertMethod(@RequestBody PersonDTO dto){
		ResponseEntity<String> entity = null;
		try {
		dao.register(dto);
		entity= new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			System.out.println(e.toString());
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}//end class
