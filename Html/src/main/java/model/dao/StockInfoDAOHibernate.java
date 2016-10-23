package model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.StockInfoBean;
import model.StockInfoDAO;

public class StockInfoDAOHibernate implements StockInfoDAO {
	private SessionFactory sessionFactory = null;

	public StockInfoDAOHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public StockInfoBean select(String stockId) {
		return (StockInfoBean) this.getSession().get(StockInfoBean.class, stockId);
	}

	@SuppressWarnings("unchecked")
	public List<StockInfoBean> select() {
		Query query = this.getSession().createQuery("from StockInfoBean");
		return (List<StockInfoBean>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> select_All_Id() {
		Query query = this.getSession().createQuery("select stockId from StockInfoBean");
		return (List<String>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<String> select_All_Group() {
		Query query = this.getSession().createQuery("select distinct groupName from StockInfoBean");
		return (List<String>) query.list();
	}
	
	public StockInfoBean insert(StockInfoBean bean) {
		StockInfoBean temp = this.getSession().get(StockInfoBean.class, bean.getStockId());
		if (temp == null) {
			this.getSession().save(bean);
			return bean;
		}
		return null;
	}
	
	public StockInfoBean update(String stockName, String groupName, String isinCode, String stockId) {
		StockInfoBean result = (StockInfoBean) this.getSession().get(StockInfoBean.class, stockId);
		if (result != null) {
			result.setStockName(stockName);
			result.setGroupName(groupName);
			result.setIsinCode(isinCode);
		}
		return result;
	}

	public boolean delete(String stockId) {
		StockInfoBean bean = this.getSession().get(StockInfoBean.class, stockId);
		if (bean != null) {
			this.getSession().delete(bean);
			return true;
		}
		return false;
	}
}
