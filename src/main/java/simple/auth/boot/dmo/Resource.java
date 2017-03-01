package simple.auth.boot.dmo;

import java.util.Date;
import java.util.List;

public class Resource {

    private String id;
    private String fatherId;// 父资源id
    private String code;// 资源code
    private String level;// 分级，可以理解为数的层
    private String status;// 状态 0：失效 1：有效

    private String icon;// 图标
    private String title;// 文字描述
    private String url;// 对应地址

    private Date createTime;// 创建时间
    private Date updateTime;// 更新时间
    private List<Role> roleList;// 角色列表

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the fatherId
     */
    public String getFatherId() {
        return fatherId;
    }

    /**
     * @param fatherId the fatherId to set
     */
    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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

}
