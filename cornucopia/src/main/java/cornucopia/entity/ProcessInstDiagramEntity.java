package cornucopia.entity;

public class ProcessInstDiagramEntity extends BaseEntity {
	private int processId;
	private int processDataId;
	private int userId;
	private String userName;
	private int levelCount;
	private int isCurrent;

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getProcessDataId() {
		return processDataId;
	}

	public void setProcessDataId(int processDataId) {
		this.processDataId = processDataId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(int levelCount) {
		this.levelCount = levelCount;
	}

	public int getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}
}
