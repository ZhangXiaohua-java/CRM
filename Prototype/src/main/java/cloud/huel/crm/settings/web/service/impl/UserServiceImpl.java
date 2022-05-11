package cloud.huel.crm.settings.web.service.impl;

import cloud.huel.crm.settings.web.dao.UserMapper;
import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.settings.web.domain.UserExample;
import cloud.huel.crm.settings.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Service
public class UserServiceImpl implements UserService {
	/**
	 * 根据用户的登录账号和密码查询用户信息
	 *
	 * @param map
	 * @return
	 */
	@Autowired
	UserMapper userMapper;

	@Override
	public User queryUserByLoginActAndPwd(Map<String, String> map) {
		return userMapper.selectUserByLoginActAndPwd(map);
	}

	/**
	 * 查询所有的用户
	 *
	 * @return
	 */
	@Override
	public List<User> queryAllUsers() {
		return  userMapper.selectByExample(null);
	}


}
