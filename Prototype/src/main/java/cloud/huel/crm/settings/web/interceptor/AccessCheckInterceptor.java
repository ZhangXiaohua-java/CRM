package cloud.huel.crm.settings.web.interceptor;

import cloud.huel.crm.commons.enumeration.SessionKeyList;
import cloud.huel.crm.settings.web.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author 张晓华
 * @version 1.0
 */
@Component
public class AccessCheckInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession httpSession = request.getSession(false);
		boolean flag = Objects.equals(null, httpSession);
		if (flag) {
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		User user =(User) httpSession.getAttribute(SessionKeyList.USER.getKey());
		boolean status = Objects.equals(user, null);
		if (status) {
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
