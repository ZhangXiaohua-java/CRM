package cloud.huel.crm.settings.web.dao;

import cloud.huel.crm.settings.web.domain.DictionaryValue;
import cloud.huel.crm.settings.web.domain.DictionaryValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictionaryValueMapper {
    long countByExample(DictionaryValueExample example);

    int deleteByExample(DictionaryValueExample example);

    int deleteByPrimaryKey(String id);

    int insert(DictionaryValue record);

    int insertSelective(DictionaryValue record);

    List<DictionaryValue> selectByExample(DictionaryValueExample example);

    DictionaryValue selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DictionaryValue record, @Param("example") DictionaryValueExample example);

    int updateByExample(@Param("record") DictionaryValue record, @Param("example") DictionaryValueExample example);

    int updateByPrimaryKeySelective(DictionaryValue record);

    int updateByPrimaryKey(DictionaryValue record);

    List<DictionaryValue> queryDictionaryValueByTypeCode(@Param("typeCode") String typeCode);
}