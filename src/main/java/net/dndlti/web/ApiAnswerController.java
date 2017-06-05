package net.dndlti.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.dndlti.domain.Answer;
import net.dndlti.domain.AnswerRepository;
import net.dndlti.domain.Question;
import net.dndlti.domain.QuestionRepository;
import net.dndlti.domain.Result;
import net.dndlti.domain.User;

//Json 데이터를 처리할 수 있는 컨트롤러로 지정하기 위해 
//@RestController 어노테이션 사용
@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
  
  @Autowired
  public QuestionRepository questionRepository;
  
  //AnswerRepository 인터페이스는 데이터베이스 테이블 Answer 와 연동한다.
  //@Autowired 어노테이션은 
  //스프링에서 자동으로 의존관계를 타입에 맞춰서 연결하도록 시킴
  //Marks a constructor, field, setter method or 
  //config method as to be autowired 
  //by Spring's dependency injection facilities.
  @Autowired
  public AnswerRepository answerRepository; 
  
  @PostMapping("")
  public Answer create(@PathVariable Long questionId, 
  String contents, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      return null;
    }
    User loginUser = 
    HttpSessionUtils.getUserFromSession(session);
    Question question = questionRepository.findOne(questionId);
    
    Answer answer = new Answer(loginUser, question, contents);
    
    /*질문 목록에 답변 수 보여주기를 할 메소드 추가*/
    question.addAnswer();
    
    return answerRepository.save(answer);
  }
  
  /* 6-1 Ajax 를 활용한 답변 추가 1 에서 주석처리
  @PostMapping("")
  public String create(@PathVariable Long questionId,
  String contents, HttpSession session) {
    if (!HttpSessionUtils.isLoginUser(session)) {
      이 코드는 다음과 같은 오류가 발생한다.
      There was an unexpected error 
      (type=Method Not Allowed, status=405).
      Request method 'POST' not supported
      return "/users/loginForm";
      
      //위의 오류 해결하기 위해 수정 
      return "redirect:/users/loginForm";
      6-1. Ajax 를 활용한 답변 추가 1 에서 수정
      return "/user/login";
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
  }*/
  
  //세션 로그인 한 사용자가 자신이 쓴 글을 삭제하는 기능 구현
  @DeleteMapping("/{id}")
  public Result delete(@PathVariable Long questionId, 
  @PathVariable Long id, HttpSession session) {
    System.out.println("deleteMapping ~ questionId: " 
    + questionId + ", id: " + id);
    if (!HttpSessionUtils.isLoginUser(session)) {
      return Result.fail("로그인해야 합니다.");
    }
    Answer answer = answerRepository.findOne(id);
    User loginUser = 
    HttpSessionUtils.getUserFromSession(session);
    if (!answer.isSameWriter(loginUser)) {
      return Result.fail("자신의 글만 삭제할 수 있습니다.");
    }
    answerRepository.delete(id);
    /*Question question = questionRepository.findOne(questionId);
    questionRepository.save(question);*/
    
    /*질문에 대한 답변을 삭제할 때마다 -1씩 감소*/ 
    Question question = questionRepository.findOne(questionId);
    question.deleteAnswer();
    questionRepository.save(question);
    
    return Result.ok();
  }
}
