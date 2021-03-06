package cornucopia.entity;

public class ProcessDataEntity extends BaseEntity {
	private int processId;
	private String processName;
	private String bizData;
	private String jsonData;
	private String formCode;
	private String procinstId;
	private int levelCount;
	private String stepName;
	private String createName;
	private String updateName;
	private String url;
	private int condition;
	private int status;
	private int callbackStatus;
	private int processStatus;

	public int getProcessId() {
		return processId;
	}

	/**
	 * @return the processStatus
	 */
	public int getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @return the callbackStatus
	 */
	public int getCallbackStatus() {
		return callbackStatus;
	}

	/**
	 * @param callbackStatus the callbackStatus to set
	 */
	public void setCallbackStatus(int callbackStatus) {
		this.callbackStatus = callbackStatus;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
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

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}
}
