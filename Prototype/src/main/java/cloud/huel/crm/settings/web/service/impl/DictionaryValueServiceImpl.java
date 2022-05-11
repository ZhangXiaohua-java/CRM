package cloud.huel.crm.settings.web.service.impl;

import cloud.huel.crm.commons.enumeration.DictionaryType;
import cloud.huel.crm.settings.web.dao.DictionaryValueMapper;
import cloud.huel.crm.settings.web.domain.DictionaryValue;
import cloud.huel.crm.settings.web.domain.DictionaryValueExample;
import cloud.huel.crm.settings.web.service.DictionaryValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */

@Service
public class DictionaryValueServiceImpl implements DictionaryValueService {
	public static final String APPELLATION = "称呼";
	public static final String CLUE_STATE = "线索状态";
	public static final String RETURN_PRIORITY = "回访优先级";
	public static final String RETURN_STATE = "回访状态";
	public static final String SOURCE = "来源";
	public static final String STAGE = "阶段";
	public static final String TRANSACTION_TYPE = "交易类型";

	@Resource
	private DictionaryValueMapper dictionaryValueMapper;

	@Override
	public List<DictionaryValue> queryDictionaryValue(DictionaryType dictionaryType) {
		return dictionaryValueMapper.queryDictionaryValueByTypeCode(dictionaryType.getCode());
	}


	@Override
	public DictionaryValue queryOrderNoByValue(String value) {
		DictionaryValueExample example = new DictionaryValueExample();
		example.createCriteria().andValueEqualTo(value);
		return dictionaryValueMapper.selectByExample(example).get(0);
	}

}
