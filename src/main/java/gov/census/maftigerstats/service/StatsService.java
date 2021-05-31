package gov.census.maftigerstats.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import gov.census.maftigerstats.entity.BrExecute;
import gov.census.maftigerstats.entity.BrImplcatDesc;
import gov.census.maftigerstats.entity.BrPending;
import gov.census.maftigerstats.entity.BrRule;

public interface StatsService {

	public List<BrRule> getBrRuleInfo(String ruleid, String rulecat, String ruledesc, String implcat, String execute,
			String feattab, String relfeattab, String crActive);

	public List<BrRule> getBrRuleInfo_All();

	public List<BrRule> getRuleCategory();

	public List<BrRule> getFeatTab();

	public List<BrRule> getRelFeatTab();

	public List<BrImplcatDesc> getImplcatDesc();

	public List<BrRule> getRuleDesc(String ruleDesc);

	public List insertBrPending(BrPending brPending);

	public List<BrPending> getBrPending();

	public List updateBrPending(int brRuleId, String brSubmitter);

	public List<BrPending> getBrPendingNC();

	public List insertbrExecute(BrExecute brExecute);

	public List<BrExecute> getBrExecute();

}