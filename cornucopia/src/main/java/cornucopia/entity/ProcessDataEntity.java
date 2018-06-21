package cornucopia.entity;

public class ProcessDataEntity extends BaseEntity {
	private int processId;
	private String bizData;

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getBizData() {
		return bizData;
	}

	public void setBizData(String bizData) {
		this.bizData = bizData;
	}
}
