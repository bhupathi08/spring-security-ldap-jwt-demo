package gov.census.maftigerstats.dao;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import gov.census.maftigerstats.entity.RefreshToken;

@Repository
@Transactional
public class RefreshTokenRepository {
	

	@Autowired
	@Qualifier(value = "sessionFactory_T")
	private SessionFactory sessionFactory_T;
	
	public boolean checkRefreshTokenExistsByUsername(String username) {
		Session currentSession = sessionFactory_T.getCurrentSession();
		Criteria cr = currentSession.createCriteria(RefreshToken.class);
		if(cr.add(Restrictions.eq("userName", username)).uniqueResult() != null) {
			return true;
		}
		return false;
	}
	
	public RefreshToken getRefreshTokenByUsername(String username) {
		Session currentSession = sessionFactory_T.getCurrentSession();
		Criteria cr = currentSession.createCriteria(RefreshToken.class);
		RefreshToken refreshToken = (RefreshToken) cr.add(Restrictions.eq("userName", username)).uniqueResult();
		return refreshToken;
	}
	
	public RefreshToken getRefreshTokenByToken(String token) {
		Session currentSession = sessionFactory_T.getCurrentSession();
		Criteria cr = currentSession.createCriteria(RefreshToken.class);
		RefreshToken refreshToken = (RefreshToken) cr.add(Restrictions.eq("token", token)).uniqueResult();
		return refreshToken;
	}
	
	public RefreshToken save(RefreshToken refreshToken) {
		Session currentSession = sessionFactory_T.getCurrentSession();
		try {
			currentSession.persist(refreshToken);
			return refreshToken;
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
		}
		return null;
	}
	
	public void delete(RefreshToken refreshToken) {
		Session currentSession = sessionFactory_T.getCurrentSession();
		try {
			currentSession.delete(refreshToken);
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
		}
	}

}
