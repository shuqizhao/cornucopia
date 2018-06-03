package cornucopia.entity;

public class ApproveConditionEntity extends BaseEntity {
	private int approveId;
	private int boolOperation;
	private String var1;
	private int var1From;
	private int varType;
	private int operation;
	private String var2;
	private int var2From;

	public int getApproveId() {
		return approveId;
	}

	public void setApproveId(int approveId) {
		this.approveId = approveId;
	}

	public int getBoolOperation() {
		return boolOperation;
	}

	public void setBoolOperation(int boolOperation) {
		this.boolOperation = boolOperation;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public int getVar1From() {
		return var1From;
	}

	public void setVar1From(int var1From) {
		this.var1From = var1From;
	}

	public int getVarType() {
		return varType;
	}

	public void setVarType(int varType) {
		this.varType = varType;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}

	public int getVar2From() {
		return var2From;
	}

	public void setVar2From(int var2From) {
		this.var2From = var2From;
	}

}
