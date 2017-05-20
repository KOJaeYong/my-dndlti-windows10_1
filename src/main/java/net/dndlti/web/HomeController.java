package net.dndlti.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	//@GetMapping("") - "" 은 /(루트)를 의미
	//http://localhost:8080 을 브라우저 주소창에 입력
	//templates 디렉토리의 index.html 로 이동
	@GetMapping("")
	public String home() {
		return "index";
	}
}
