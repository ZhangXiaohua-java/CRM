package cloud.huel.crm.workbench.web.controller;

import cloud.huel.crm.commons.enumeration.DictionaryType;
import cloud.huel.crm.commons.enumeration.SessionKeyList;
import cloud.huel.crm.commons.utils.DateUtils;
import cloud.huel.crm.commons.utils.UUIDUtils;
import cloud.huel.crm.domain.Message;
import cloud.huel.crm.settings.web.domain.DictionaryValue;
import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.settings.web.service.DictionaryValueService;
import cloud.huel.crm.settings.web.service.UserService;
import cloud.huel.crm.workbench.web.domain.*;
import cloud.huel.crm.workbench.web.service.ActivityService;
import cloud.huel.crm.workbench.web.service.ClueActivityRelationService;
import cloud.huel.crm.workbench.web.service.ClueRemarkService;
import cloud.huel.crm.workbench.web.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
	@Autowired
	private ClueService clueService;
	@Autowired
	private DictionaryValueService dictionaryValueService;
	@Autowired
	private UserService userService;
	@Autowired
	private ClueRemarkService clueRemarkService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ClueActivityRelationService clueActivityRelationService;

//	跳转向线索主页面的请求
	@RequestMapping("/toClueIndex")
	public String toClueIndex(@RequestParam(defaultValue = "1") Integer pageNo,
		@RequestParam(defaultValue = "10") Integer pageSize, Model model){
		List<Clue> clueList = clueService.queryCluesWithPageNo(pageNo, pageSize);
		List<DictionaryValue> sourceList = dictionaryValueService.queryDictionaryValue(DictionaryType.SOURCE);
		List<DictionaryValue> stateList = dictionaryValueService.queryDictionaryValue(DictionaryType.CLUE_STATE);
		List<DictionaryValue> appellationList = dictionaryValueService.queryDictionaryValue(DictionaryType.APPELLATION);
		List<User> userList = userService.queryAllUsers();
		model.addAttribute("clueList",clueList);
		model.addAttribute("sourceList",sourceList);
		model.addAttribute("stateList",stateList);
		model.addAttribute("appellationList",appellationList);
		model.addAttribute("userList",userList);
		return "/workbench/clue/index";
	}

	@RequestMapping("/saveClue")
	@ResponseBody
	public Message saveClue(Clue clue, HttpSession session){
		User user = (User) session.getAttribute(SessionKeyList.USER.getKey());
		clue.setCreateBy(user.getId());
		clue.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
		clue.setId(UUIDUtils.getUUID());
		try {
			Integer rowCount = clueService.saveClue(clue);
			if (rowCount == null || rowCount ==0) {
				return Message.processFailedRequest();
			}
			return Message.processSuccessRequest();
		}catch(Exception e){
			e.printStackTrace();
			return Message.processFailedRequest();
		}
	}

	@RequestMapping("/pageQueryClue")
	@ResponseBody
	public Message pageQueryClue(Integer pageNo,Integer pageSize){

		List<Clue> clueList = clueService.queryCluesWithPageNo(pageNo, pageSize);
		Integer recordsCount = clueService.countRecordsCount();
		if (clueList == null) {
			return Message.processFailedRequest();
		}
		return Message.processSuccessRequest().add("rowCount",recordsCount).add("clueList",clueList);
	}

	@RequestMapping("/queryCount")
	@ResponseBody
	public Message queryCount(){
		Integer recordsCount = clueService.countRecordsCount();
		return Message.processSuccessRequest().add("recordsCount",recordsCount);
	}

	@RequestMapping("/queryClueDetailInfo/{id}")
	public String queryClueDetailInfo(@PathVariable("id") String id,Model model){
		Clue clue = clueService.queryClueInfo(id);
		List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkByClueId(id);
		List<Activity> activityList = activityService.queryActivityByClueId(id);
		model.addAttribute("activityList",activityList);
		model.addAttribute("clue",clue);
		model.addAttribute("remarkList",remarkList);
		return "/workbench/clue/detail";
	}

	@RequestMapping(value = "/saveClueRemark",method = RequestMethod.POST)
	@ResponseBody
	public Message saveClueRemark(ClueRemark clueRemark,HttpSession session){
		User user = (User) session.getAttribute(SessionKeyList.USER.getKey());
		clueRemark.setId(UUIDUtils.getUUID());
		clueRemark.setCreateBy(user.getId());
		clueRemark.setEditFlag(ClueRemark.NON_EDITED);
		clueRemark.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
		try {
			Integer rowCount = clueRemarkService.addClueRemark(clueRemark);
			if (rowCount == null || rowCount == 0) {
				return Message.processFailedRequest();
			}
			return Message.processSuccessRequest().add("clueRemark",clueRemark);
		}catch(Exception e){
			e.printStackTrace();
			return Message.processFailedRequest();
		}
	}

