package cornucopia.entity;

public class ProcessInstDiagramEntity extends BaseEntity {
	private String guid;
	private int processId;
	private int processDataId;
	private int userId;
	private String userName;
	private int levelCount;
	private int isCurrent;
	private String email;
	private String personNumber;
	private int parentId;
	private String parentGuid;

	public int getProcessId() {
		return processId;
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
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}
}
