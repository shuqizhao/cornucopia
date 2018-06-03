package cornucopia.model;

import cornucopia.entity.ApproveConditionEntity;
import cornucopia.entity.ApproveEntity;

public class ApproveViewModel {
	private ApproveEntity approve;
	private ApproveConditionEntity[] approveConditions;

	public ApproveEntity getApprove() {
		return approve;
	}

	public void setApprove(ApproveEntity approve) {
		this.approve = approve;
	}

	public ApproveConditionEntity[] getApproveConditions() {
		return approveConditions;
	}

	public void setApproveConditions(ApproveConditionEntity[] approveConditions) {
		this.approveConditions = approveConditions;
	}
}
