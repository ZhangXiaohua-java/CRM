package cloud.huel.crm.workbench.web.service.impl;

import cloud.huel.crm.commons.enumeration.SessionKeyList;
import cloud.huel.crm.commons.utils.DateUtils;
import cloud.huel.crm.commons.utils.UUIDUtils;
import cloud.huel.crm.settings.web.domain.User;
import cloud.huel.crm.workbench.web.dao.*;
import cloud.huel.crm.workbench.web.domain.*;
import cloud.huel.crm.workbench.web.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
@Service
public class ClueServiceImpl implements ClueService {

	@Autowired
	private ClueMapper clueMapper;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private ContactsMapper contactsMapper;

	@Autowired
	private ClueRemarkMapper clueRemarkMapper;

	@Autowired
	private CustomerRemarkMapper customerRemarkMapper;

	@Autowired
	private ContactsRemarkMapper contactsRemarkMapper;

	@Autowired
	private ActivityMapper activityMapper;

	@Autowired
	private ContactsActivityRelationMapper contactsActivityRelationMapper;

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private TransactionRemarkMapper transactionRemarkMapper;

	@Autowired
	private ClueActivityRelationMapper clueActivityRelationMapper;
	/**
	 * 分页查询线索信息
	 *
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<Clue> queryCluesWithPageNo(Integer pageNo, Integer pageSize) {
		return clueMapper.selectCluesForPage((pageNo-1)*pageSize,pageSize) ;
	}

	/**
	 * 保存添加的线索记录
	 *
	 * @param clue
	 * @return
	 */
	@Override
	public Integer saveClue(Clue clue) {
		return clueMapper.insertSelective(clue);
	}

	/**
	 * 查询表中所有记录数
	 *
	 * @return
	 */
	@Override
	public Integer countRecordsCount() {
		return clueMapper.queryRecordCount();
	}


