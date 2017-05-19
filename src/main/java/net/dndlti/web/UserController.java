package net.dndlti.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.dndlti.domain.User;
import net.dndlti.domain.UserRepository;

@Controller
public class UserController {

	/*회원가입 저장*/
	/*private List<User> users = new ArrayList<User>();*/
	
	//데이터베이스 테이블과의 연동
	//@Autowired 어노테이션은 
	//스프링에서 자동으로 의존관계를 타입에 맞춰서 연결
	@Autowired
	private UserRepository userRepository;
	
	//http://localhost:8080/user/form.html에서 회원가입
	@PostMapping("/users")	
	public String memberCreate(User user) {
		System.out.println("user: " + user);
		/*users.add(user);*/
		userRepository.save(user);
		/*@RequestMapping("/list") list 메소드 호출*/
		return "redirect:/users";
	}
	//@RequestMapping 어노테이션도 사용가능
	@GetMapping("/users")
	public String memberList(Model model) {
		/*model.addAttribute("users", users);*/
		//H2 데이터베이스 User 테이블에 있는 레코드를 
		//모두 리스트로 출력 
		model.addAttribute("users", 
		userRepository.findAll());
		return "/user/list";
	}
	
	@PostMapping("/create")	
	public String create(User user) {
		System.out.println("user: " + user);
		
		/*users.add(user);*/
		userRepository.save(user);
		
		/*@RequestMapping("/list") list 메소드 호출*/
		return "redirect:/list";
	}
	
	//@GetMapping 어노테이션도 사용가능
	@RequestMapping("/list")
	public String list(Model model) {
		
		/*model.addAttribute("users", users);*/
		//H2 데이터베이스 User 테이블에 있는 레코드를 
		//모두 리스트로 출력 
		model.addAttribute("users", 
		userRepository.findAll());
		
		return "list";
	}
	
}
