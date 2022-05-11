package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.TransactionRemark;
import cloud.huel.crm.workbench.web.domain.TransactionRemarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransactionRemarkMapper {
    long countByExample(TransactionRemarkExample example);

    int deleteByExample(TransactionRemarkExample example);

    int deleteByPrimaryKey(String id);

    int insert(TransactionRemark record);

    int insertSelective(TransactionRemark record);

    List<TransactionRemark> selectByExample(TransactionRemarkExample example);

    TransactionRemark selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TransactionRemark record, @Param("example") TransactionRemarkExample example);

    int updateByExample(@Param("record") TransactionRemark record, @Param("example") TransactionRemarkExample example);

    int updateByPrimaryKeySelective(TransactionRemark record);

    int updateByPrimaryKey(TransactionRemark record);

    /**
     * 将线索备注批量保存到交易备注表中
     * @param transactionRemarkList
     * @return
     */
    Integer copyFromClueRemark(List<TransactionRemark> transactionRemarkList);

    /**
     * 根据外键查询所有备注信息
     * @param id
     * @return
     */
    List<TransactionRemark> selectRemarkByTransactionId(@Param("id") String id);

}