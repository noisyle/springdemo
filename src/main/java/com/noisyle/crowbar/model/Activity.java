package com.noisyle.crowbar.model;

import com.noisyle.crowbar.core.base.BaseModel;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Activity extends BaseModel {
    private Long sponsorId;
    private String title;
    private String content;
    private Date startdate;
    private Integer status;

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
