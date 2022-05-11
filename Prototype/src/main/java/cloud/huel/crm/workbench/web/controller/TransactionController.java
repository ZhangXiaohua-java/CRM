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
import cloud.huel.crm.workbench.web.domain.Transaction;
import cloud.huel.crm.workbench.web.domain.TransactionRemark;
import cloud.huel.crm.workbench.web.service.ActivityService;
import cloud.huel.crm.workbench.web.service.ITransactionService;
import cloud.huel.crm.workbench.web.service.TransactionRemarkService;
import org.omg.IOP.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author 张晓华
 * @version 1.0
 */

@SuppressWarnings({"all"})
@Controller
@RequestMapping("/workbench/transaction")
public class TransactionController {

	@Autowired
	private ITransactionService transactionService;

	@Autowired
	private DictionaryValueService dictionaryValueService;

	@Autowired
	private UserService userService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private TransactionRemarkService transactionRemarkService;


	@RequestMapping("/toIndex")
	public String toIndex(Model model){
		List<DictionaryValue> stageList = dictionaryValueService.queryDictionaryValue(DictionaryType.STAGE);
		List<DictionaryValue> tranStatusList = dictionaryValueService.queryDictionaryValue(DictionaryType.TRANSACTION_TYPE);
		List<DictionaryValue> sourceList = dictionaryValueService.queryDictionaryValue(DictionaryType.SOURCE);
		List<Transaction> transactionList = transactionService.queryTransactionForPage();
		model.addAttribute("tranStatusList",tranStatusList);
		model.addAttribute("stageList",stageList);
		model.addAttribute("sourceList",sourceList);
		model.addAttribute("transactionList",transactionList);
		return "/workbench/transaction/index";
	}


	@GetMapping("/createTransaction")
	public String createTransaction(Model model){
		List<DictionaryValue> stageList = dictionaryValueService.queryDictionaryValue(DictionaryType.STAGE);
		List<DictionaryValue> tranStatusList = dictionaryValueService.queryDictionaryValue(DictionaryType.TRANSACTION_TYPE);
		List<DictionaryValue> sourceList = dictionaryValueService.queryDictionaryValue(DictionaryType.SOURCE);
		List<User> userList = userService.queryAllUsers();
		model.addAttribute("userList",userList);
		model.addAttribute("stageList",stageList);
		model.addAttribute("tranStatusList",tranStatusList);
		model.addAttribute("sourceList",sourceList);
		return "/workbench/transaction/save";
	}

	@RequestMapping(value = "/possibilityAnalysis",method = RequestMethod.GET)
	@ResponseBody
	public String possibilityAnalysis(@RequestParam String keyWord){
		Objects.requireNonNull(keyWord);
		ResourceBundle resourceBundle = ResourceBundle.getBundle("possibility");
		return resourceBundle.getString(keyWord);
	}


	@PostMapping("/createTransaction")
	@ResponseBody
	public Message createTransaction(Transaction transaction, HttpSession session){
		User user = (User) session.getAttribute(SessionKeyList.USER.getKey());
		transaction.setId(UUIDUtils.getUUID());
		transaction.setOwner(user.getId());
		transaction.setCreateBy(user.getId());
		transaction.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
		try {
			Integer rowCount = transactionService.addTransaction(transaction,user);
			if (rowCount == null || rowCount == 0) {
				return Message.processFailedRequest();
			}
			return Message.processSuccessRequest();
		}catch (Exception e){
			e.printStackTrace();
			return Message.processFailedRequest();
		}
	}

	@GetMapping("/queryDetailTransaction/{id}")
	public String queryDetailTransaction(@PathVariable("id") String transactionId,Model model) throws IOException {
		Transaction transaction = transactionService.queryTransactionById(transactionId);
		Properties properties = new Properties();
		properties.load(new FileInputStream("/src/main/resources/possibility.properties"));
		String possibility = (String) properties.get(transaction.getId());
		transaction.setPossibility(possibility);
		model.addAttribute("transaction",transaction);
		List<TransactionRemark> remarkList = transactionRemarkService.queryRemarksByTransactionId(transactionId);
		model.addAttribute("remarkList",remarkList);
		List<DictionaryValue> stageList = dictionaryValueService.queryDictionaryValue(DictionaryType.STAGE);
		model.addAttribute("stageList",stageList);
		DictionaryValue presentStage = dictionaryValueService.queryOrderNoByValue(transaction.getStage());
		model.addAttribute("presentStage",presentStage);
		return "/workbench/transaction/detail";
	}





}
