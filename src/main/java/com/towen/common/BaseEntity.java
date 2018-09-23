package com.towen.common;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    /**
     * 数据记录的创建者
     */
    public long createBy;

    /**
     * 数据记录的创建时间
     */
    public String createDate;

    /**
     * 数据记录的最后更新者
     */
    public long updateBy;

    /**
     * 数据记录的最后更新时间
     */
    public String updateDate;

    /**
     * 状态
     * @return
     */
    public String status;

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(long updateBy) {
        this.updateBy = updateBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
