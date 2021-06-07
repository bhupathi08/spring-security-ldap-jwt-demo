package gov.census.maftigerstats.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
		CriteriaBuilder cb = currentSession.getCriteriaBuilder();
		CriteriaQuery<RefreshToken> cq = cb.createQuery(RefreshToken.class);
		Root<RefreshToken> root = cq.from(RefreshToken.class);
		cq.where(cb.equal(root.get("userName"), username));
		Query<RefreshToken> query = currentSession.createQuery(cq);
		if (query.uniqueResult() != null) {
			return true;
		}
		return false;
	}

	public RefreshToken getRefreshTokenByUsername(String username) {
		Session currentSession = sessionFactory_T.getCurrentSession();
		CriteriaBuilder cb = currentSession.getCriteriaBuilder();
		CriteriaQuery<RefreshToken> cq = cb.createQuery(RefreshToken.class);
		Root<RefreshToken> root = cq.from(RefreshToken.class);
		cq.where(cb.equal(root.get("userName"), username));
		Query<RefreshToken> query = currentSession.createQuery(cq);
		return query.uniqueResult();
	}

	public RefreshToken getRefreshTokenByToken(String token) {
		Session currentSession = sessionFactory_T.getCurrentSession();
		CriteriaBuilder cb = currentSession.getCriteriaBuilder();
		CriteriaQuery<RefreshToken> cq = cb.createQuery(RefreshToken.class);
		Root<RefreshToken> root = cq.from(RefreshToken.class);
		cq.where(cb.equal(root.get("token"), token));
		Query<RefreshToken> query = currentSession.createQuery(cq);
		return query.uniqueResult();
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
