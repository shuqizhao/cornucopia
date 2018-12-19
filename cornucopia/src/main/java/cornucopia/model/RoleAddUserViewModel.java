package cornucopia.model;

public class RoleAddUserViewModel{
    private int roleId;
    private int[] userIds;

    /**
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * @return the userIds
     */
    public int[] getUserIds() {
        return userIds;
    }

    /**
     * @param userIds the userIds to set
     */
    public void setUserIds(int[] userIds) {
        this.userIds = userIds;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}