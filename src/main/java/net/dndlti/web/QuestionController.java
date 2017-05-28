package net.dndlti.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.dndlti.domain.Question;
import net.dndlti.domain.QuestionRepository;
import net.dndlti.domain.User;

//컨트롤러 역할을 하는 클래스
@Controller
@RequestMapping("/questions")
public class QuestionController {
  
  //데이터베이스 테이블과의 연동
  //@Autowired 어노테이션은 
  //스프링에서 자동으로 의존관계를 타입에 맞춰서 연결하도록 시킴
  //Marks a constructor, field, setter method or 
  //config method as to be autowired 
  //by Spring's dependency injection facilities.
  @Autowired
  private QuestionRepository questionRepository;
  
  @GetMapping("/form")
  public String form(HttpSession session) {
    //세션로그인한 사용자만이 질문할 수 있도록 질문하기 기능 구현
    if (!HttpSessionUtils.isLoginUser(session)) {
      return "redirect:/users/loginForm";
      /*모두 동일한 경로로 페이지 이동*/
      /*return "/user/login";*/
      /*return "/users/loginForm";*/
    }
    return "/qna/form";
  }
  
  //src/main/resources/static/qna/form.html 폼에서 
  //input name 속성값을 읽어옴
  @PostMapping("")
  public String create(String title, String contents,
  HttpSession session) {
    //세션로그인 회원만이 글쓰기를 할 수 있도록 기능 구현
    //세션로그인 여부를 확인할 수 있는 메소드
    if (!HttpSessionUtils.isLoginUser(session)) {
      return "redirect:/users/loginForm";
    }
    //세션로그인을 한 User 객체인지를 확인
    User sessionUser = HttpSessionUtils.getUserFromSession(session);
    
    //세션로그인 회원의 id, 질문하기 제목, 내용을 매개변수로 하는 
    //질문하기로 입력한 내용을 insert 할 수 있는 Question 클래스 인스턴스 생성 
    /* 4-5 강의에서 추가 질문하기, 질문목록 기능 구현 
        세션로그인을 한 후 질문하기 한 회원의 userid 조회
    public String getUserId() {
      return userId;
    } */
    /*Question newQuestion = new Question(
    sessionUser.getUserId(), title, contents);*/
    //가급적 User 객체를 사용하여 질문하기의 writer 입력란을 작성하도록 수정
    Question newQuestion = new Question(
    sessionUser, title, contents);
    
    questionRepository.save(newQuestion);
    
    return "redirect:/";
  }
  
  @GetMapping("/{id}")
  public String show(@PathVariable Long id, Model model) {
    model.addAttribute(
    "question", questionRepository.findOne(id));
    return "/qna/show";
  }
  
  //질문 수정 폼으로 이동
  @GetMapping("/{id}/form")
  public String updateForm(
  @PathVariable Long id, Model model) {
    model.addAttribute(
    "question", questionRepository.findOne(id));
    return "/qna/updateForm";
  }
  
  //질문 수정을 한 후 해당 질문이 표시되도록 페이지 이동
  @PutMapping("/{id}")
  public String update(
  @PathVariable Long id, String title, String contents) {
    Question question = questionRepository.findOne(id);
    question.update(title, contents);
    questionRepository.save(question);
    return String.format("redirect:/questions/%d", id);
  }
  
  //질문 작성자가 질문 삭제 
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id) {
    questionRepository.delete(id);
    return "redirect:/";
  }
}
