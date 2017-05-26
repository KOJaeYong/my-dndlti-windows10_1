package net.dndlti.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import net.dndlti.domain.QuestionRepository;

@Controller
public class HomeController {
	
	//@GetMapping("") - "" 은 /(루트)를 의미
	//http://localhost:8080 을 브라우저 주소창에 입력
	//templates 디렉토리의 index.html 로 이동
	/*@GetMapping("")
	public String home() {
		return "index";
	}*/
	
  //Marks a constructor, field, setter method or 
  //config method as to be autowired 
  //by Spring's dependency injection facilities.
  //질문하기 테이블 question 레코드들을 읽어 들임 
	@Autowired
	private QuestionRepository questionRepository;
  
  //질문하기 리스트를 읽어 들여 model 에 저장한 다음 
	//src/main/resources/templates/index.html 페이지로 전달
	@GetMapping("")
	public String home(Model model) {
	  model.addAttribute("questions", 
	  questionRepository.findAll());
	  return "index";
	}
}
