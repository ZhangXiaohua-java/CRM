package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.CustomerRemark;
import cloud.huel.crm.workbench.web.domain.CustomerRemarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerRemarkMapper {
    long countByExample(CustomerRemarkExample example);

    int deleteByExample(CustomerRemarkExample example);

    int deleteByPrimaryKey(String id);

    int insert(CustomerRemark record);

    int insertSelective(CustomerRemark record);

    List<CustomerRemark> selectByExample(CustomerRemarkExample example);

    CustomerRemark selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CustomerRemark record, @Param("example") CustomerRemarkExample example);

    int updateByExample(@Param("record") CustomerRemark record, @Param("example") CustomerRemarkExample example);

    int updateByPrimaryKeySelective(CustomerRemark record);

    int updateByPrimaryKey(CustomerRemark record);

    /**
     * 批量保存线索备注到客户备注表中
     * @param customerRemarkList
     * @return
     */
    Integer copyFromClueRemark(List<CustomerRemark> customerRemarkList);


}