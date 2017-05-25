package net.dndlti.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.dndlti.domain.User;
import net.dndlti.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	/*회원가입 저장*/
	/*private List<User> users = new ArrayList<User>();*/
	
	//데이터베이스 테이블과의 연동
	//@Autowired 어노테이션은 
	//스프링에서 자동으로 의존관계를 타입에 맞춰서 연결
	@Autowired
	private UserRepository userRepository;
	
	//index.html 에서 로그인 메뉴 버튼 클릭하면 
	//templates/user/login.html 페이지로 이동
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	//src/main/resources/templates/user/login.html 에서
	//로그인폼 action="/users/login" 과 url mapping
	@PostMapping("/login")
	public String login(String userId, String password,
	HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			System.out.println("Login Failure!");
			/*return "/user/login";*/
			return "redirect:/users/loginForm";
		}
		/*User passwd = 
		userRepository.findByPassword(password);
		if (passwd == null) {
			return "/user/login";
		}*/
		if (!password.equals(user.getPassword())) {
			System.out.println("Login Failure!");
			/*return "/user/login";*/
			return "redirect:/users/loginForm";
		}
		
		// user 로 session 설정하여 / 루트에 리턴
		System.out.println("Login Success!");
		session.setAttribute("sessionUser", user);
		return "redirect:/";
	}
	
	//http://localhost:8080/user/form.html에서 회원가입
	/*@PostMapping("")	
	public String memberCreate(User user) {
		System.out.println("user: " + user);
		users.add(user);
		userRepository.save(user);
		@RequestMapping("/list") list 메소드 호출
		return "redirect:/users";
	}*/
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	  //세션 로그인 사용자의 세션을 제거
	  session.removeAttribute("sessionUser");
	  System.out.println("세션 제거!");
	  return "redirect:/";
	}
	
	//index.html 에서 회원가입 메뉴 버튼 클릭하면 
	//templates/user/form.html 페이지로 이동
	@GetMapping("/form")	
	public String form() {
		return "/user/form";
	}
	
	//index.html 에서 회원정보수정 메뉴 버튼 클릭하면 
	//templates/user/updateForm.html 페이지로 이동
	@GetMapping("/updateForm")	
	public String updateForm() {
		return "/user/updateForm";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("user: " + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		//회원 목록 정보를 model 에 저장
		model.addAttribute("users", 
		userRepository.findAll());
		//user 디렉토리의 list.html 페이지로 이동
		return "/user/list";
	}
	
	/*//@RequestMapping 어노테이션도 사용가능
	@GetMapping("")
	public String memberList(Model model) {
		model.addAttribute("users", users);
		//H2 데이터베이스 User 테이블에 있는 레코드를 
		//모두 리스트로 출력 
		model.addAttribute("users", 
		userRepository.findAll());
		return "/user/list";
	}*/
	
	//자신만의 회원정보를 수정할 수 있도록 세션로그인 사용자, 
	//세션로그인 사용자의 id 가 같을 때에만 updateForm 페이지로 이동  
	//회원리스트 페이지 list.html 에서 회원정보수정을 요청
	/*<a href="/users/{{id}}/form" 
            class="btn btn-success" 
            role="button">수정</a>*/
	@GetMapping("/{id}/form")	
	public String updateForm(@PathVariable Long id,
	Model model, HttpSession session) {
	  //세션 로그인 사용자만 개인정보수정 가능 - alt+shift+r : rename 단축키
	  User sessionedUser = (User) session.getAttribute("sessionUser");
	  if (sessionedUser == null) {
	    //비로그인 사용자는 로그인 페이지로 이동
      return "redirect:/users/loginForm";
    }
	  System.out.println("나의~ tempUser: " + sessionedUser);
	  
	  //사용자의 id 가 동일하지 않을 때에는 에러 메시지 출력
	  if (!id.equals(sessionedUser.getId())) {
      throw new IllegalAccessError("You can edit only for your information");
    }
	  
		/*model.addAttribute("user", 
		userRepository.findOne(id));*/
		User user = userRepository.findOne(id);
		/*User user = userRepository.findOne(sessionedUser.getId());*/
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	/*@PostMapping("/{id}")
	public String update(@PathVariable long id, 
	User newUser) {
		User user = userRepository.findOne(id);
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users"; //회원목록으로 페이지이동
	}*/
	
	//자신만의 회원정보를 수정할 수 있도록 세션로그인 사용자, 
  //세션로그인 사용자의 id 가 같을 때에만 자신의 회원정보 수정 가능 
  @PutMapping("/{id}")
	public String update(@PathVariable Long id, 
	User updatedUser, HttpSession session) {
	//세션 로그인 사용자만 개인정보수정 가능 - alt+shift+r : rename 단축키
    User sessionedUser = (User) session.getAttribute("sessionUser");
    if (sessionedUser == null) {
      //비로그인 사용자는 로그인 페이지로 이동
      return "redirect:/users/loginForm";
    }
    System.out.println("나의~ tempUser: " + sessionedUser);
    
    //사용자의 id 가 동일하지 않을 때에는 에러 메시지 출력
    if (!id.equals(sessionedUser.getId())) {
      throw new IllegalAccessError("You can edit only for your information");
    }
	  
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users"; //회원목록으로 페이지이동
	}
	
	
	
	
	
	
	
	
}
