package cornucopia.model;

import cornucopia.entity.ProcessDataEntity;

public class ProcessMonitorViewModel extends ProcessDataEntity {
    private String loginName;

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}