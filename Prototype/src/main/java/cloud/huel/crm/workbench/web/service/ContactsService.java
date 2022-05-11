package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.domain.Contacts;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface ContactsService {
	/**
	 * 根据姓名模糊查询联系人信息
	 * @param name
	 * @return
	 */
	List<Contacts> fuzzyQueryContactsByName(String name);

	/**
	 * 进入联系人主页时执行的默认查询,默认查询 0 - 10 条之间的记录
	 * @return
	 */
	List<Contacts> queryAllContactsForPage();

}
