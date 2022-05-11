package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.Activity;
import cloud.huel.crm.workbench.web.domain.ActivityExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ActivityMapper {
    long countByExample(ActivityExample example);

    int deleteByExample(ActivityExample example);

    int deleteByPrimaryKey(String id);

    int insertActivity(Activity activity);

    int insertSelective(Activity record);

    List<Activity> selectByExample(ActivityExample example);

    Activity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Activity record, @Param("example") ActivityExample example);

    int updateByExample(@Param("record") Activity record, @Param("example") ActivityExample example);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);


    List<Activity> usingConditionQueryActivitiesForPage(Map<String,Object> map);


    Integer queryRecordCount();

    /**
     * 批量删除活动信息
     * @param id
     * @return
     */
    Integer deleteActivitiesByIds(String [] id);

    /**
     * 查询所有的市场活动信息
     */
    List<Activity> selectAllActivities();


    /**
     * 执行导入市场活动
     * @param activityList
     * @return
     */
    Integer insertActivityFromImportedFile(List<Activity> activityList);

    Activity selectActivityByIdForDetailInfo(@Param("id") String id);

    List<Activity> queryActivityByClueId(@Param("clueId") String clueId);

    /**
     * 根据市场活动名模糊查询市场活动信息
     * @param activityName
     * @return
     */
    List<Activity> fuzzyQueryByName(@Param("activityName") String activityName,@Param("clueId") String clueId);

    List<Activity> selectActivityByClueId(@Param("clueId") String clueId);


    /**
     * 选择导出Excel信息
     * @param ids
     * @return
     */
    List<Activity> queryActivitySelective(String [] ids);

    /**
     * 根据市场活动名称查询已经和线索关联的市场活动信息
     * @param parameterMap
     * @return
     */
    List<Activity> selectActivityByNameAndClueId(Map<String,String> parameterMap);


    /**
     * 根据线索ID查询其关联的所有的市场活动信息
     *
     * @param clueId
     * @return
     */
    List<Activity> queryActivitiesByClueID(@Param("clueId") String clueId);

    /**
     * 模糊查询市场活动
     */
    List<Activity> fuzzyQueryActivity(@Param("activityName") String activityName);

}