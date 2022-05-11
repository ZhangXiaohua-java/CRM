package cloud.huel.crm.commons.enumeration;

/**
 * @author 张晓华
 * @version 1.0
 */
public enum SessionKeyList {
	USERNAME("username"),
	USER("user");
	private String key;

	SessionKeyList(String key) {
		this.key = key;
	}

	public String getKey(){
		return this.key;
	}

	@Override
	public String toString() {
		return "SessionKeyList{" +
				"key='" + key + '\'' +
				'}';
	}

}
