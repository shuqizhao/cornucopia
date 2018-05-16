package cornucopia.model;

import java.util.List;

public class ResourceViewModel {
	private int id;
	private String name;
	private String icon;
	private List<ResourceViewModel> children;

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

	public List<ResourceViewModel> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceViewModel> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
