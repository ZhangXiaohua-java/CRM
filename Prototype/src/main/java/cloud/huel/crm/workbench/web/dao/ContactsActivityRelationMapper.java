package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.ContactsActivityRelation;
import cloud.huel.crm.workbench.web.domain.ContactsActivityRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContactsActivityRelationMapper {
    long countByExample(ContactsActivityRelationExample example);

    int deleteByExample(ContactsActivityRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(ContactsActivityRelation record);

    int insertSelective(ContactsActivityRelation record);

    List<ContactsActivityRelation> selectByExample(ContactsActivityRelationExample example);

    ContactsActivityRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ContactsActivityRelation record, @Param("example") ContactsActivityRelationExample example);

    int updateByExample(@Param("record") ContactsActivityRelation record, @Param("example") ContactsActivityRelationExample example);

    int updateByPrimaryKeySelective(ContactsActivityRelation record);

    int updateByPrimaryKey(ContactsActivityRelation record);

    /**
     * 批量保存市场活动与联系人之间的关联关系
     * @param contactsActivityRelationList
     * @return
     */
    Integer saveAssociationWithActivityAndContacts(List<ContactsActivityRelation> contactsActivityRelationList);
}