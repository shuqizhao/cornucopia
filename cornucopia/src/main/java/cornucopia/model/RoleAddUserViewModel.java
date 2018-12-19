package cornucopia.model;

public class RoleAddUserViewModel{
    private int userId;
    private int[] roleIds;

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the roleIds
     */
    public int[] getRoleIds() {
        return roleIds;
    }

    /**
     * @param roleIds the roleIds to set
     */
    public void setRoleIds(int[] roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}