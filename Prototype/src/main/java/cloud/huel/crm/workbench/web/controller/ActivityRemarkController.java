package cloud.huel.crm.workbench.web.controller;

import cloud.huel.crm.commons.enumeration.SessionKeyList;
import cloud.huel.crm.commons.utils.DateUtils;
import cloud.huel.crm.commons.utils.UUIDUtils;
import cloud.huel.crm.domain.Message;
import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.workbench.web.domain.Activity;
import cloud.huel.crm.workbench.web.domain.ActivityRemark;
import cloud.huel.crm.workbench.web.service.ActivityRemarkService;
import cloud.huel.crm.workbench.web.service.ActivityService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author 张晓华
 * @version 1.0
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityRemarkController {

	@Autowired
	private ActivityRemarkService activityRemarkService;
	@Autowired
	private ActivityService activityService;


	@GetMapping("/queryActivityInfo/{id}")
	public String queryActivityInfo(Model model,@PathVariable("id") String id){
		Objects.requireNonNull(id,"id不允许为空");
		Activity activity = activityService.queryDetailActivityInfo(id);
		boolean flag = Objects.isNull(activity);
		if (flag) {
			return "redirect:/";
		}
		System.out.println(activity);
		model.addAttribute("activity",activity);
		return "workbench/activity/detail";
	}

	@RequestMapping(value = "/addActivityRemark",method = RequestMethod.POST)
	@ResponseBody
	public Message addActivityRemark(ActivityRemark activityRemark, @NotNull HttpSession session){
		Objects.requireNonNull(activityRemark,"参数不允许为空");
		User user = (User) session.getAttribute(SessionKeyList.USER.getKey());
		activityRemark.setId(UUIDUtils.getUUID());
		activityRemark.setCreateBy(user.getId());
		activityRemark.setEditFlag(ActivityRemark.NON_EDITED);
		activityRemark.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM_NO_TIME));
		Integer rowCount = activityRemarkService.addActivityRemark(activityRemark);
		if (rowCount == null || rowCount ==0) {
			return Message.processFailedRequest();
		}
		return Message.processSuccessRequest().add("activityRemark",activityRemark);
	}

	@DeleteMapping("/deleteActivityRemark")
	@ResponseBody
	public Message deleteActivityRemark(String id){
		if (id == null) {
			return Message.processFailedRequest();
		}
		Integer rowCount = activityRemarkService.deleteActivityRemarkById(id);
		if (rowCount == null || rowCount == 0) {
			return Message.processFailedRequest();
		}
		return Message.processSuccessRequest();
	}

	@PutMapping("/modifyActivityRemark")
	@ResponseBody
	public Message modifyActivityRemark(ActivityRemark activityRemark,HttpSession session){
		if (activityRemark == null) {
			return Message.processFailedRequest();
		}
		User user = (User) session.getAttribute(SessionKeyList.USER.getKey());
		activityRemark.setEditBy(user.getId());
		activityRemark.setEditFlag(ActivityRemark.HAS_EDITED);
		activityRemark.setEditTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
		try {
			Integer rowCount = activityRemarkService.modifyActivityRemarkInfo(activityRemark);
			if (rowCount == null || rowCount ==0) {
				return Message.processFailedRequest();
			}
			return Message.processSuccessRequest().add("activityRemark",activityRemark);
		}catch(Exception e){
			e.printStackTrace();
			return Message.processFailedRequest();
		}

	}


}
