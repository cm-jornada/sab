package simple.auth.boot.dmo;

import java.util.Date;
import java.util.List;

public class User {
    private String id;
    private String userName;// 用户名
    private String password;// 密码
    private String enable;// 1:激活 0:未激活
    private String phone;// 手机号
    private String email;// 邮箱
    private String extendField;// json格式扩展字段
    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private List<Role> roleList;// 角色列表
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the enable
     */
    public String getEnable() {
        return enable;
    }
    /**
     * @param enable the enable to set
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the extendField
     */
    public String getExtendField() {
        return extendField;
    }
    /**
     * @param extendField the extendField to set
     */
    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }
    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
     * @return the roleList
     */
    public List<Role> getRoleList() {
        return roleList;
    }
    /**
     * @param roleList the roleList to set
     */
    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
    
}
