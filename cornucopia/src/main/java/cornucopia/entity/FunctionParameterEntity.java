package cornucopia.entity;

public class FunctionParameterEntity extends BaseEntity {
	private String desc;
	private int funcId;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getFuncId() {
		return funcId;
	}

	public void setFuncId(int funcId) {
		this.funcId = funcId;
	}
}
