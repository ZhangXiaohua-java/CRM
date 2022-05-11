package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.commons.utils.DateUtils;
import cloud.huel.crm.commons.utils.UUIDUtils;
import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.workbench.web.dao.TransactionMapper;
import cloud.huel.crm.workbench.web.domain.Customer;
import cloud.huel.crm.workbench.web.domain.Transaction;
import cloud.huel.crm.workbench.web.service.CustomerService;
import cloud.huel.crm.workbench.web.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
@Service
public class ITransactionServiceImpl implements ITransactionService {

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private CustomerService customerService;

	/**
	 * 分页查询交易数据
	 *
	 * @return
	 */
	@Override
	public List<Transaction> queryTransactionForPage() {
		return transactionMapper.selectTransactionForPage(0,10);
	}

	@Override
	public List<Transaction> queryTransactionForPage(Integer pageNo, Integer pageSize) {
		return transactionMapper.selectTransactionForPage((pageNo-1)*pageSize,pageSize);
	}

	/**
	 * 创建交易
	 *
	 * @param transaction
	 */
	@Override
	public Integer addTransaction(Transaction transaction, User user) {
		Customer customer = customerService.queryCustomerByName(transaction.getCustomerId());
		if (customer == null) {
			customer  = new Customer();
			customer.setId(UUIDUtils.getUUID());
			customer.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
			customer.setCreateBy(user.getId());
			customerService.addCustomer(customer);
		}
		transaction.setCustomerId(customer.getId());
		System.out.println(transaction);
		return transactionMapper.insertSelective(transaction);
	}

	/**
	 * 根据id查询交易的详细信息
	 * @param id
	 * @return
	 */

	@Override
	public Transaction queryTransactionById(String id) {
		return transactionMapper.selectTransactionById(id);
	}


}
