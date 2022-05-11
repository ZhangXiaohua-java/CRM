package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.ClueRemark;
import cloud.huel.crm.workbench.web.domain.ClueRemarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClueRemarkMapper {
    long countByExample(ClueRemarkExample example);

    int deleteByExample(ClueRemarkExample example);

    int deleteByPrimaryKey(String id);

    int insert(ClueRemark record);

    int insertSelective(ClueRemark record);

    List<ClueRemark> selectByExample(ClueRemarkExample example);

    ClueRemark selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ClueRemark record, @Param("example") ClueRemarkExample example);

    int updateByExample(@Param("record") ClueRemark record, @Param("example") ClueRemarkExample example);

    int updateByPrimaryKeySelective(ClueRemark record);

    int updateByPrimaryKey(ClueRemark record);

    /**
     * 根据线索ID查询对应的备注信息
     * @param clueId
     * @return
     */
    List<ClueRemark> queryClueRemarksByClueId(@Param("clueId") String clueId);


    List<ClueRemark> queryClueRemarkByClueId(@Param("clueId") String clueId);

}