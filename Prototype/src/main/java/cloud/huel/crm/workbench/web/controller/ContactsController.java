package cloud.huel.crm.workbench.web.controller;

import cloud.huel.crm.domain.Message;
import cloud.huel.crm.workbench.web.domain.Contacts;
import cloud.huel.crm.workbench.web.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
@RequestMapping("/workbench/contacts")
@Controller
public class ContactsController {

	@Autowired
	private ContactsService contactsService;

	@RequestMapping("/toIndex")
	public String toIndex(Model model){
		List<Contacts> contactsList = contactsService.queryAllContactsForPage();
		model.addAttribute("contactsList",contactsList);
		return "/workbench/contacts/index";
	}

	@GetMapping("/queryContacts")
	@ResponseBody
	public Message queryContacts(@RequestParam String name){
		List<Contacts> contactsList = contactsService.fuzzyQueryContactsByName(name);
		if (contactsList == null || contactsList.size() == 0) {
			return Message.processFailedRequest();
		}
		return Message.processSuccessRequest().add("contactsList",contactsList);
	}





}
