package cornucopia.entity;

public class ApproveMatrixEntity extends BaseEntity {
	private int processId;
	private int processNodeId;
	private int approveCondition1Id;
	private int approveCondition2Id;
	private int approvePositionId;
	private int isChecked;

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getProcessNodeId() {
		return processNodeId;
	}

	public void setProcessNodeId(int processNodeId) {
		this.processNodeId = processNodeId;
	}

	public int getApproveCondition1Id() {
		return approveCondition1Id;
	}

	public void setApproveCondition1Id(int approveCondition1Id) {
		this.approveCondition1Id = approveCondition1Id;
	}

	public int getApproveCondition2Id() {
		return approveCondition2Id;
	}

	public void setApproveCondition2Id(int approveCondition2Id) {
		this.approveCondition2Id = approveCondition2Id;
	}

	public int getApprovePositionId() {
		return approvePositionId;
	}

	public void setApprovePositionId(int approvePositionId) {
		this.approvePositionId = approvePositionId;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}
}
