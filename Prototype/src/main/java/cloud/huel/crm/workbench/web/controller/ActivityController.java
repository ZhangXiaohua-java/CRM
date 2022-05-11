package cloud.huel.crm.workbench.web.controller;

import cloud.huel.crm.commons.enumeration.SessionKeyList;
import cloud.huel.crm.commons.utils.DateUtils;
import cloud.huel.crm.commons.utils.HSSFUtils;
import cloud.huel.crm.commons.utils.UUIDUtils;
import cloud.huel.crm.domain.Message;
import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.workbench.web.domain.Activity;
import cloud.huel.crm.workbench.web.service.ActivityService;
import cloud.huel.crm.workbench.web.service.WorkbenchUserService;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

	@Autowired
	private WorkbenchUserService workbenchUserService;
	@Autowired
	private ActivityService activityService;

	/**
	 * 因此此处查询的是表中所有的用户信息,表中永远都有数据,
	 * 所以不需要进行非空判断,直接向请求域中存放数据,转发视图
	 * @param model
	 * @return
	 */
	@GetMapping("/index")
	public String index(Model model){
		List<User> userList = workbenchUserService.queryAllUsers();
		model.addAttribute("userList",userList);
		return "workbench/activity/index";
	}

	/**
	 * 添加活动
	 * @return
	 */
	@PostMapping("/addActivity")
	@ResponseBody
	public Message addActivity(Activity activity, HttpSession httpSession){
		if (Objects.equals(null,activity)) {
			return Message.processFailedRequest().add("msg","参数为空");
		}
		Objects.requireNonNull(activity,"参数不允许为空");
		activity.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
		activity.setCreateBy(((User)httpSession.getAttribute(SessionKeyList.USER.getKey())).getId());
		activity.setId(UUIDUtils.getUUID());
		System.out.println(activity);
		boolean flag = activityService.addActivity(activity);
		if (!flag) {
			return  Message.processFailedRequest().add("msg","系统繁忙,稍后再试");
		}
		return Message.processSuccessRequest();
	}

	@RequestMapping("/queryActivities")
	@ResponseBody
	public Message queryActivities(Activity activity,
		   @RequestParam(defaultValue = "1") Integer pageNo,
		   @RequestParam(defaultValue = "10") Integer pageSize){
		Map<String,Object> paramMap = new HashMap<>(10,0.8F);
		paramMap.put("name",activity.getName());
		paramMap.put("ownerName",activity.getOwner());
		paramMap.put("startDate",activity.getStartDate());
		paramMap.put("endDate",activity.getEndDate());
		paramMap.put("pageNo",(pageNo-1)*pageSize);
		paramMap.put("pageSize",pageSize);
		List<Activity> activityList = activityService.queryActivitiesInOrder(paramMap);
		if (Objects.equals(null,activityList)) {
			return Message.processFailedRequest().add("msg","请检查检索参数");
		}
		Integer rows = activityService.queryTotalRows();
		if (rows == null) {
			return Message.processFailedRequest().add("msg","请检查检索参数");
		}
		return Message.processSuccessRequest().add("activitiesList",activityList).add("totalRows",rows);
	}

	@RequestMapping(value = "/deleteActivity",method = RequestMethod.DELETE)
	@ResponseBody
	public Message deleteActivity(String ids){
		System.out.println(ids);
		String[] id = processParameter(ids);
		System.out.println(Arrays.toString(id));
		if (Objects.isNull(id) || id.length == 0) {
			return Message.processFailedRequest();
		}
		Integer rowCount = activityService.removeActivities(id);
		if (rowCount == null || rowCount == 0) {
			return Message.processFailedRequest();
		}
		return Message.processSuccessRequest();
	}

	private String [] processParameter(String str){
		if (str == null || str.length() ==0) {
			return null;
		}
		String[] strings = str.split("_");
		return strings;
	}

	@RequestMapping(value = "/queryActivity/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Message queryActivity(@PathVariable("id") String id){
		if (Objects.isNull(id)) {
			return Message.processFailedRequest();
		}
		Activity activity = activityService.queryActivityInfoByID(id);
		if (activity == null) {
			return Message.processFailedRequest().add("msg","系统忙,请稍候...");
		}
		return Message.processSuccessRequest().add("activity",activity);
	}

	/**
	 * 更新市场活动信息
	 * @return
	 */
	@PutMapping("/updateActivityInfo")
	@ResponseBody
	public Message updateActivityInfo(Activity activity,HttpSession session){
		if (activity == null) {
			return Message.processFailedRequest();
		}
		User user = (User) session.getAttribute(SessionKeyList.USER.getKey());
		activity.setEditBy(user.getId());
		activity.setEditTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
		if (activityService.modifyActivityInfo(activity)) {
			return Message.processSuccessRequest();
		}
		return Message.processFailedRequest();
	}

	@RequestMapping("/exportAllActivities")
	public void  exportAllActivities(HttpServletResponse response) throws Throwable{
		String filename = DateUtils.formatDate(null,DateUtils.MEDIUM)+"市场活动信息.xls";
		filename = URLEncoder.encode(filename,"UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.addHeader("Content-Disposition","attachment;filename="+filename);
		List<Activity> activityList = activityService.exportAllActivities();
		Objects.requireNonNull(activityList,"查询结果为空");
		ServletOutputStream outputStream = response.getOutputStream();
		HSSFWorkbook workbook = generateWorkBook(titleList(),activityList);
		workbook.write(outputStream);
		workbook.close();
		outputStream.flush();
		outputStream.close();
	}


	private HSSFWorkbook generateWorkBook(List<String> titleList,List<Activity> activityList){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
		HSSFSheet workbookSheet = workbook.createSheet("市场活动信息列表");
		HSSFRow row = workbookSheet.createRow(0);
		HSSFCell cell = null;
		for (int i = 0; i < titleList.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(titleList.get(i));
		}
		for (int i = 0; i < activityList.size() ; i++) {
			row = workbookSheet.createRow(i+1);
			Activity activity = activityList.get(i);
			cell = row.createCell(0);
			cell.setCellValue(activity.getId());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(1);
			cell.setCellValue(activity.getName());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(2);
			cell.setCellValue(activity.getOwner());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(3);
			cell.setCellValue(activity.getCost());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(4);
			cell.setCellValue(activity.getDescription());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(5);
			cell.setCellValue(activity.getStartDate());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(6);
			cell.setCellValue(activity.getEndDate());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(7);
			cell.setCellValue(activity.getEditTime());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(8);
			cell.setCellValue(activity.getEditBy());
			cell.setCellStyle(cellStyle);
			cell = row.createCell(9);
			cell.setCellValue(activity.getCreateTime());
			cell.setCellStyle(cellStyle);

			cell = row.createCell(10);
			cell.setCellValue(activity.getCreateBy());
			cell.setCellStyle(cellStyle);
		}
		return workbook;
	}

	private List<String> titleList(){
		ArrayList<String> titleList = new ArrayList<>();
		titleList.add("ID");
		titleList.add("活动名");
		titleList.add("组织者");
		titleList.add("成本");
		titleList.add("活动描述");
		titleList.add("开始日期");
		titleList.add("结束日期");
		titleList.add("修改者");
		titleList.add("修改日期");
		titleList.add("创建时间");
		titleList.add("创建者");
		return titleList;
	}


//	处理导入市场活动的方法
	@PostMapping("/importActivity")
	@ResponseBody
	public Message importActivity(@RequestParam("activityFile") MultipartFile activityFile,HttpSession session){
		Objects.requireNonNull(activityFile,"获取到的文件为空");
		try {
			List<Activity> activityList = parseExcel(activityFile, session);
			Integer rowCount = activityService.addActivityFromImportedFile(activityList);
			if (rowCount == null || rowCount ==0) {
				return Message.processFailedRequest();
			}
			return Message.processSuccessRequest().add("rowCount",rowCount);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Activity> parseExcel(MultipartFile file,HttpSession session) throws IOException {
		InputStream inputStream = file.getInputStream();
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		Activity activity = null;
		User user = (User) session.getAttribute(SessionKeyList.USER.getKey());
		List<Activity> activityList = new ArrayList<>();
		int maxRowIndex = sheet.getLastRowNum();
		Date date = null ;
		for (int i = 1; i <= maxRowIndex; i++) {
			row = sheet.getRow(i);
			activity = new Activity();
			activity.setId(UUIDUtils.getUUID());
			activity.setCreateBy(user.getId());
			activity.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
			activity.setOwner(user.getId());
			for (int j = 0; j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				String cellValue = HSSFUtils.getCellValue(cell);
				System.out.println(cellValue);
				if (j == 0) {
					activity.setName(cellValue);
				}else if (j == 1) {
					activity.setCost(cellValue);
				}else if (j == 2) {
					activity.setDescription(cellValue);
				}else if (j == 3) {
					date = DateUtil.getJavaDate(Double.valueOf(cellValue));
					cellValue = DateUtils.formatDate(date, DateUtils.MEDIUM_NO_TIME);
					activity.setStartDate(cellValue);
				}else if (j == 4) {
					date = DateUtil.getJavaDate(Double.valueOf(cellValue));
					cellValue = DateUtils.formatDate(date, DateUtils.MEDIUM_NO_TIME);
					activity.setEndDate(cellValue);
				}

			}
			activityList.add(activity);
		}
		return activityList;
	}


//	Excel模板下载方法


	@GetMapping("/templateDownload")
	@ResponseBody
	public ResponseEntity<byte []> templateDownload(HttpSession session) throws IOException {
		String path = session.getServletContext().getRealPath("/file/市场信息导入模板.xls");
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String filename = "导入信息模板.xls";
		filename = new String(filename.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1);
		headers.setContentDispositionFormData("attachment",filename);
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		return responseEntity;
	}

//	根据市场活动名迷糊查询所有的市场活动信息
// 	需要添加添加排除已经关联过的市场活动功能
	@RequestMapping(value = "/queryActivityWithActivityName",method = RequestMethod.GET)
	@ResponseBody
	public Message queryActivityWithActivityName(@RequestParam String activityName,String clueId){
		List<Activity> activityList = activityService.queryActivityByName(activityName,clueId);
		if (activityList == null) {
			return Message.processFailedRequest();
		}
		return Message.processSuccessRequest().add("activityList",activityList);
	}




//	选择导出Excel信息
	@RequestMapping("/exportActivityInfoSelective")
	public void exportActivityInfoSelective(@RequestParam("ids") String [] ids,HttpServletResponse response) throws IOException {
		List<Activity> activityList = activityService.queryActivityInfoSelective(ids);
		String filename = DateUtils.formatDate(null,DateUtils.MEDIUM_NO_TIME)+"市场活动信息.xls";
		HSSFWorkbook workbook = generateWorkBook(titleList(), activityList);
		response.setContentType("application/octec-stream;charset=utf-8");
		response.addHeader("Content-Disposition","attachment;filename="+filename);
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.flush();
		outputStream.close();
	}

	@RequestMapping("/fuzzyQueryActivity")
	@ResponseBody
	public Object fuzzyQueryActivity(String activityName){
		return activityService.fuzzyQueryActivityByName(activityName);
	}



}
