package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.settings.web.domain.User;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface WorkbenchUserService {

	List<User> queryAllUsers();

}
