package cloud.huel.crm.workbench.web.service;

import cloud.huel.crm.workbench.web.domain.Clue;

import java.util.List;
import java.util.Map;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface ClueService {
	/**
	 * 分页查询线索信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Clue> queryCluesWithPageNo(Integer pageNo,Integer pageSize);

	/**
	 * 保存添加的线索记录
	 * @param clue
	 * @return
	 */
	Integer saveClue(Clue clue);

	/**
	 * 查询表中所有记录数
	 * @return
	 */
	Integer countRecordsCount();

	/**
	 * 查询线索的详细信息
	 * @param id
	 * @return
	 */
	Clue queryClueInfo(String id);

	/**
	 * 事务保证线索转换的完成
	 * @param parameterMap
	 */
	void convert(Map<String,Object> parameterMap);


}
