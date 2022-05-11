package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.domain.TransactionRemark;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface TransactionRemarkService {

	/**
	 * 根据交易记录的id查询其对应的所有备注信息
	 * @param id
	 * @return
	 */
	List<TransactionRemark> queryRemarksByTransactionId(String id);


}
