package net.dndlti.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	/*회원가입 저장*/
	private List<User> users = new ArrayList<User>();
	
	@PostMapping("/create")	
	public String create(User user) {
		System.out.println("user: " + user);
		users.add(user);
		/*@RequestMapping("/list") list 메소드 호출*/
		return "redirect:/list";
	}
	
	//@GetMapping 어노테이션도 사용가능
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", users);
		return "list";
	}
	
}
