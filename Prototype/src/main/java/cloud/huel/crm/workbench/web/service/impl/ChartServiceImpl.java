package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.workbench.web.dao.TransactionMapper;
import cloud.huel.crm.workbench.web.domain.ChartVo;
import cloud.huel.crm.workbench.web.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 张晓华
 * @version 1.0
 */

@SuppressWarnings({"all"})
@Service
public class ChartServiceImpl implements ChartService {

	@Autowired
	private TransactionMapper transactionMapper;

	@Override
	public List<ChartVo> queryChartData() {
		return transactionMapper.selectChartData();
	}




}