//	保存线索与市场活动之间的关联

	@RequestMapping(value = "/saveAssociationBetweenClueAndActivity",method = RequestMethod.POST)
	@ResponseBody
	public Message saveAssociationBetweenClueAndActivity(@RequestParam("activityId[]") String [] activityId,@RequestParam String clueId){
		System.out.println(Arrays.toString(activityId));
		System.out.println(clueId+"线索Id");
		ClueActivityRelation clueActivityRelation = null;
		List<ClueActivityRelation> clueActivityRelationList = new ArrayList<>();
		for (String s : activityId) {
			clueActivityRelation = new ClueActivityRelation();
			clueActivityRelation.setId(UUIDUtils.getUUID());
			clueActivityRelation.setActivityId(s);
			clueActivityRelation.setClueId(clueId);
			clueActivityRelationList.add(clueActivityRelation);
		}

		Integer rowCount = clueActivityRelationService.addClueActivityAssociation(clueActivityRelationList);
		if (rowCount == null || rowCount ==0 ||rowCount !=activityId.length) {
			return Message.processFailedRequest();
		}
		List<Activity> activityList = activityService.queryActivityByClueId(clueId);
		return Message.processSuccessRequest().add("activityList",activityList);
	}


	@RequestMapping(value = "/unbindingAssociation",method = RequestMethod.DELETE)
	@ResponseBody
	public Message unbindingAssociation(ClueActivityRelation clueActivityRelation){
		Integer rowCount = clueActivityRelationService.deleteClueActivityAssociation(clueActivityRelation);
		if (rowCount == null || rowCount ==0) {
			return Message.processFailedRequest();
		}
		return Message.processSuccessRequest();
	}


	@RequestMapping("/queryClue/{clueId}")
	public String queryClue(@PathVariable("clueId") String clueId,Model model){
		Clue clue = clueService.queryClueInfo(clueId);
		List<DictionaryValue> stageList = dictionaryValueService.queryDictionaryValue(DictionaryType.STAGE);
		model.addAttribute("clue",clue);
		model.addAttribute("stageList",stageList);
		return "workbench/clue/convert";
	}


	/**
	 * 模糊查询已经和当前线索关联的市场活动信息
	 */
	@GetMapping("/fuzzyQueryActivity")
	@ResponseBody
	public Message fuzzyQueryActivity(@RequestParam String activityName,@RequestParam String clueId){
		if (clueId == null) {
			return Message.processFailedRequest();
		}
		Map<String,String> parameterMap = new HashMap<>();
		parameterMap.put("activityName",activityName);
		parameterMap.put("clueId",clueId);
		List<Activity> activityList = activityService.queryActivityByNameAndClueId(parameterMap);
		return Message.processSuccessRequest().add("activityList",activityList);
	}

	@GetMapping("/convert")
	@ResponseBody
	public Message convert(String clueId,Boolean tranFlag,
		   String money,String name,String expectedDate,
		   String stage,String activityId,HttpSession session){
		Map<String,Object> parameterMap = new HashMap<>();
		parameterMap.put("clueId",clueId);
		parameterMap.put("tranFlag",tranFlag);
		parameterMap.put("name",name);
		parameterMap.put("money",money);
		parameterMap.put("expectedDate",expectedDate);
		parameterMap.put("stage",stage);
		parameterMap.put("activityId",activityId);
		User user = (User) session.getAttribute(SessionKeyList.USER.getKey());
		parameterMap.put(SessionKeyList.USER.getKey(),user);
//		调用service完成转换
		System.out.println(parameterMap);
		try {
			clueService.convert(parameterMap);
			return Message.processSuccessRequest();
		}catch(Exception e){
			e.printStackTrace();
			return Message.processFailedRequest();
		}

	}

	/**
	 * 删除线索备注
	 * @param remarkId
	 * @return
	 */
	@DeleteMapping("/deleteRemark/{id}")
	@ResponseBody
	public Message deleteRemark(@PathVariable("id") String remarkId){
		Integer rowCount = clueRemarkService.deleteRemarkById(remarkId);
		if (rowCount == null || rowCount ==0) {
			return Message.processFailedRequest();
		}
		return Message.processSuccessRequest();
	}





}
