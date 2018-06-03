package cornucopia.entity;

public class ApproveEntity extends BaseEntity{
	private int processId;
	private int processNodeId;

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
}
