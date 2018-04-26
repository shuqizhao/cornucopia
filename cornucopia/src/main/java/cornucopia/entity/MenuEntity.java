package cornucopia.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuEntity extends BaseEntity {

	@JsonProperty(value = "Id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonProperty(value = "Name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty(value = "ParentId")
	private int parentId;

	@JsonProperty(value = "Url")
	private String url;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
