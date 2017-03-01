package simple.auth.boot.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import simple.auth.boot.service.SabSourceService;

@Controller
public class LoginController {

	@Autowired
	SabSourceService esdSourceService;

	@RequestMapping(value = { "index", "/" })
	public String index() {
		return "index";
	}

	@RequestMapping("login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("loginSuccess")
	public String loginSuccess() {
		return "index";
	}

	@ResponseBody
	@RequestMapping("loginFail")
	public String loginFail() {
		return "loginFail";
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/login";
	}

	@ResponseBody
	@RequestMapping("reloadResource")
	public String reloadResource(HttpServletRequest request) {
		try {
			esdSourceService.loadResource();
		} catch (Exception e) {
			return e.getMessage();
		}
		return "success";
	}
}
