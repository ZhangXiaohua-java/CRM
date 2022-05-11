package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.ClueActivityRelation;
import cloud.huel.crm.workbench.web.domain.ClueActivityRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClueActivityRelationMapper {
    long countByExample(ClueActivityRelationExample example);

    int deleteByExample(ClueActivityRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(ClueActivityRelation record);

    int insertSelective(ClueActivityRelation record);

    List<ClueActivityRelation> selectByExample(ClueActivityRelationExample example);

    ClueActivityRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ClueActivityRelation record, @Param("example") ClueActivityRelationExample example);

    int updateByExample(@Param("record") ClueActivityRelation record, @Param("example") ClueActivityRelationExample example);

    int updateByPrimaryKeySelective(ClueActivityRelation record);

    int updateByPrimaryKey(ClueActivityRelation record);

    /**
     * 批量保存市场活动与线索之间的关系
     * @param clueActivityRelationList
     * @return
     */
    Integer addAssociation(List<ClueActivityRelation> clueActivityRelationList);


    /**
     * 删除市场活动和线索之间联系
     * @param clueActivityRelation
     * @return
     */
    Integer deleteClueActivityAssociation(ClueActivityRelation clueActivityRelation);



}