package net.dndlti;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//spring 에서 controller 로 자동등록
@RestController
public class HelloWorldController {
	
	// url 매핑 - @RequestMapping 과 @GetMapping 은 동일 기능 
	//브라우저 주소창에 http://localhost:8080/helloworld 입력하면
	//웹 브라우저에 Hello World! 가 리턴된다.
	@GetMapping("/helloworld")
	public String helloWorld() {
		return "Hello World!";
	}
}
