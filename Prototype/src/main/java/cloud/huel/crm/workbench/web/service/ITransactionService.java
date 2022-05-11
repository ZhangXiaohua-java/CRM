package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.workbench.web.domain.Transaction;

import java.util.List;
import java.util.Map;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface ITransactionService {

	/**
	 * 分页查询交易数据
	 * @return
	 */
	List<Transaction> queryTransactionForPage();

	List<Transaction> queryTransactionForPage(Integer pageNo,Integer pageSize);

	/**
	 * 创建交易
	 */
	Integer addTransaction(Transaction transaction, User user);



	Transaction queryTransactionById(String id);




}
