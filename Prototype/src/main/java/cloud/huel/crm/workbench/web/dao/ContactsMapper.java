package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.Contacts;
import cloud.huel.crm.workbench.web.domain.ContactsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContactsMapper {
    long countByExample(ContactsExample example);

    int deleteByExample(ContactsExample example);

    int deleteByPrimaryKey(String id);

    int insert(Contacts record);

    int insertSelective(Contacts record);

    List<Contacts> selectByExample(ContactsExample example);

    Contacts selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Contacts record, @Param("example") ContactsExample example);

    int updateByExample(@Param("record") Contacts record, @Param("example") ContactsExample example);

    int updateByPrimaryKeySelective(Contacts record);

    int updateByPrimaryKey(Contacts record);

    /**
     * 根据姓名模糊查询联系人信息
     * @param name
     * @return
     */
    List<Contacts> selectContactsByName(@Param("name") String name);

    /**
     * 分页查询联系人信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Contacts> selectContactsForPage(@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);


}