package com.Z.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Z.pojo.Account;
import com.Z.pojo.JSONResult;
import com.Z.service.AccountService;
import com.Z.utils.MD5Utils;

@Controller
public class RegistLoginController {
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/validateUsername")
	@ResponseBody
	public JSONResult validateUsername(String username) {

		if(StringUtils.isBlank(username)) {
			return JSONResult.errorMsg("请输入用户名");
		}
		boolean exist = accountService.usernameIsExist(username);
		
		return JSONResult.ok(exist ?  "用户名已存在" : "用户名可用" );
	}
	
	@RequestMapping("/login")
	public String login(Account account ,HttpServletRequest request, HttpSession session) throws Exception {
		if(account != null )
			account.setPassword(MD5Utils.getMD5Str(account.getPassword()));
		Account user = accountService.findAccount(account);
		user.setPassword("");
		session.setAttribute("user", user);
		return "redirect:/home";
	}
	
	@RequestMapping("/regist.do")
	public String regist(Account account,ModelMap map) throws Exception{
		if(account!=null && !StringUtils.isBlank(account.getUsername()) && !StringUtils.isBlank(account.getUsername())) {
			boolean exist = accountService.usernameIsExist(account.getUsername());
			if(exist) return "/th/regist";
			account.setName(account.getUsername());
			String password = account.getPassword();
			account.setPassword(MD5Utils.getMD5Str(account.getPassword()));
			accountService.saveAccount(account);
			account.setPassword(password);
			map.addAttribute("account",account);
		}
		return "index";
	}
	
	@RequestMapping("/login.do")
	@ResponseBody
	public JSONResult login(Account account) throws Exception {
		if(account != null) 
			account.setPassword(MD5Utils.getMD5Str(account.getPassword()));
		boolean flag = accountService.findAccountForLogin(account);
		if(flag)
			return JSONResult.ok(account);
		else
			return JSONResult.errorMsg("账户或密码不正确");
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:index";
	}
	
	@RequestMapping({"/","/index"})
	public String index() {
		
		return "index";
	}
}
