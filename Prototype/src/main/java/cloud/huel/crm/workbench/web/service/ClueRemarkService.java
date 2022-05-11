package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.domain.ClueRemark;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface ClueRemarkService {

	/**
	 * 查询线索对应的备注信息
	 * @param clueId
	 * @return
	 */
	List<ClueRemark> queryClueRemarkByClueId(String clueId);

	Integer addClueRemark(ClueRemark clueRemark);


	/**
	 * 删除备注信息
	 */
	Integer deleteRemarkById(String remarkId);

}