	/**
	 * 查询线索的详细信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Clue queryClueInfo(String id) {
		return clueMapper.queryClueInfo(id);
	}

	/**
	 * 事务保证线索转换的完成
	 *
	 * @param parameterMap
	 */
	@Override
	public void convert(Map<String, Object> parameterMap) {
		Clue clue = clueMapper.selectByPrimaryKey((String) parameterMap.get("clueId"));
		User user = (User) parameterMap.get(SessionKeyList.USER.getKey());
		Customer customer = new Customer();
		customer.setId(UUIDUtils.getUUID());
		customer.setOwner(clue.getOwner());
		customer.setName(clue.getCompany());
		customer.setWebsite(clue.getWebsite());
		customer.setPhone(clue.getPhone());
		customer.setCreateBy(user.getId());
		customer.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
		customer.setContactSummary(clue.getContactSummary());
		customer.setNextContactTime(clue.getNextContactTime());
		customer.setDescription(clue.getDescription());
		customer.setAddress(clue.getAddress());
//		保存副本,向数据库中插入Customer对象的记录
		customerMapper.insertSelective(customer);
		Contacts contacts = new Contacts();
		contacts.setId(UUIDUtils.getUUID());
		contacts.setAddress(clue.getAddress());
		contacts.setCreateBy(user.getId());
		contacts.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
		contacts.setAppellation(clue.getAppellation());
		contacts.setContactSummary(clue.getContactSummary());
		contacts.setDescription(clue.getDescription());
		contacts.setFullname(clue.getFullname());
		contacts.setJob(clue.getJob());
		contacts.setMphone(clue.getMphone());
		contacts.setSource(clue.getSource());
		contacts.setNextContactTime(clue.getNextContactTime());
		contacts.setOwner(clue.getOwner());
		contacts.setEmail(clue.getEmail());
		contacts.setCustomerId(customer.getId());
//		插入联系人的记录
		contactsMapper.insert(contacts);
//		备注备份,分别在联系人和客户表中备份一份
		List<ClueRemark> clueRemarkList = clueRemarkMapper.queryClueRemarkByClueId((String) parameterMap.get("clueId"));
		CustomerRemark customerRemark = null;
		ContactsRemark contactsRemark = null;
		List<CustomerRemark> customerRemarkList = new ArrayList<>();
		List<ContactsRemark> contactsRemarkList = new ArrayList<>();
		for (ClueRemark remark : clueRemarkList) {
			customerRemark = new CustomerRemark();
			contactsRemark = new ContactsRemark();
			customerRemark.setId(UUIDUtils.getUUID());
			contactsRemark.setId(UUIDUtils.getUUID());
			customerRemark.setNoteContent(remark.getNoteContent());
			contactsRemark.setNoteContent(remark.getNoteContent());
			customerRemark.setCreateBy(user.getId());
			contactsRemark.setCreateBy(user.getId());
			customerRemark.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
			contactsRemark.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
			customerRemark.setEditBy(remark.getEditBy());
			contactsRemark.setEditBy(remark.getEditBy());
			customerRemark.setEditTime(remark.getEditTime());
			contactsRemark.setEditTime(remark.getEditTime());
			customerRemark.setEditFlag(remark.getEditFlag());
			contactsRemark.setEditFlag(remark.getEditFlag());
			customerRemark.setCustomerId(customer.getId());
			contactsRemark.setContactsId(contacts.getId());
			customerRemarkList.add(customerRemark);
			contactsRemarkList.add(contactsRemark);
		}
//		保存客户备注记录和联系人备注记录
		customerRemarkMapper.copyFromClueRemark(customerRemarkList);
		contactsRemarkMapper.copyFromClueRemark(contactsRemarkList);
//		建立市场活动与联系人之间的关联关系
		List<Activity> activityList = activityMapper.queryActivitiesByClueID((String) parameterMap.get("clueId"));
		ContactsActivityRelation contactsActivityRelation = null;
		List<ContactsActivityRelation> contactsActivityRelationList = new ArrayList<>();
		for (Activity activity : activityList) {
			contactsActivityRelation = new ContactsActivityRelation();
			contactsActivityRelation.setId(UUIDUtils.getUUID());
			contactsActivityRelation.setActivityId(activity.getId());
			contactsActivityRelation.setContactsId(contacts.getId());
			contactsActivityRelationList.add(contactsActivityRelation);
		}
//		批量保存市场活动与联系人之间的关联关系
		contactsActivityRelationMapper.saveAssociationWithActivityAndContacts(contactsActivityRelationList);
		Boolean flag = (Boolean) parameterMap.get("tranFlag");
		if (flag) {
			Transaction transaction = new Transaction();
			transaction.setId(UUIDUtils.getUUID());
			transaction.setActivityId((String) parameterMap.get("activityId"));
			transaction.setMoney((String) parameterMap.get("money"));
			transaction.setName((String) parameterMap.get("name"));
			transaction.setStage((String) parameterMap.get("stage"));
			transaction.setExpectedDate((String) parameterMap.get("expectedDate"));
			transaction.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
			transaction.setCreateBy(user.getId());
			transaction.setContactsId(contacts.getId());
			transaction.setCustomerId(customer.getId());
			transaction.setOwner(user.getId());
			transactionMapper.insertSelective(transaction);
			List<TransactionRemark> transactionRemarkList = new ArrayList<>();
			TransactionRemark transactionRemark = null;
			for (ClueRemark remark : clueRemarkList) {
				transactionRemark = new TransactionRemark();
				transactionRemark.setId(UUIDUtils.getUUID());
				transactionRemark.setNoteContent(remark.getNoteContent());
				transactionRemark.setCreateBy(user.getId());
				transactionRemark.setCreateTime(DateUtils.formatDate(null,DateUtils.MEDIUM));
				transactionRemark.setEditBy(remark.getEditBy());
				transactionRemark.setEditTime(remark.getEditTime());
				transactionRemark.setEditFlag(remark.getEditFlag());
				transactionRemark.setTranId(transaction.getId());
				transaction.setContactsId(contacts.getId());
				transaction.setCustomerId(customer.getId());
				transactionRemarkList.add(transactionRemark);
			}
//			将线索备注也备份到交易备注表中一份
			transactionRemarkMapper.copyFromClueRemark(transactionRemarkList);
		}
		//			删除和原线索有关的一切信息,删除线索信息
		ClueActivityRelationExample example = new ClueActivityRelationExample();
		ClueActivityRelationExample.Criteria exampleCriteria = example.createCriteria();
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < activityList.size(); i++) {
			idList.add(i,activityList.get(i).getId());
		}
		exampleCriteria.andClueIdEqualTo(clue.getId());
		exampleCriteria.andActivityIdIn(idList);
		clueActivityRelationMapper.deleteByExample(example);
		ClueRemarkExample remarkExample = new ClueRemarkExample();
		ClueRemarkExample.Criteria criteria = remarkExample.createCriteria();
		criteria.andClueIdEqualTo(clue.getId());
		clueRemarkMapper.deleteByExample(remarkExample);
		clueMapper.deleteByPrimaryKey(clue.getId());

	}


}
