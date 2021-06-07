package gov.census.maftigerstats.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MTBR.BR_RULE")
public class BrRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BRID")
	private int brid;

	@Column(name = "BRRULEID")
	private String brruleid;

	@Column(name = "BRRULEDESC")
	private String brruledesc;

	@Column(name = "BRRULECAT")
	private String brrulecat;

	@Column(name = "BRIMPLCAT")
	private String brimplcat;

	@Column(name = "BREXECUTE")
	private String brexecute;

	@Column(name = "BRFEATTAB")
	private String brfeattab;

	@Column(name = "BRRELFEATTAB")
	private String brrelfeattab;

	@Column(name = "BRSPATREL")
	private String brspatrel;

	@Transient
	private String brexecuteActive;

	@Transient
	public String getBrexecuteActive() {
		return brexecuteActive;
	}

	@Transient
	public void setBrexecuteActive(String brexecuteActive) {
		this.brexecuteActive = brexecuteActive;
	}

	public String getBrimplcat() {
		return brimplcat;
	}

	public void setBrimplcat(String brimplcat) {
		this.brimplcat = brimplcat;
	}

	public int getBrid() {
		return brid;
	}

	public void setBrid(int brid) {
		this.brid = brid;
	}

	public String getBrruleid() {
		return brruleid;
	}

	public void setBrruleid(String brruleid) {
		this.brruleid = brruleid;
	}

	public String getBrruledesc() {
		return brruledesc;
	}

	public void setBrruledesc(String brruledesc) {
		this.brruledesc = brruledesc;
	}

	public String getBrrulecat() {
		return brrulecat;
	}

	public void setBrrulecat(String brrulecat) {
		this.brrulecat = brrulecat;
	}

	public String getBrexecute() {
		return brexecute;
	}

	public void setBrexecute(String brexecute) {
		this.brexecute = brexecute;
	}

	public String getBrfeattab() {
		return brfeattab;
	}

	public void setBrfeattab(String brfeattab) {
		this.brfeattab = brfeattab;
	}

	public String getBrrelfeattab() {
		return brrelfeattab;
	}

	public void setBrrelfeattab(String brrelfeattab) {
		this.brrelfeattab = brrelfeattab;
	}

	public String getBrspatrel() {
		return brspatrel;
	}

	public void setBrspatrel(String brspatrel) {
		this.brspatrel = brspatrel;
	}

}