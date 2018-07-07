package cornucopia.entity;

public class ApprovePositionEntity extends BaseEntity {
	private int type;
	private int rule;
	private int processNodeId;
	private String vitualTitle;
	private int approveType;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRule() {
		return rule;
	}

	public void setRule(int rule) {
		this.rule = rule;
	}

	public int getProcessNodeId() {
		return processNodeId;
	}

	public void setProcessNodeId(int processNodeId) {
		this.processNodeId = processNodeId;
	}

	public String getVitualTitle() {
		return vitualTitle;
	}

	public void setVitualTitle(String vitualTitle) {
		this.vitualTitle = vitualTitle;
	}

	public int getApproveType() {
		return approveType;
	}

	public void setApproveType(int approveType) {
		this.approveType = approveType;
	}
}
