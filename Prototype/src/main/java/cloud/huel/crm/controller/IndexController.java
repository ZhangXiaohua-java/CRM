package cloud.huel.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 张晓华
 * @version 1.0
 */
@Controller
public final class IndexController {

	@GetMapping("/")
	public String welcome(){
		return "index";
	}




}
