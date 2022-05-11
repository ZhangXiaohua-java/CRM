package cloud.huel.crm.commons.enumeration;

/**
 * @author 张晓华
 * @version 1.0
 */
public enum DictionaryType {
	APPELLATION("称呼","appellation"), CLUE_STATE("线索状态","clueState"),
	RETURN_PRIORITY("回访优先级","returnPriority"), RETURN_STATE("回访状态","returnState"),
	SOURCE("来源","source"), STAGE("阶段","stage"), TRANSACTION_TYPE("交易类型","transactionType");


	DictionaryType() {
	}
	private String name;
	private String code;
	DictionaryType(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}
