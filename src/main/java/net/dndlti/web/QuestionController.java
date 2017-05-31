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
import net.dndlti.domain.Result;
import net.dndlti.domain.User;

//컨트롤러 역할을 하는 클래스
@Controller
@RequestMapping("/questions")
public class QuestionController {
  
  //QuestionRepository 인터페이스는 데이터베이스 테이블 Question 과 연동한다.
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
  public String show(@PathVariable Long id, 
  Model model) {
    model.addAttribute(
    "question", questionRepository.findOne(id));
    return "/qna/show";
  }
  
  //질문 수정 폼으로 이동
  //-- 세션 로그인한 질문 작성자만이 질문 수정하도록 구현
  @GetMapping("/{id}/form")
  public String updateForm(@PathVariable Long id, 
  Model model, HttpSession session) {
    //세션로그인 여부를 확인할 수 있는 메소드
    /*System.out.println(
    "나의 ~ !HttpSessionUtils.isLoginUser(session): " 
    + !HttpSessionUtils.isLoginUser(session));
    if (!HttpSessionUtils.isLoginUser(session)) {
      css,js,bootstrap,jquery 등의 자원을 제대로 읽어오기 위해 수정
      return "redirect:/users/loginForm";
    }
    //세션로그인을 한 User 객체인지를 확인
    User loginUser = 
    HttpSessionUtils.getUserFromSession(session);
    
    Question question = questionRepository.findOne(id);
    System.out.println(
    "나의 ~ !question.isSameWriter(loginUser): " 
    + !question.isSameWriter(loginUser));
    if (!question.isSameWriter(loginUser)) {
      css,js,bootstrap,jquery 등의 자원을 제대로 읽어오기 위해 수정
      return "redirect:/users/loginForm";
    }
    model.addAttribute("question", question);
    return "/qna/updateForm";*/
    
    /*5-6. 첫번째 QuestionController 중복 코드 제거 리팩토링*/ 
    /*try {
      Question question = questionRepository.findOne(id);
      hasPermission(session, question);
      model.addAttribute("question", question);
      return "/qna/updateForm";
    } catch (IllegalStateException e) {
      model.addAttribute("errorMessage", e.getMessage());
      //동일한 주소로 이동하지만 자원 include 경로 수정 필요 
      return "/user/login";
      return "redirect:/users/loginForm";
    }*/
    /*5-6. 두번째 QuestionController 중복 코드 제거 리팩토링*/
    Question question = 
    questionRepository.findOne(id);
    Result result = valid(session,question);
    if (!result.isValid()) {
      model.addAttribute(
      "errorMessage", result.getErrorMessage());
      return "/user/login";
    }
    
    model.addAttribute("question", question);
    return "/qna/updateForm";
  }
  
  private Result valid(HttpSession session,
  Question question) {
    //로그인 사용자가 아니라면
    if (!HttpSessionUtils.isLoginUser(session)) {
      return Result.fail("로그인이 필요합니다");
    }
    //질문을 작성한 로그인 사용자와 동일인이 아니라면 질문 수정, 삭제 불가
    User loginUser = 
    HttpSessionUtils.getUserFromSession(session);
    if (!question.isSameWriter(loginUser)) {
      return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
    }
    return Result.ok();
  }
  
  //질문을 작성할 권한이 있지 않으면 예외처리를 하여 에러메시지를 전송하는 
  //방식으로 중복 코드 제거 리팩토링
  /*private boolean hasPermission(HttpSession session,
  Question question) {
    //로그인 사용자가 아니라면
    if (!HttpSessionUtils.isLoginUser(session)) {
      throw new IllegalStateException("로그인이 필요합니다.");
    }
    //질문을 작성한 로그인 사용자와 동일인이 아니라면 질문 수정, 삭제 불가
    User loginUser = 
    HttpSessionUtils.getUserFromSession(session);
    if (!question.isSameWriter(loginUser)) {
      throw new IllegalStateException(
      "자신이 쓴 글만 수정, 삭제가 가능합니다.");
    }
    return true;
  }*/
  
  //질문 수정을 한 후 해당 질문이 표시되도록 페이지 이동 
  //로그인 사용자중에 질문 작성자만이 질문 수정할 수 있도록 구현
  @PutMapping("/{id}")
  public String update(
  @PathVariable Long id, String title, String contents,
  Model model, HttpSession session) {
    /*if (!HttpSessionUtils.isLoginUser(session)) {
      css,js,bootstrap,jquery 등의 자원을 제대로 읽어오기 위해 수정
      return "redirect:/users/loginForm";
    }
    User loginUser = 
    HttpSessionUtils.getUserFromSession(session);
    Question question = questionRepository.findOne(id);
    if (!question.isSameWriter(loginUser)) {
      css,js,bootstrap,jquery 등의 자원을 제대로 읽어오기 위해 수정
      return "redirect:/users/loginForm";
    }
    
    question.update(title, contents);
    questionRepository.save(question);
    return String.format("redirect:/questions/%d", id);*/
    
    /*5-6. 첫번째 QuestionController 중복 코드 제거 리팩토링*/
    /*try {
      Question question = questionRepository.findOne(id);
      hasPermission(session, question);
      question.update(title, contents);
      questionRepository.save(question);
      return String.format("redirect:/questions/%d", id);
    } catch (IllegalStateException e) {
      model.addAttribute("errorMessage", e.getMessage());
      //동일한 주소로 이동하지만 자원 include 경로 수정 필요 
      return "/user/login";
      return "redirect:/users/loginForm";
    }*/
    
    /*5-6. 두번째 QuestionController 중복 코드 제거 리팩토링*/
    Question question = 
    questionRepository.findOne(id);
    Result result = valid(session, question);
    if (!result.isValid()) {
      model.addAttribute(
      "errorMessage", result.getErrorMessage());
      return "/user/login";
    }
    question.update(title,contents);
    questionRepository.save(question);
    return String.format(
    "redirect:/questions/%d", id);
  }
  
  //질문 작성자만 질문 삭제 
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id,
  Model model, HttpSession session) {
    /*if (!HttpSessionUtils.isLoginUser(session)) {
      css,js,bootstrap,jquery 등의 자원을 제대로 읽어오기 위해 수정
      return "redirect:/users/loginForm";
    }
    User loginUser = HttpSessionUtils.getUserFromSession(session);
    Question question = questionRepository.findOne(id);
    if (!question.isSameWriter(loginUser)) {
      css,js,bootstrap,jquery 등의 자원을 제대로 읽어오기 위해 수정
      return "redirect:/users/loginForm";
    }
    
    questionRepository.delete(id);
    return "redirect:/";*/
    
    /*5-6. 첫번째 QuestionController 중복 코드 제거 리팩토링*/
    /*try {
      Question question = questionRepository.findOne(id);
      hasPermission(session, question);
      questionRepository.delete(id);
      return "redirect:/";
    } catch (IllegalStateException e) {
      model.addAttribute("errorMessage", e.getMessage());
      //동일한 주소로 이동하지만 자원 include 경로 수정 필요 
      return "/user/login";
      return "redirect:/users/loginForm";
    }*/
    
    /*5-6. 두번째 QuestionController 중복 코드 제거 리팩토링*/
    Question question = 
    questionRepository.findOne(id);
    Result result = valid(session,question);
    if (!result.isValid()) {
      model.addAttribute(
      "errorMessage", result.getErrorMessage());
      return "/user/login";
    }
    questionRepository.delete(id);
    return "redirect:/";
  }
}
