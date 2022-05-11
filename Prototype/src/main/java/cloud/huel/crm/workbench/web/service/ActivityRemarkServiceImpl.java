package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.dao.ActivityRemarkMapper;
import cloud.huel.crm.workbench.web.domain.ActivityRemark;
import cloud.huel.crm.workbench.web.domain.ActivityRemarkExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 张晓华
 * @version 1.0
 */
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

	@Autowired
	private ActivityRemarkMapper activityRemarkMapper;

	@Override
	public Integer addActivityRemark(ActivityRemark activityRemark) {
		return activityRemarkMapper.insertActivityRemark(activityRemark);
	}

	@Override
	public Integer deleteActivityRemarkById(String id) {
		ActivityRemarkExample example = new ActivityRemarkExample();
		example.createCriteria().andIdEqualTo(id);
		return activityRemarkMapper.deleteByExample(example);
	}

	@Override
	public Integer modifyActivityRemarkInfo(ActivityRemark activityRemark) {
		return activityRemarkMapper.updateByPrimaryKeySelective(activityRemark);
	}

}
