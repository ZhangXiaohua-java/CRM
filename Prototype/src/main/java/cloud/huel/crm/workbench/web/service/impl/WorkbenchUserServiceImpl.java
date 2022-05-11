package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.settings.web.dao.UserMapper;
import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.workbench.web.service.WorkbenchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
@Service
public class WorkbenchUserServiceImpl implements WorkbenchUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> queryAllUsers() {
		return userMapper.selectByExample(null);
	}

}
