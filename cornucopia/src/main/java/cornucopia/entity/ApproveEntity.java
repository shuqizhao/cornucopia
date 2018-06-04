package cornucopia.entity;

public class ApproveEntity extends BaseEntity{
	private int parentId;
	private int processId;
	private int processNodeId;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

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
