package cornucopia.entity;

import java.util.List;

public class ProcessCategoryEntity extends BaseEntity {
	private int orderNum;

	private String icon;

	private List<ProcessEntity> proesses;

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<ProcessEntity> getProesses() {
		return proesses;
	}

	public void setProesses(List<ProcessEntity> proesses) {
		this.proesses = proesses;
	}
}
