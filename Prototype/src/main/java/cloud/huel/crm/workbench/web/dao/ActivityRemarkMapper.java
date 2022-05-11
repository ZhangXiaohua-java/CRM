package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.Activity;
import cloud.huel.crm.workbench.web.domain.ActivityRemark;
import cloud.huel.crm.workbench.web.domain.ActivityRemarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityRemarkMapper {
    long countByExample(ActivityRemarkExample example);

    int deleteByExample(ActivityRemarkExample example);

    int deleteByPrimaryKey(String id);

    int insert(ActivityRemark record);

    int insertSelective(ActivityRemark record);

    List<ActivityRemark> selectByExample(ActivityRemarkExample example);

    ActivityRemark selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ActivityRemark record, @Param("example") ActivityRemarkExample example);

    int updateByExample(@Param("record") ActivityRemark record, @Param("example") ActivityRemarkExample example);

    int updateByPrimaryKeySelective(ActivityRemark record);

    int updateByPrimaryKey(ActivityRemark record);

    /**
     * 添加市场活动备注记录
     * @param activityRemark
     * @return
     */
    int insertActivityRemark(ActivityRemark activityRemark);



}