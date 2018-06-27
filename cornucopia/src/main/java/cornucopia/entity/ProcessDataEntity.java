package cornucopia.entity;

public class ProcessDataEntity extends BaseEntity {
	private int processId;
	private String bizData;
	private String formCode;
	private int procinstId;
	private int levelCount;
	private String stepName;

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

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public int getProcinstId() {
		return procinstId;
	}

	public void setProcinstId(int procinstId) {
		this.procinstId = procinstId;
	}

	public int getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(int levelCount) {
		this.levelCount = levelCount;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
}
