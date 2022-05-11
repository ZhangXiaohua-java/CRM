package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.workbench.web.dao.TransactionRemarkMapper;
import cloud.huel.crm.workbench.web.domain.TransactionRemark;
import cloud.huel.crm.workbench.web.service.TransactionRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
@Service
public class TransactionRemarkServiceImpl implements TransactionRemarkService {

	@Autowired
	private TransactionRemarkMapper transactionRemarkMapper;
	/**
	 * 根据交易记录的id查询其对应的所有备注信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public List<TransactionRemark> queryRemarksByTransactionId(String id) {
		return transactionRemarkMapper.selectRemarkByTransactionId(id);
	}


}
