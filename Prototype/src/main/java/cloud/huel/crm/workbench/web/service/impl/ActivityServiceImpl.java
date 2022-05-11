package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.workbench.web.dao.ActivityMapper;
import cloud.huel.crm.workbench.web.domain.Activity;
import cloud.huel.crm.workbench.web.domain.ActivityExample;
import cloud.huel.crm.workbench.web.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityMapper activityMapper;

	/**
	 * 需要为这个方法做一个切面监控异常
	 * @param activity
	 * @return
	 */
	@Override
	public boolean addActivity(Activity activity) {
		Integer i = activityMapper.insertActivity(activity);
		if (i != null) {
			return true;
		}
		return false;
	}

	/**
	 * 分页查询活动信息
	 *
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Activity> queryActivitiesInOrder(Map<String, Object> paramMap) {
		return activityMapper.usingConditionQueryActivitiesForPage(paramMap);
	}


	/**
	 * 查询表中总记录条目数
	 *
	 * @return
	 */
	@Override
	public Integer queryTotalRows() {
		Integer count = activityMapper.queryRecordCount();
		if (Objects.isNull(count) || count ==0) {
			return null;
		}
		return count;
	}

	@Override
	public Integer removeActivities(String[] ids) {
		return activityMapper.deleteActivitiesByIds(ids);
	}

	@Override
	public Activity queryActivityInfoByID(String id) {
		return activityMapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean modifyActivityInfo(Activity activity) {
		return activityMapper.updateByPrimaryKeySelective(activity) == 1;
	}

	@Override
	public List<Activity> exportAllActivities() {
		return activityMapper.selectAllActivities();
//		return activityMapper.selectByExample(null);
	}

	@Override
	public Integer addActivityFromImportedFile(List<Activity> activityList) {
		Objects.requireNonNull(activityList,"传入的activityList为空");
		return activityMapper.insertActivityFromImportedFile(activityList);
	}

	/**
	 * 查询市场活动的详细信息,这里使用关联查询
	 * @param id
	 * @return
	 */
	@Override
	public Activity queryDetailActivityInfo(String id) {
		return activityMapper.selectActivityByIdForDetailInfo(id);
	}


	/**
	 * 线索与市场活动之间存在着多对多的关系,一个市场活动可能产生多个线索,一个线索可能参与多个市场活动
	 *
	 * @param clueId
	 * @return
	 */
	@Override
	public List<Activity> queryActivityByClueId(String clueId) {
		return activityMapper.queryActivityByClueId(clueId);
	}

	/**
	 * 根据市场活动名查询市场活动信息
	 *
	 * @param name
	 * @return
	 */
	@Override
	public List<Activity> queryActivityByName(String name,String clueId) {
		if (name == null) {
			return null;
		}
		return activityMapper.fuzzyQueryByName(name,clueId);
	}

	/**
	 * 选择性导出市场活动信息
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public List<Activity> queryActivityInfoSelective(String[] ids) {
		return activityMapper.queryActivitySelective(ids);
	}

	@Override
	public List<Activity> queryActivityByNameAndClueId(Map<String, String> parameterMap) {
		return activityMapper.selectActivityByNameAndClueId(parameterMap);
	}

	@Override
	public List<Activity> fuzzyQueryActivityByName(String activityName) {
		return activityMapper.fuzzyQueryActivity(activityName);
	}


}
