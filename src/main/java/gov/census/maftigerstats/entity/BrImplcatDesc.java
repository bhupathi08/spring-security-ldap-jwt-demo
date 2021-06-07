package gov.census.maftigerstats.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BRPENDING.BR_IMPLCAT_DESC")
public class BrImplcatDesc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BRIMPLCAT")
	private String brimplcat;

	@Column(name = "BRIMPLCAT_DESC")
	private String brimplcatdesc;

	public String getbrimplcat() {
		return brimplcat;
	}

	public void setbrimplcat(String brimplcat) {
		this.brimplcat = brimplcat;
	}

	public String getbrimplcatdesc() {
		return brimplcatdesc;
	}

	public void setbrimplcatdesc(String brimplcatdesc) {
		this.brimplcatdesc = brimplcatdesc;
	}

}