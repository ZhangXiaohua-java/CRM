package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.ContactsRemark;
import cloud.huel.crm.workbench.web.domain.ContactsRemarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContactsRemarkMapper {
    long countByExample(ContactsRemarkExample example);

    int deleteByExample(ContactsRemarkExample example);

    int deleteByPrimaryKey(String id);

    int insert(ContactsRemark record);

    int insertSelective(ContactsRemark record);

    List<ContactsRemark> selectByExample(ContactsRemarkExample example);

    ContactsRemark selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ContactsRemark record, @Param("example") ContactsRemarkExample example);

    int updateByExample(@Param("record") ContactsRemark record, @Param("example") ContactsRemarkExample example);

    int updateByPrimaryKeySelective(ContactsRemark record);

    int updateByPrimaryKey(ContactsRemark record);

    /**
     * 批量保存线索备注到联系人备注表中
     * @param contactsRemarkList
     * @return
     */
    Integer copyFromClueRemark(List<ContactsRemark> contactsRemarkList);


}