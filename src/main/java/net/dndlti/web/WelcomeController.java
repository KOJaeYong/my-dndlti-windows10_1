package net.dndlti.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	//get 방식으로 url mapping
	//http://localhost:8080/helloworld?name=dndlti&age=30
	@GetMapping("/helloworld")
	public String welcome(String name, 
	int age, Model model) {
		System.out.println("name: " + name 
		+ " age:" + age);
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "welcome";
	}
	
	@GetMapping("/helloworld_1")
	public String welcome_1(Model model) {
		model.addAttribute("name", "manidto");
		model.addAttribute("value", 10000);
		model.addAttribute("taxed_value:", 10000-(10000*0.4));
		model.addAttribute("in_ca", true);
		return "welcome_1";
	}
	
	@GetMapping("/helloworld_2")
	public String welcome_2(Model model) {
		model.addAttribute("name", "manidto");
		model.addAttribute("company", "<b>Dndlti</b>");
		return "welcome_2";
	}
	
	@GetMapping("helloworld_3")
	public String welcome_3(Model model) {
		/*List<MyModel> repo = new ArrayList<MyModel>();*/
		List<MyModel> repo = Arrays.asList(new MyModel("우이샤"), new MyModel("마이또"));
		model.addAttribute("repo", repo);
		return "welcome_3";
	}
	
	
}
