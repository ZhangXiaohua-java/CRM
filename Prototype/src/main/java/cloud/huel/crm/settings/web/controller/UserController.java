package cloud.huel.crm.settings.web.controller;

import cloud.huel.crm.commons.enumeration.SessionKeyList;
import cloud.huel.crm.commons.utils.DateUtils;
import cloud.huel.crm.domain.Message;
import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.settings.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Controller
@RequestMapping("/settings/qx/user")
public class UserController {
	private Map<String,String> map;
	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String toLogin(){
		return "settings/qx/user/login";
	}


	@PostMapping("/login")
	@ResponseBody
	public Message login(@RequestParam("loginAct") String loginAct,
						 @RequestParam("loginPwd") String loginPwd,
						 boolean isRemeber, HttpServletRequest request, HttpServletResponse response){
		map = new HashMap<>(6,0.8F);
		map.put("loginAct",loginAct);
		map.put("loginPwd",loginPwd);
		User user = userService.queryUserByLoginActAndPwd(map);
		boolean flag = Objects.equals(user,null);
		if (flag) {
			return Message.processFailedRequest().add("msg","账号或密码错误");
		}
		return processLogin(user,request,isRemeber,response);
	}


//	控制器方法的辅助方法,非控制器方法
	public Message processLogin(User user, HttpServletRequest request,boolean flag,HttpServletResponse response){
//		1 代表正常使用,0代表冻结
		String state = user.getLockState();
		System.out.println(state);
		String expireTime = user.getExpireTime();
		String now = DateUtils.formatDate(null, DateUtils.MEDIUM);
		if (!Objects.equals(state,"1")) {
			return Message.processFailedRequest().add("msg","账号已被冻结");
		}else if (now.compareTo(expireTime) >0 ) {
//			now 2022  expireTime 2018
//			登录失败,需进行下一步判断
			return Message.processFailedRequest().add("msg","账号已超出使用期限");
		}else if (!user.getAllowIps().contains(request.getRemoteAddr())){
			return Message.processFailedRequest().add("msg","IP受限");
		}
		HttpSession session = request.getSession();
		session.setAttribute(SessionKeyList.USERNAME.getKey(), user.getName());
		session.setAttribute(SessionKeyList.USER.getKey(), user);
		this.processUserCookie(user,response,flag);
		return Message.processSuccessRequest();
	}

	public void processUserCookie(User user,HttpServletResponse response,boolean flag){
		if (flag) {
			Cookie loginActCookie = new Cookie("loginAct", user.getLoginAct());
			Cookie loginPwdCookie = new Cookie("loginPwd", user.getLoginPwd());
			loginActCookie.setMaxAge(10*24*3600);
			loginPwdCookie.setMaxAge(10*24*3600);
			loginActCookie.setPath("/");
			loginPwdCookie.setPath("/");
			response.addCookie(loginActCookie);
			response.addCookie(loginPwdCookie);
		}else {
			Cookie loginActCookie = new Cookie("loginAct", "1");
			Cookie loginPwdCookie = new Cookie("loginPwd", "1");
			loginActCookie.setMaxAge(0);
			loginPwdCookie.setMaxAge(0);
			loginActCookie.setPath("/");
			loginPwdCookie.setPath("/");
			response.addCookie(loginActCookie);
			response.addCookie(loginPwdCookie);
		}

	}


	@GetMapping("/logout")
	public String logout(HttpSession httpSession,HttpServletResponse response){
		httpSession.removeAttribute(SessionKeyList.USERNAME.getKey());
		httpSession.removeAttribute(SessionKeyList.USER.getKey());
		httpSession.invalidate();
		Cookie loginActCookie = new Cookie("loginAct", "null");
		Cookie loginPwdCookie = new Cookie("loginPwd", "null");
		loginActCookie.setMaxAge(0);
		loginPwdCookie.setMaxAge(0);
		loginActCookie.setPath("/");
		loginPwdCookie.setPath("/");
		response.addCookie(loginActCookie);
		response.addCookie(loginPwdCookie);
		loginActCookie = null;
		loginPwdCookie = null;
		System.gc();
		return "redirect:/";
	}








}
