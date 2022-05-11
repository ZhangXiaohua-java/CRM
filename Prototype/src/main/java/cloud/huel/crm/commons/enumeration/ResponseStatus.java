package cloud.huel.crm.commons.enumeration;

/**
 * @author 张晓华
 * @version 1.0
 */
public enum ResponseStatus {

	SUCCESS("200"),FAIL("500");

	private String code;

	private ResponseStatus() {
	}

	ResponseStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ResponseStatus{" +
				"code='" + code + '\'' +
				'}';
	}

}
