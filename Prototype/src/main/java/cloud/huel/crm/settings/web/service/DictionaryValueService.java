package cloud.huel.crm.settings.web.service;

import cloud.huel.crm.commons.enumeration.DictionaryType;
import cloud.huel.crm.settings.web.domain.DictionaryValue;

import java.util.List;

/**
 * @author 张晓华
 * @version 1.0
 */
public interface DictionaryValueService {

	List<DictionaryValue> queryDictionaryValue(DictionaryType dictionaryType);

	DictionaryValue queryOrderNoByValue(String value);

}
