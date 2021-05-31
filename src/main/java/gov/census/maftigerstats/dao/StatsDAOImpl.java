package gov.census.maftigerstats.dao;

import java.sql.SQLTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.dialect.lock.PessimisticEntityLockException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import gov.census.maftigerstats.entity.BrExecute;
import gov.census.maftigerstats.entity.BrImplcatDesc;
import gov.census.maftigerstats.entity.BrPending;
import gov.census.maftigerstats.entity.BrRule;

@Repository
public class StatsDAOImpl implements StatsDAO {

	/*
	 * // need to inject the session factory
	 * 
	 * @Autowired
	 * 
	 * @Qualifier(value="sessionFactory") private SessionFactory sessionFactory;
	 */

	@Autowired
	@Qualifier(value = "sessionFactory_T")
	private SessionFactory sessionFactory_T;

//US #130
	@Override
	public List<BrRule> getBrRuleInfo_All() {

// get the current hibernate session
		Session currentSession = sessionFactory_T.getCurrentSession();

		System.out.println("The getBrRuleInfo() method was called..!");

//Create the sql
		String sql = "FROM BrRule where  SUBSTR(brruleid, 6) = brid ORDER BY TO_NUMBER(SUBSTR(BRRULEID, 6, 7)) ASC, BRID ASC";

// retrieve main data from BrRule
		Query<BrRule> mainQuery = currentSession.createQuery(sql, BrRule.class);

// execute mainQuery and get main data list
		List<BrRule> mainData = mainQuery.getResultList();
		mainData.stream().peek(e -> {
			if (e.getBrexecute() == null) {
				e.setBrexecuteActive("On");
			} else {
				e.setBrexecuteActive("Off");
			}
		}).collect(Collectors.toList());

		return mainData;
	}

	@Override
	public List<BrRule> getBrRuleInfo(String ruleid, String rulecat, String ruledesc, String implcat, String execute,
			String feattab, String relfeattab, String crActive) {

// get the current hibernate session
		Session currentSession = sessionFactory_T.getCurrentSession();

		System.out.println("The getBrRuleInfo() method was called..!");
		if (ruleid != null)
			ruleid = "MTRBR" + ruleid;

		String ruleIDQuery = (ruleid != null && ruleid != "")
				? (ruleid.equals("MTRBR")) ? "" : "brruleid  = '" + ruleid + "'"
				: "";
		String feattabDQuery = (feattab != null && feattab != "") ? " brfeattab  = '" + feattab + "'" : "";
		String executeQuery = (execute != null) ? (execute.equals("OFF")) ? " brexecute IS NOT NULL "
				: (execute.equals("ON")) ? " brexecute is null " : "" : "";
		String implcatQuery = (implcat != null && implcat != "") ? " brimplcat  = '" + implcat + "'" : "";
		String rulecatQuery = (rulecat != null && rulecat != "") ? " brrulecat  = '" + rulecat + "'" : "";
		String relfeattabQuery = (relfeattab != null && relfeattab != "") ? " brrelfeattab  = '" + relfeattab + "'"
				: "";
		String ruledescQuery = (ruledesc != null && ruledesc != "")
				? " lower(brruledesc)  LIKE '%" + ruledesc.trim().toLowerCase().replace("'", "''") + "%'"
				: "";
		String crActiveQuery = (crActive != null) ? (crActive.equals("false")) ? "" : " SUBSTR(brruleid, 6) = brid"
				: " SUBSTR(brruleid, 6) = brid";

		ArrayList<String> queries = new ArrayList<String>();
		if (ruleIDQuery != "")
			queries.add(ruleIDQuery);
		if (feattabDQuery != "")
			queries.add(feattabDQuery);
		if (executeQuery != "")
			queries.add(executeQuery);
		if (implcatQuery != "")
			queries.add(implcatQuery);
		if (rulecatQuery != "")
			queries.add(rulecatQuery);
		if (relfeattabQuery != "")
			queries.add(relfeattabQuery);
		if (ruledescQuery != "")
			queries.add(ruledescQuery);
		if (crActiveQuery != "")
			queries.add(crActiveQuery);

		String sql = "";
		if (queries.size() == 0) {
			sql = "FROM BrRule";
			sql += " Order By SUBSTR(brruleid,6)+0 , brid ";
		} else if (queries.size() == 1) {
			sql = "From BrRule where " + queries.get(0);
			sql += " Order By SUBSTR(brruleid,6)+0 , brid ";
		} else if (queries.size() > 1) {
			sql += "From BrRule where ";
			for (String query : queries) {
				sql += query + " AND";
			}
			sql = sql.substring(0, sql.length() - 4);
			sql += " Order By SUBSTR(brruleid,6)+0 , brid ";
		}

		System.out.println(sql);
// retrieve main data from BrRule
		Query<BrRule> mainQuery = currentSession.createQuery(sql, BrRule.class);

// execute mainQuery and get main data list
		List<BrRule> mainData = mainQuery.getResultList();
		mainData.stream().peek(e -> {
			if (e.getBrexecute() == null) {
				e.setBrexecuteActive("On");
			} else {
				e.setBrexecuteActive("Off");
			}
		}).collect(Collectors.toList());

		return mainData;
	}

	@Override
	public List<BrRule> getRuleCategory() {
// get the current hibernate session
		Session currentSession = sessionFactory_T.getCurrentSession();

		System.out.println("The getBrRuleInfo() method was called..!");

//Create the sql
		String sql = "Select distinct(b.brrulecat)FROM BrRule b where b.brrulecat is not null ORDER BY b.brrulecat";
		System.out.println(sql);

// retrieve main data from BrRule
		Query mainQuery = currentSession.createQuery(sql);

// execute mainQuery and get main data list
		List mainData = mainQuery.getResultList();
//
		return mainData;
	}

