package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.domain.Customer;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface CustomerService {

	List<Customer> queryCustomerForPage(Integer pageNo,Integer pageSize);

	List<String> fuzzyQueryCustomerName(String name);

	/**
	 * 根据客户名查找客户信息,这里的客户名即公司名,唯一
	 * @param customerName
	 * @return
	 */
	Customer queryCustomerByName(String customerName);

	/**
	 * 添加客户
	 * @param customer
	 * @return
	 */
	Integer addCustomer(Customer customer);

	/**
	 * 查询表中记录数
	 * @return
	 */

	Integer queryRecordsCount();


}
