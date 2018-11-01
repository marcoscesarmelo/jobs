package br.com.job.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "job")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Job {
	

	public Job() {}
	
	public Job(Integer partnerId, String title, Integer categoryId, Date expiresAt) {
		this.partnerId = partnerId;
		this.title = title;
		this.categoryId = categoryId;
		this.expiresAt = expiresAt;
	}

	@Id
	@Column(name = "partner_id")
    private Integer partnerId;
	
	@Column(name = "title")
    private String title;
	
	@Column(name = "category_id")
    private Integer categoryId;
	
	@Column(name = "expires_at")
    private Date expiresAt;
	
	@Column(name = "status")
    private Integer status;	

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}	

}