	@Override
	public List<BrRule> getFeatTab() {
// TODO Auto-generated method stub
		Session currentSession = sessionFactory_T.getCurrentSession();

		System.out.println("The getBrRuleInfo() method was called..!");

//Create the sql
		String sql = "Select distinct(b.brfeattab)FROM BrRule b where b.brfeattab is not null ORDER BY b.brfeattab";
		System.out.println(sql);

// retrieve main data from BrRule
		Query mainQuery = currentSession.createQuery(sql);

// execute mainQuery and get main data list
		List mainData = mainQuery.getResultList();
//
		return mainData;
	}

	@Override
	public List<BrRule> getRelFeatTab() {
// TODO Auto-generated method stub
		Session currentSession = sessionFactory_T.getCurrentSession();
		System.out.println("The getBrRuleInfo() method was called..!");

//Create the sql
		String sql = "Select distinct(b.brrelfeattab) FROM BrRule b where b.brrelfeattab is not null ORDER BY brrelfeattab";
		System.out.println(sql);

// retrieve main data from BrRule
		Query mainQuery = currentSession.createQuery(sql);

// execute mainQuery and get main data list
		List mainData = mainQuery.getResultList();
//
		return mainData;
	}

	@Override
	public List<BrImplcatDesc> getImplcatDesc() {
// get the current hibernate session
		Session currentSession = sessionFactory_T.getCurrentSession();

		System.out.println("The getBrRuleInfo() method was called..!");

//Create the sql
		String sql = "select b from BrImplcatDesc b";

// retrieve main data from BrRule
		Query<BrImplcatDesc> mainQuery = currentSession.createQuery(sql, BrImplcatDesc.class);

// execute mainQuery and get main data list
		List<BrImplcatDesc> mainData = mainQuery.getResultList();
//
		return mainData;
	}

	@Override
	public List<BrRule> getRuleDesc(String ruleDesc) {
// TODO Auto-generated method stub
		Session currentSession = sessionFactory_T.getCurrentSession();
		System.out.println("The getBrRuleInfo() method was called..!");

//Create the sql
		String sql = "SELECT b FROM BrRule b WHERE lower(b.brruledesc) LIKE '%" + ruleDesc.trim().toLowerCase() + "%'";
		System.out.println(sql);

// retrieve main data from BrRule
		Query mainQuery = currentSession.createQuery(sql);

// execute mainQuery and get main data list
		List mainData = mainQuery.getResultList();
//
		return mainData;
	}

	@Override
	public List insertBrPending(BrPending brPending) {
// TODO Auto-generated method stub
		Session currentSession = sessionFactory_T.getCurrentSession();
		String crNum = "";
		if (brPending.getBrCrNum().length() > 4) {
			crNum = brPending.getBrCrNum().substring(0, 5);
		} else {
			crNum = brPending.getBrCrNum();
		}
		List list = currentSession.createNativeQuery("SELECT BRPENDING.BR_PENDING_PKG.ADD_REC_TO_BR_PENDING('"
				+ brPending.getBrRuleDesc() + "', " + brPending.getBrRuleType() + ", '" + brPending.getBrSubmitter()
				+ "', '" + crNum + "') FROM dual").getResultList();
		return list;
	}

	@Override
	public List<BrPending> getBrPending() {
// TODO Auto-generated method stub
		Session session = sessionFactory_T.getCurrentSession();
		List data = session.createQuery("Select p from BrPending p ORDER BY brRuleId ASC").getResultList();
		return data;
	}

	@Override
	public List updateBrPending(int brRuleId, String brSubmitter) {
// TODO Auto-generated method stub
		Session currentSession = sessionFactory_T.getCurrentSession();
		List data = null;
		List list = currentSession.createNativeQuery("SELECT BRPENDING.BR_PENDING_PKG.TOGGLE_IS_COMPLETE('" + brRuleId
				+ "' , '" + brSubmitter + "') FROM dual").getResultList();
		if (list.get(0).toString().contains("TOGGLE SUCCESSFUL")) {
			data = currentSession.createQuery("Select p from BrPending p where brIsComplete ='N' ORDER BY brRuleId ASC")
					.getResultList();
		} else {
			data.add(0, "Error: Submission is unsuccessfull");
		}
		return data;
	}

	@Override
	public List<BrPending> getBrPendingNC() {
// TODO Auto-generated method stub
		Session session = sessionFactory_T.getCurrentSession();
		List data = session.createQuery("Select p from BrPending p where brIsComplete ='N' ORDER BY brRuleId ASC")
				.getResultList();
		return data;
	}

	@Override
	public List insertBrPending(BrExecute brExecute) {
// TODO Auto-generated method stub
		Session currentSession = sessionFactory_T.getCurrentSession();
		String crNum = "";
		if (brExecute.getBrCrnum().length() > 4) {
			crNum = brExecute.getBrCrnum().substring(0, 5);
		} else {
			crNum = brExecute.getBrCrnum();
		}
		List list = currentSession.createNativeQuery(
				"SELECT BRPENDING.BR_EXECUTE_PKG.ADD_REC_TO_BR_EXECUTE('" + brExecute.getBrRuleID() + "', '" + crNum
						+ "', '" + brExecute.getExecStatus() + "', '" + brExecute.getBrSubmitter() + "') FROM dual")
				.getResultList();
		return list;
	}

	@Override
	public List<BrExecute> getBrExecute() {
		Session session = sessionFactory_T.getCurrentSession();
		List data = session.createQuery("Select e from BrExecute e ORDER BY requestDatetime DESC").getResultList();
		return data;
	}
}