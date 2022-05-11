package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.workbench.web.dao.CustomerMapper;
import cloud.huel.crm.workbench.web.domain.Customer;
import cloud.huel.crm.workbench.web.domain.CustomerExample;
import cloud.huel.crm.workbench.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public List<Customer> queryCustomerForPage(Integer pageNo, Integer pageSize) {
		 return customerMapper.selectCustomerForPage((pageNo - 1) * pageSize, pageSize);
	}

	@Override
	public List<String> fuzzyQueryCustomerName(String name) {
		return customerMapper.selectCustomerName(name);
	}

	/**
	 * 根据客户名查找客户信息,这里的客户名即公司名,唯一
	 *
	 * @param customerName
	 * @return
	 */
	@Override
	public Customer queryCustomerByName(String customerName) {
		CustomerExample example = new CustomerExample();
		example.createCriteria().andNameEqualTo(customerName);
		return customerMapper.selectByExample(example).get(0);
	}

	/**
	 * 添加客户
	 *
	 * @param customer
	 * @return
	 */
	@Override
	public Integer addCustomer(Customer customer) {
		return customerMapper.insertSelective(customer);
	}

	/**
	 * 查询表中记录数
	 *
	 * @return
	 */
	@Override
	public Integer queryRecordsCount() {
		return customerMapper.selectRowCounts();
	}

}
