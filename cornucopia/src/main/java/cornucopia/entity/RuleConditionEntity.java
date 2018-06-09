package cornucopia.entity;

public class RuleConditionEntity extends BaseEntity{
	private int ruleId;
	private int boolOperation;
	private String var1;
	private int var1From;
	private int var1Type;
	private int operation;
	private String var2;
	private int var2From;
	private int var2Type;

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
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

	public int getVar1Type() {
		return var1Type;
	}

	public void setVar1Type(int var1Type) {
		this.var1Type = var1Type;
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

	public int getVar2Type() {
		return var2Type;
	}

	public void setVar2Type(int var2Type) {
		this.var2Type = var2Type;
	}
}
