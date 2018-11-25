package cornucopia.entity;

public class ApprovePositionEntity extends BaseEntity {
	private int type;
	private int rule;
	private int processNodeId;
	private String vitualTitle;
	private int approveType;
	private Boolean preSign;
	private Boolean afterSign;
	private Boolean transfer;
	private Boolean modify;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRule() {
		return rule;
	}

	public void setRule(int rule) {
		this.rule = rule;
	}

	public int getProcessNodeId() {
		return processNodeId;
	}

	public void setProcessNodeId(int processNodeId) {
		this.processNodeId = processNodeId;
	}

	public String getVitualTitle() {
		return vitualTitle;
	}

	public void setVitualTitle(String vitualTitle) {
		this.vitualTitle = vitualTitle;
	}

	public int getApproveType() {
		return approveType;
	}

	public void setApproveType(int approveType) {
		this.approveType = approveType;
	}


	/**
	 * @return the modify
	 */
	public Boolean getModify() {
		return modify;
	}

	/**
	 * @param modify the modify to set
	 */
	public void setModify(Boolean modify) {
		this.modify = modify;
	}

	/**
	 * @return the transfer
	 */
	public Boolean getTransfer() {
		return transfer;
	}

	/**
	 * @param transfer the transfer to set
	 */
	public void setTransfer(Boolean transfer) {
		this.transfer = transfer;
	}

	/**
	 * @return the afterSign
	 */
	public Boolean getAfterSign() {
		return afterSign;
	}

	/**
	 * @param afterSign the afterSign to set
	 */
	public void setAfterSign(Boolean afterSign) {
		this.afterSign = afterSign;
	}

	/**
	 * @return the preSign
	 */
	public Boolean getPreSign() {
		return preSign;
	}

	/**
	 * @param preSign the preSign to set
	 */
	public void setPreSign(Boolean preSign) {
		this.preSign = preSign;
	}
}
