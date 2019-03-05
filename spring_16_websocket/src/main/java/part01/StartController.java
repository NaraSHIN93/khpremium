package part01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartController {
	
	
	//http://localhost:8090/mywebsocket/start.do
	@RequestMapping("/start.do")
	public String process() {
		return "first";
	}
	
}
