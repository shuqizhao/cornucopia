package cornucopia.entity;

public class ParainstEntity extends BaseEntity {
	private String parainstId;
	private int functionId;
	private String parainstJson;

	public String getParainstId() {
		return parainstId;
	}

	public void setParainstId(String parainstId) {
		this.parainstId = parainstId;
	}

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getParainstJson() {
		return parainstJson;
	}

	public void setParainstJson(String parainstJson) {
		this.parainstJson = parainstJson;
	}
}
