package cloud.huel.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 张晓华
 * @version 1.0
 */
@Controller
@RequestMapping("/workbench")
public class WorkBenchController {

	@GetMapping("/toIndex")
	public String toIndex(){
		return "workbench/index";
	}





}
