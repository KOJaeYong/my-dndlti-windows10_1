package net.dndlti.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.dndlti.domain.Answer;
import net.dndlti.domain.AnswerRepository;
import net.dndlti.domain.Question;
import net.dndlti.domain.QuestionRepository;
import net.dndlti.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
  
  //AnswerRepository 인터페이스는 데이터베이스 테이블 Answer 와 연동한다.
  //@Autowired 어노테이션은 
  //스프링에서 자동으로 의존관계를 타입에 맞춰서 연결하도록 시킴
  //Marks a constructor, field, setter method or 
  //config method as to be autowired 
  //by Spring's dependency injection facilities.
  @Autowired
  public AnswerRepository answerRepository; 
  
  @Autowired
  public QuestionRepository questionRepository;
  
  @PostMapping("")
  public String create(@PathVariable Long questionId,
  String contents, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      /*이 코드는 다음과 같은 오류가 발생한다.
      There was an unexpected error 
      (type=Method Not Allowed, status=405).
      Request method 'POST' not supported*/
      /*return "/users/loginForm";*/
      
      //위의 오류 해결하기 위해 수정 
      return "redirect:/users/loginForm";
    }
    User loginUser = 
    HttpSessionUtils.getUserFromSession(session);
    
    Question questionID = 
    questionRepository.findOne(questionId);
    System.out.println("나의 ~ questionID: " + questionID);
    System.out.println("나의~ qID: " + questionRepository.findOne(questionId));
    Answer answer = new Answer(loginUser, questionID, 
    contents);
    System.out.println("나의~ answer: " + answer);
    answerRepository.save(answer);
    System.out.println("나의~ answerRepository.save(answer): "
    + answerRepository.save(answer));
    
    return String.format("redirect:/questions/%d", 
    questionId);
  }
}
