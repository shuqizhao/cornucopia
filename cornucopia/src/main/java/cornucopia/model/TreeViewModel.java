package cornucopia.model;

import java.util.List;

public class TreeViewModel {
	private int id;
	private String name;
	private String icon;
	private List<TreeViewModel> children;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreeViewModel> getChildren() {
		return children;
	}

	public void setChildren(List<TreeViewModel> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
