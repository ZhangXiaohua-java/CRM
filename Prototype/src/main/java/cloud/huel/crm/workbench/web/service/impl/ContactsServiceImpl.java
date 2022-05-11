package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.workbench.web.dao.ContactsMapper;
import cloud.huel.crm.workbench.web.domain.Contacts;
import cloud.huel.crm.workbench.web.domain.ContactsExample;
import cloud.huel.crm.workbench.web.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */

@Service
public class ContactsServiceImpl implements ContactsService {

	@Autowired
	private ContactsMapper contactsMapper;
	/**
	 * 根据姓名模糊查询联系人信息
	 *
	 * @param name
	 * @return
	 */
	@Override
	public List<Contacts> fuzzyQueryContactsByName(String name) {
		return contactsMapper.selectContactsByName(name);
	}

	/**
	 * 进入联系人主页时执行的默认查询,默认查询 0 - 10 条之间的记录
	 *
	 * @return
	 */
	@Override
	public List<Contacts> queryAllContactsForPage() {
		return contactsMapper.selectContactsForPage(0,10);
	}

}
