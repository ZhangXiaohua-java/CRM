package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.domain.Activity;
import cloud.huel.crm.workbench.web.domain.ActivityRemark;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface ActivityRemarkService {


	Integer addActivityRemark(ActivityRemark activityRemark);

	Integer deleteActivityRemarkById(String id);

	Integer modifyActivityRemarkInfo(ActivityRemark activityRemark);

}
