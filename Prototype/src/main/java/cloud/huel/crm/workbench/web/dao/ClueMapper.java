package cloud.huel.crm.workbench.web.dao;

import cloud.huel.crm.workbench.web.domain.Clue;
import cloud.huel.crm.workbench.web.domain.ClueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClueMapper {
    long countByExample(ClueExample example);

    int deleteByExample(ClueExample example);

    int deleteByPrimaryKey(String id);

    int insert(Clue record);

    int insertSelective(Clue record);

    List<Clue> selectByExample(ClueExample example);

    Clue selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Clue record, @Param("example") ClueExample example);

    int updateByExample(@Param("record") Clue record, @Param("example") ClueExample example);

    int updateByPrimaryKeySelective(Clue record);

    int updateByPrimaryKey(Clue record);

    List<Clue> selectCluesForPage(@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);

    Integer queryRecordCount();

    Clue queryClueInfo(@Param("id") String id);


}