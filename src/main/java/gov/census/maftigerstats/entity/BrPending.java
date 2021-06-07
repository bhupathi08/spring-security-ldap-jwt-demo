package gov.census.maftigerstats.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(schema = "BRPENDING", name = "BR_PENDING")
public class BrPending {

	@Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BRRULEID")
	private int brRuleId;

	@Column(name = "BRRULEDESC")
	private String brRuleDesc;

	@Column(name = "BRRULETYPE")
	private int brRuleType;

	@Column(name = "BRSUBMITTER")
	private String brSubmitter;

	@Basic
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "BRDATETIME")
	private Date brDateTime;

	@Column(name = "BRCRNUM")
	private String brCrNum;

	@Column(name = "IS_COMPLETE")
	private String brIsComplete;

	@Column(name = "COMPLETE_SUBMITTER")
	private String brCompleteSubmitter;

	@Basic
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "COMPLETE_DATETIME")
	private Date brCompleteDateTime;

	public int getBrRuleId() {
		return brRuleId;
	}

	public void setBrRuleId(int brRuleId) {
		this.brRuleId = brRuleId;
	}

	public String getBrRuleDesc() {
		return brRuleDesc;
	}

	public void setBrRuleDesc(String brRuleDesc) {
		this.brRuleDesc = brRuleDesc;
	}

	public int getBrRuleType() {
		return brRuleType;
	}

	public void setBrRuleType(int brRuleType) {
		this.brRuleType = brRuleType;
	}

	public String getBrSubmitter() {
		return brSubmitter;
	}

	public void setBrSubmitter(String brSubmitter) {
		this.brSubmitter = brSubmitter;
	}

	public Date getBrDateTime() {
		return brDateTime;
	}

	public void setBrDateTime(Date brDateTime) {
		this.brDateTime = brDateTime;
	}

	public String getBrCrNum() {
		return brCrNum;
	}

	public void setBrCrNum(String brCrNum) {
		this.brCrNum = brCrNum;
	}

	public String getBrIsComplete() {
		return brIsComplete;
	}

	public void setBrIsComplete(String brIsComplete) {
		this.brIsComplete = brIsComplete;
	}

	public String getBrCompleteSubmitter() {
		return brCompleteSubmitter;
	}

	public void setBrCompleteSubmitter(String brCompleteSubmitter) {
		this.brCompleteSubmitter = brCompleteSubmitter;
	}

	public Date getBrCompleteDateTime() {
		return brCompleteDateTime;
	}

	public void setBrCompleteDateTime(Date brCompleteDateTime) {
		this.brCompleteDateTime = brCompleteDateTime;
	}

}