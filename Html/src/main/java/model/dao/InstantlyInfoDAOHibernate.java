package model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.InstantlyInfoBean;
import model.InstantlyInfoDAO;

public class InstantlyInfoDAOHibernate implements InstantlyInfoDAO {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.config.xml");

		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		sessionFactory.getCurrentSession().beginTransaction();
		InstantlyInfoDAOHibernate DAO = new InstantlyInfoDAOHibernate(sessionFactory);

		// DAO.update("1", new Date());

		InstantlyInfoBean instantlyInfoBean = new InstantlyInfoBean();
		instantlyInfoBean.setStockId("2");
		instantlyInfoBean.setStockName("test");
		DAO.insert(instantlyInfoBean);

		DAO.update("1", "tessst", null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null);

		DAO.delete("2");
		
		System.out.println(DAO.select());

		sessionFactory.getCurrentSession().beginTransaction().commit();
		((ConfigurableApplicationContext) context).close();

	}

	private SessionFactory sessionFactory = null;

	public InstantlyInfoDAOHibernate(SessionFactory sessionfactory) {
		this.sessionFactory = sessionfactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<InstantlyInfoBean> select() {
		Query query = this.getSession().createQuery("from InstantlyInfoBean");
		return (List<InstantlyInfoBean>) query.list();
	}

	public InstantlyInfoBean select(String stockId) {
		return (InstantlyInfoBean) this.getSession().get(InstantlyInfoBean.class, stockId);
	}

	public InstantlyInfoBean insert(InstantlyInfoBean instantlyInfoBean) {
		InstantlyInfoBean result = (InstantlyInfoBean) this.getSession().get(InstantlyInfoBean.class,
				instantlyInfoBean.getStockId());
		if (result == null) {
			this.getSession().save(instantlyInfoBean);
			return instantlyInfoBean;
		}
		return null;
	}

	public boolean delete(String stockId) {
		InstantlyInfoBean bean = new InstantlyInfoBean();
		bean = (InstantlyInfoBean) this.getSession().get(InstantlyInfoBean.class, stockId);
		if (bean != null) {
			this.getSession().delete(bean);
			return true;
		}
		return false;
	}

	public InstantlyInfoBean update(String stockId, String stockName, Double finalPrice, Integer temporalVolume,
			Integer volume, String infomationTime, String infomationDate, Double high, Double low, Double openPrice,
			Double limitUpPoint, Double limitDownPoint, Double closingPrice, String a, String f, String b, String g) {
		InstantlyInfoBean bean = new InstantlyInfoBean();
		bean = (InstantlyInfoBean) this.getSession().get(InstantlyInfoBean.class, stockId);
		if (bean != null) {
			bean.setStockName(stockName);
			bean.setFinalPrice(finalPrice);
			bean.setTemporalVolume(temporalVolume);
			bean.setVolume(volume);
			bean.setInfomationTime(infomationTime);
			bean.setInfomationDate(infomationDate);
			bean.setHigh(high);
			bean.setLow(low);
			bean.getOpenPrice();
			bean.setLimitUpPoint(limitUpPoint);
			bean.setLimitDownPoint(limitDownPoint);
			bean.getClosingPrice();
			bean.setA(a);
			bean.setF(f);
			bean.setB(b);
			bean.setG(g);
		}
		return bean;
	}
}
