package cornucopia.model;

public class DoActionViewModel{
    private int userId;
    private String action;
    private int processDataId;
    private int approvePositionId;

    /**
     * @return the userId
     */
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

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}