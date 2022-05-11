package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface ActivityService {


	/**
	 * 添加活动
	 * @param activity
	 * @return
	 */
	boolean addActivity(Activity activity);

	/**
	 * 分页查询活动信息
	 * @param paramMap
	 * @return
	 */
	List<Activity> queryActivitiesInOrder(Map<String,Object> paramMap);

	/**
	 * 查询表中总记录条目数
	 * @return
	 */
	Integer queryTotalRows();

	Integer removeActivities(String [] ids);

	/**
	 * 根据id查询市场活动信息
	 * @param id
	 * @return
	 */
	Activity queryActivityInfoByID(String id);

	Boolean modifyActivityInfo(Activity activity);

	List<Activity> exportAllActivities();

	Integer addActivityFromImportedFile(List<Activity> activityList);


	/**
	 * 查询市场活动的详细信息,这里使用嵌套查询
	 * @param id
	 * @return
	 */
	Activity queryDetailActivityInfo(String id);

	/**
	 * 线索与市场活动之间存在着多对多的关系,一个市场活动可能产生多个线索,一个线索可能参与多个市场活动
	 * @param clueId
	 * @return
	 */
	List<Activity> queryActivityByClueId(String clueId);

	/**
	 * 根据市场活动名查询市场活动信息
	 * @param name
	 * @return
	 */
	List<Activity> queryActivityByName(String name,String clueId);

	/**
	 * 选择性导出市场活动信息
	 * @param ids
	 * @return
	 */
	List<Activity> queryActivityInfoSelective(String [] ids);


	List<Activity> queryActivityByNameAndClueId(Map<String,String> parameterMap);

	List<Activity> fuzzyQueryActivityByName(String activityName);
}
