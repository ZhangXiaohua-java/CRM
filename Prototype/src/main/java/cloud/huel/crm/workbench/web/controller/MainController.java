package cloud.huel.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 张晓华
 * @version 1.0
 */
@Controller
@RequestMapping("/workbench/main")
public class MainController {

	@RequestMapping("/index")
	public String index(){
		return "workbench/main/index";
	}



}
