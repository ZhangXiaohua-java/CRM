package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.workbench.web.dao.ClueMapper;
import cloud.huel.crm.workbench.web.dao.ClueRemarkMapper;
import cloud.huel.crm.workbench.web.domain.ClueRemark;
import cloud.huel.crm.workbench.web.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {

	@Autowired
	private ClueRemarkMapper clueRemarkMapper;

	/**
	 * 查询线索对应的备注信息
	 *
	 * @param clueId
	 * @return
	 */
	@Override
	public List<ClueRemark> queryClueRemarkByClueId(String clueId) {
		return clueRemarkMapper.queryClueRemarksByClueId(clueId);
	}

	/**
	 * 添加备注信息
	 * @param clueRemark
	 * @return
	 */
	@Override
	public Integer addClueRemark(ClueRemark clueRemark) {
		return clueRemarkMapper.insertSelective(clueRemark);
	}

	/**
	 * 删除备注信息
	 *
	 * @param remarkId
	 */
	@Override
	public Integer deleteRemarkById(String remarkId) {
		return clueRemarkMapper.deleteByPrimaryKey(remarkId);
	}

}
