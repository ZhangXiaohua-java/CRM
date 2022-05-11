package cloud.huel.crm.workbench.web.controller;

import cloud.huel.crm.domain.Message;
import cloud.huel.crm.workbench.web.domain.Customer;
import cloud.huel.crm.workbench.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Controller
@RequestMapping("/workbench/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/toIndex")
	public String toIndex(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize , Model model){
		List<Customer> customerList = customerService.queryCustomerForPage(pageNo, pageSize);
		model.addAttribute("customerList",customerList);
		return "workbench/customer/index";
	}

	/**
	 * 配合typehead插件的自动补全客户名的方法
	 * @param name
	 * @return
	 */
	@GetMapping("/fuzzyQueryCustomer")
	@ResponseBody
	public Object fuzzyQueryCustomer(@RequestParam String name){
		return customerService.fuzzyQueryCustomerName(name);
	}


	@GetMapping("/queryCount")
	@ResponseBody
	public Object queryCount(){
		return customerService.queryRecordsCount();
	}

	@RequestMapping("/customer/{pageNo}/{pageSize}")
	@ResponseBody
	public Message customer(@PathVariable Integer pageNo,@PathVariable Integer pageSize){
		List<Customer> customers = customerService.queryCustomerForPage(pageNo, pageSize);
		return Message.processSuccessRequest().add("customers",customers);
	}


	@RequestMapping("/customerDetail/{id}")
	public String customerDetail(@PathVariable("id") String customerId){

		return null;
	}


}
