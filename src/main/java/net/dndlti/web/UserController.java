package net.dndlti.web;

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
	
	//http://localhost:8080/user/form.html에서 회원가입
	/*@PostMapping("")	
	public String memberCreate(User user) {
		System.out.println("user: " + user);
		users.add(user);
		userRepository.save(user);
		@RequestMapping("/list") list 메소드 호출
		return "redirect:/users";
	}*/
	
	//index.html 에서 회원가입 메뉴 버튼 클릭하면 
	//templates/user/form.html 페이지로 이동
	@GetMapping("/form")	
	public String form() {
		return "/user/form";
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
	
	//회원리스트 페이지 list.html 에서 회원정보수정을 요청
	/*<a href="/users/{{id}}/form" 
            class="btn btn-success" 
            role="button">수정</a>*/
	@GetMapping("/{id}/form")	
	public String updateForm(@PathVariable long id,
	Model model) {
		/*model.addAttribute("user", 
		userRepository.findOne(id));*/
		User user = userRepository.findOne(id);
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
	
	@PutMapping("/{id}")
	public String update(@PathVariable long id, 
	User newUser) {
		User user = userRepository.findOne(id);
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users"; //회원목록으로 페이지이동
	}
	
	
	
	
	
	
	
	
}
