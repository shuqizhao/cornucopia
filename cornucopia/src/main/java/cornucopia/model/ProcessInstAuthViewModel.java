package cornucopia.model;

public class ProcessInstAuthViewModel {
	private int id;
	private int userId;
	private String doaName;
	private String currentStep;
	private String preSign;
	private String afterSign;
	private String modify;
	private String transfer;
	private String vitualTitle;
	private String positionName;
	private String guid;
	private String parentGuid;
	private int approvePositionId;

	public int getUserId() {
		return userId;
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
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * @return the vitualTitle
	 */
	public String getVitualTitle() {
		return vitualTitle;
	}

	/**
	 * @param vitualTitle the vitualTitle to set
	 */
	public void setVitualTitle(String vitualTitle) {
		this.vitualTitle = vitualTitle;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getButtonType() {
		return doaName;
	}

	public void setButtonType(String doaName) {
		this.doaName = doaName;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	
	/**
	 * @return the transfer
	 */
	public String getTransfer() {
		return transfer;
	}

	/**
	 * @param transfer the transfer to set
	 */
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	/**
	 * @return the modify
	 */
	public String getModify() {
		return modify;
	}

	/**
	 * @param modify the modify to set
	 */
	public void setModify(String modify) {
		this.modify = modify;
	}

	/**
	 * @return the afterSign
	 */
	public String getAfterSign() {
		return afterSign;
	}

	/**
	 * @param afterSign the afterSign to set
	 */
	public void setAfterSign(String afterSign) {
		this.afterSign = afterSign;
	}

	/**
	 * @return the preSign
	 */
	public String getPreSign() {
		return preSign;
	}

	/**
	 * @param preSign the preSign to set
	 */
	public void setPreSign(String preSign) {
		this.preSign = preSign;
	}
}
