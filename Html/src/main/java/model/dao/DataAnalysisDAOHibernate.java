package model.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.DataAnalysisBean;
import model.DataAnalysisDAO;

public class DataAnalysisDAOHibernate implements DataAnalysisDAO {

	public static void main(String[] args) throws ParseException  {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.config.xml");

		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		sessionFactory.getCurrentSession().beginTransaction();
		DataAnalysisDAOHibernate DAO = new DataAnalysisDAOHibernate(sessionFactory);

		DateFormat d = new SimpleDateFormat("yyyy/MM/dd");

		System.out.println(DAO.select(null, d.parse("2016-10-14"), d.parse("2016-10-18")).size());

		// DAO.update("1", new Date(), null, null, null, null, null, null, null,
		// null, null, null, null, null);

		sessionFactory.getCurrentSession().beginTransaction().commit();
		((ConfigurableApplicationContext) context).close();

	}
	
	private SessionFactory sessionFactory = null;

	public DataAnalysisDAOHibernate(SessionFactory sessionfactory) {
		this.sessionFactory = sessionfactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<DataAnalysisBean> select(String stockId, Date start, Date end) {

		if (stockId != null ) {
			if (start != null) {
				if (end != null) {
					Query query = this.getSession().createQuery(
							"from DataAnalysisBean D where D.stockId = :stock_Id AND D.buildDate >= :start AND D.buildDate <= :end");
					query.setParameter("stock_Id", stockId);
					query.setParameter("start", new SimpleDateFormat("yyyy/MM/dd").format(start));
					query.setParameter("end", new SimpleDateFormat("yyyy/MM/dd").format(end));
					return (List<DataAnalysisBean>) query.list();
				} else if (end == null) {
					Query query = this.getSession().createQuery(
							"from DataAnalysisBean D where D.stockId = :stock_Id AND D.buildDate >= :start");
					query.setParameter("stock_Id", stockId);
					query.setParameter("start", new SimpleDateFormat("yyyy/MM/dd").format(start));
					return (List<DataAnalysisBean>) query.list();

				}

			} else if (start == null) {
				if (end != null) {// !=!

					Query query = this.getSession()
							.createQuery("from DataAnalysisBean D where D.stockId = :stock_Id AND D.buildDate <= :end");
					query.setParameter("stock_Id", stockId);
					query.setParameter("end", new SimpleDateFormat("yyyy/MM/dd").format(end));

					return (List<DataAnalysisBean>) query.list();
				} else if (end == null) {// !==
					Query query = this.getSession().createQuery("from DataAnalysisBean D where D.stockId = :stock_Id");
					query.setParameter("stock_Id", stockId);
					return (List<DataAnalysisBean>) query.list();
				}
			}

		} else if (stockId == null || stockId.length() == 0) {
			if (start != null) {
				if (end != null) {// =!!
					Query query = this.getSession()
							.createQuery("from DataAnalysisBean D where D.buildDate >= :start AND D.buildDate <= :end");
					query.setParameter("start", new SimpleDateFormat("yyyy/MM/dd").format(start));
					query.setParameter("end", new SimpleDateFormat("yyyy/MM/dd").format(end));
					return (List<DataAnalysisBean>) query.list();
				} else if (end == null) {
					// =!=
					Query query = this.getSession().createQuery("from DataAnalysisBean D where D.buildDate >= :start");
					query.setParameter("start", new SimpleDateFormat("yyyy/MM/dd").format(start));
					return (List<DataAnalysisBean>) query.list();
				}

			} else if (start == null) {
				if (end != null) {
					// ==!
					Query query = this.getSession().createQuery("from DataAnalysisBean D where D.buildDate <= :end");
					query.setParameter("end", new SimpleDateFormat("yyyy/MM/dd").format(end));
					return (List<DataAnalysisBean>) query.list();
				} else if (end == null) {
					// ===
					Query query = this.getSession().createQuery("from DataAnalysisBean");
					return (List<DataAnalysisBean>) query.list();
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<DataAnalysisBean> select() {
		Query query = this.getSession().createQuery("from DataAnalysisBean");
		return (List<DataAnalysisBean>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<DataAnalysisBean> select(String stockId) {
		Query query = this.getSession().createQuery("from DataAnalysisBean D where D.stockId = :stock_id ");
		query.setParameter("stock_id", stockId);
		return (List<DataAnalysisBean>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<DataAnalysisBean> select(Date buildDate) {
		Query query = this.getSession().createQuery("from DataAnalysisBean D where D.buildDate = :build_Date");
		query.setParameter("build_Date", new SimpleDateFormat("yyyy-MM-dd").format(buildDate));
		return (List<DataAnalysisBean>) query.list();
	}

	public DataAnalysisBean select(String stockId, Date buildDate) {
		DataAnalysisBean bean = new DataAnalysisBean();
		bean.setStockId(stockId);
		bean.setBuildDate(new SimpleDateFormat("yyyy-MM-dd").format(buildDate));
		return this.getSession().load(DataAnalysisBean.class, bean);
	}

	public DataAnalysisBean insert(DataAnalysisBean dataAnalysisBean) {
		DataAnalysisBean bean = new DataAnalysisBean();
		bean.setStockId(dataAnalysisBean.getStockId());
		bean.setBuildDate(dataAnalysisBean.getBuildDate());
		bean = (DataAnalysisBean) this.getSession().get(DataAnalysisBean.class, bean);
		if (bean == null) {
			this.getSession().save(dataAnalysisBean);
			return dataAnalysisBean;
		}
		return null;

	}

	public boolean delete(String stockId, Date buildDate) {
		DataAnalysisBean bean = new DataAnalysisBean();
		bean.setStockId(stockId);
//		bean.setBuildDate(new SimpleDateFormat("yyyy-MM-dd").format(buildDate));
		bean = (DataAnalysisBean) this.getSession().get(DataAnalysisBean.class, bean);
		if (bean != null) {
			this.getSession().delete(bean);
			return true;
		}
		return false;
	}

	public DataAnalysisBean update(String stockId, String buildDate, Double openPrice, Double closingPrice,
			Long turnOverInValue, String changeInPrice, Long tradingVolume, Integer numberOfTransactions,
			Double highestPrice, Double lowestPrice, Double refDividendYield) {

		// 日期之後改在DAO外處理
		DataAnalysisBean bean = new DataAnalysisBean();
		bean.setStockId(stockId);
		bean.setBuildDate(buildDate);

		bean = (DataAnalysisBean) this.getSession().get(DataAnalysisBean.class, bean);
		if (bean != null) {
			bean.setOpenPrice(openPrice);
			bean.setClosingPrice(closingPrice);
			bean.setTurnOverInValue(turnOverInValue);
			bean.setChangeInPrice(changeInPrice);
			bean.setTradingVolume(tradingVolume);
			bean.setNumberOfTransactions(numberOfTransactions);
			bean.setHighestPrice(highestPrice);
			bean.setLowestPrice(lowestPrice);
			bean.setRefDividendYield(refDividendYield);
		}
		return bean;
	}

}
