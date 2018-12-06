package cornucopia.model;

public class UserSearchViewModel{
    private int orgId;
    private String name;
    private int isEnable;

    /**
     * @return the orgId
     */
    public int getOrgId() {
        return orgId;
    }

    /**
     * @return the isEnable
     */
    public int getIsEnable() {
        return isEnable;
    }

    /**
     * @param isEnable the isEnable to set
     */
    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
}