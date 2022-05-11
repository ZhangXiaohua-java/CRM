package cloud.huel.crm.workbench.web.controller;

import cloud.huel.crm.workbench.web.domain.ChartVo;
import cloud.huel.crm.workbench.web.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */

@SuppressWarnings({"all"})
@RequestMapping("/workbench/chart")
@Controller
public class ChartController {

	@Autowired
	private ChartService chartService;

	@GetMapping("/toIndex")
	public String toIndex(){
		return "workbench/chart/transaction/index";
	}



	@GetMapping("/showChart")
	@ResponseBody
	public Object showChart(){
		List<ChartVo> voList = chartService.queryChartData();
		System.out.println(voList);
		return voList;
	}


}
