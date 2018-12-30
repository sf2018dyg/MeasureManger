package com.soonfor.measuremanager.home.login.bean;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/14 0014 11:20
 * 邮箱：suibozhu@139.com
 * 当前登录用户
 */

public class CurrentUserBean {

    /*{"userId":"ACS05739999170004","userName":"测试店长","salesId":442528687,"salesCode":"ACS05739999170004","roleId":"4","roleName":"店长"}*/

    private String userId;
    private String userName;
    private String salesId;
    private String salesCode;
    private String roleId;
    private boolean roleName;

    public String getUserId() {
        return userId==null?"":userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName==null?"":userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId==null?"":roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isRoleName() {
        return roleName;
    }

    public void setRoleName(boolean roleName) {
        this.roleName = roleName;
    }

    public String getSalesId() {
        return salesId==null?"":salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getSalesCode() {
        return salesCode==null?"":salesCode;
    }

    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }
}
