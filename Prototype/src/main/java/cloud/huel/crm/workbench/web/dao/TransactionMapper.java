package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.ChartVo;
import cloud.huel.crm.workbench.web.domain.Transaction;
import cloud.huel.crm.workbench.web.domain.TransactionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransactionMapper {
    long countByExample(TransactionExample example);

    int deleteByExample(TransactionExample example);

    int deleteByPrimaryKey(String id);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    List<Transaction> selectByExample(TransactionExample example);

    Transaction selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Transaction record, @Param("example") TransactionExample example);

    int updateByExample(@Param("record") Transaction record, @Param("example") TransactionExample example);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    List<Transaction> selectTransactionForPage(@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);

    /**
     * 根据id查询交易信息
     * @param id
     * @return
     */
    Transaction selectTransactionById(@Param("id") String id);


    /**
     * 查询统计图表需要的数据(每个交易阶段的交易数)
     * @return
     */
    List<ChartVo> selectChartData();



}