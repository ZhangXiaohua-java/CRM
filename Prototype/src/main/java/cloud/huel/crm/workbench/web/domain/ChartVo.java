package cloud.huel.crm.workbench.web.domain;

/**
 * @author 张晓华
 * @version 1.0
 */

public class ChartVo {

	private String name;
	private Integer value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ChartVo{" +
				"name='" + name + '\'' +
				", value=" + value +
				'}';
	}
}
