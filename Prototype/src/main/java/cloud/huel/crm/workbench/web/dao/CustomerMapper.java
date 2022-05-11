package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.Customer;
import cloud.huel.crm.workbench.web.domain.CustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerMapper {
    long countByExample(CustomerExample example);

    int deleteByExample(CustomerExample example);

    int deleteByPrimaryKey(String id);

    int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> selectByExample(CustomerExample example);

    Customer selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Customer record, @Param("example") CustomerExample example);

    int updateByExample(@Param("record") Customer record, @Param("example") CustomerExample example);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    /**
     * 分页查询客户信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Customer> selectCustomerForPage(@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);

    /**
     * 模糊查询客户名
     * @param name
     * @return
     */
    List<String> selectCustomerName(@Param("name") String name);


    Integer selectRowCounts();

}