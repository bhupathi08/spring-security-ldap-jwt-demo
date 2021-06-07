package gov.census.maftigerstats.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(schema = "BRPENDING", name = "BR_EXECUTE")
public class BrExecute {

	@Id
	@Column(name = "BRRULEID")
	private int brRuleID;

	@Column(name = "BRCRNUM")
	private String brCrnum;

	@Column(name = "EXEC_STATUS")
	private String execStatus;

	@Column(name = "BRSUBMITTER")
	private String brSubmitter;

	@Basic
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "REQUEST_DATETIME")
	private Date requestDatetime;

	@Column(name = "UPDATE_STATUS")
	private String updateStatus;

	@Column(name = "UPDATE_ERROR")
	private String updateError;

	@Basic
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATETIME")
	private Date updateDatetime;

	public int getBrRuleID() {
		return brRuleID;
	}

	public void setBrRuleID(int brRuleID) {
		this.brRuleID = brRuleID;
	}

	public String getBrCrnum() {
		return brCrnum;
	}

	public void setBrCrnum(String brCrnum) {
		this.brCrnum = brCrnum;
	}

	public String getExecStatus() {
		return execStatus;
	}

	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}

	public String getBrSubmitter() {
		return brSubmitter;
	}

	public void setBrSubmitter(String brSubmitter) {
		this.brSubmitter = brSubmitter;
	}

	public Date getRequestDatetime() {
		return requestDatetime;
	}

	public void setRequestDatetime(Date requestDatetime) {
		this.requestDatetime = requestDatetime;
	}

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String getUpdateError() {
		return updateError;
	}

	public void setUpdateError(String updateError) {
		this.updateError = updateError;
	}

	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

}