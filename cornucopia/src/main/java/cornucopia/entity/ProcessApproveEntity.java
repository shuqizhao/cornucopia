package cornucopia.entity;

public class ProcessApproveEntity extends BaseEntity{
	private String guid;
    private int processId;
	private String processName;
	private String procinstId;
	private int levelCount;
	private String stepName;
	private int processDataId;
	private int userId;
	private String parentGuid;
	private int approvePositionId;

	public int getProcessId() {
		return processId;
	}

	/**
	 * @return the approvePositionId
	 */
	public int getApprovePositionId() {
		return approvePositionId;
	}

	/**
	 * @param approvePositionId the approvePositionId to set
	 */
	public void setApprovePositionId(int approvePositionId) {
		this.approvePositionId = approvePositionId;
	}

	/**
	 * @return the parentGuid
	 */
	public String getParentGuid() {
		return parentGuid;
	}

	/**
	 * @param parentGuid the parentGuid to set
	 */
	public void setParentGuid(String parentGuid) {
		this.parentGuid = parentGuid;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
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