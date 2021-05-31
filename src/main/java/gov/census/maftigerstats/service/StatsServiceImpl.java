package gov.census.maftigerstats.service;

import java.sql.SQLException;
import java.util.List;

import gov.census.maftigerstats.dao.StatsDAO;
import gov.census.maftigerstats.entity.BrExecute;
import gov.census.maftigerstats.entity.BrImplcatDesc;
import gov.census.maftigerstats.entity.BrPending;
import gov.census.maftigerstats.entity.BrRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatsServiceImpl implements StatsService {

// need to inject stats dao
	@Autowired
	private StatsDAO statsDAO;

	@Override
	@Transactional("transactionManager_T")
	public List<BrRule> getBrRuleInfo(String ruleid, String rulecat, String ruledesc, String implicat, String execute,
			String feattab, String relfeattab, String crActive) {
// TODO Auto-generated method stub
		return statsDAO.getBrRuleInfo(ruleid, rulecat, ruledesc, implicat, execute, feattab, relfeattab, crActive);
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrRule> getBrRuleInfo_All() {
// TODO Auto-generated method stub
		return statsDAO.getBrRuleInfo_All();
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrRule> getRuleCategory() {
// TODO Auto-generated method stub
		return statsDAO.getRuleCategory();
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrRule> getFeatTab() {
// TODO Auto-generated method stub
		return statsDAO.getFeatTab();
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrRule> getRelFeatTab() {
// TODO Auto-generated method stub
		return statsDAO.getRelFeatTab();
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrImplcatDesc> getImplcatDesc() {
// TODO Auto-generated method stub
		return statsDAO.getImplcatDesc();
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrRule> getRuleDesc(String ruleDesc) {
// TODO Auto-generated method stub
		return statsDAO.getRuleDesc(ruleDesc);
	}

	@Override
	@Transactional("transactionManager_T")
	public List insertBrPending(BrPending brPending) {
// TODO Auto-generated method stub
		return statsDAO.insertBrPending(brPending);
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrPending> getBrPending() {
// TODO Auto-generated method stub
		return statsDAO.getBrPending();
	}

	@Override
	@Transactional("transactionManager_T")
	public List updateBrPending(int brRuleId, String brSubmitter) {
// TODO Auto-generated method stub
		return statsDAO.updateBrPending(brRuleId, brSubmitter);
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrPending> getBrPendingNC() {
// TODO Auto-generated method stub
		return statsDAO.getBrPendingNC();
	}

	@Override
	@Transactional("transactionManager_T")
	public List insertbrExecute(BrExecute brExecute) {
// TODO Auto-generated method stub
		return statsDAO.insertBrPending(brExecute);
	}

	@Override
	@Transactional("transactionManager_T")
	public List<BrExecute> getBrExecute() {
// TODO Auto-generated method stub
		return statsDAO.getBrExecute();
	}
}