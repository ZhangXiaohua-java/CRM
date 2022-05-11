package cloud.huel.crm.settings.web.service;

import cloud.huel.crm.settings.web.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface UserService {

	/**
	 * 根据用户的登录账号和密码查询用户信息
	 * @param map
	 * @return
	 */
	User queryUserByLoginActAndPwd(Map<String,String> map);

	/**
	 * 查询所有的用户
	 * @return
	 */
	List<User> queryAllUsers();


}
