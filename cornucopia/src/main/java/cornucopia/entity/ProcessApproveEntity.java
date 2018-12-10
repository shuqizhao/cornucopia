package cornucopia.entity;

public class ProcessApproveEntity extends BaseEntity{
    private int processId;
	private String processName;
	private String procinstId;
	private int levelCount;
	private String stepName;
	private int processDataId;
	private int userId;
	private int processInstDiagramId;

	public int getProcessId() {
		return processId;
	}

	/**
	 * @return the processInstDiagramId
	 */
	public int getProcessInstDiagramId() {
		return processInstDiagramId;
	}

	/**
	 * @param processInstDiagramId the processInstDiagramId to set
	 */
	public void setProcessInstDiagramId(int processInstDiagramId) {
		this.processInstDiagramId = processInstDiagramId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the processDataId
	 */
	public int getProcessDataId() {
		return processDataId;
	}

	/**
	 * @param processDataId the processDataId to set
	 */
	public void setProcessDataId(int processDataId) {
		this.processDataId = processDataId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getProcinstId() {
		return procinstId;
	}

	public void setProcinstId(String procinstId) {
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

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
}