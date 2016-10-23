package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Transient;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataAnalysisBean implements Serializable {
	private static final long serialVersionUID = 2118822944676880992L;
	@Transient
	private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy/MM/dd");

	public static void main(String[] args) {

		ApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("beans.config.xml");

			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			sessionFactory.getCurrentSession().beginTransaction();

			Session session = sessionFactory.getCurrentSession();

			DataAnalysisBean bean = new DataAnalysisBean();

			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();

			// select test
			bean.setStockId("1");
			bean.setBuildDate(sFormat.format(date));
			System.out.println(session.load(DataAnalysisBean.class, bean));

			// insert
			// bean.setStockId("3");
			// bean.setBuildDate(sFormat.format(date));
			// bean.setClosingPrice(3.0);
			//
			// session.save(bean);

			// update

			// bean.setStockId("3");
			// bean.setBuildDate("2016/10/05");
			// DataAnalysisBean update = session.load(DataAnalysisBean.class,
			// bean);
			// update.setClosingPrice(4.0);
			// update.setOpenPrice(4.0);

			// delete
			// bean.setStockId("3");
			// bean.setBuildDate(sFormat.format(date));
			// session.delete(bean);

			sessionFactory.getCurrentSession().beginTransaction().commit();
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			((ConfigurableApplicationContext) context).close();

		}

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		System.out.println(sqlDate.getTime());

	}

	private String stockId;// pk
	private String buildDate;// pk
	private Double openPrice;
	private Double closingPrice;
	private Long turnOverInValue;
	private String changeInPrice;
	private Long tradingVolume;
	private Integer numberOfTransactions;
	private Double highestPrice;
	private Double lowestPrice;
	private Double refDividendYield;

	@Override
	public String toString() {
		return "DataAnalysisBean [stockId=" + stockId + ", buildDate=" + buildDate + ", openPrice=" + openPrice
				+ ", closingPrice=" + closingPrice + ", turnOverInValue=" + turnOverInValue + ", changeInPrice="
				+ changeInPrice + ", tradingVolume=" + tradingVolume + ", numberOfTransactions=" + numberOfTransactions
				+ ", highestPrice=" + highestPrice + ", lowestPrice=" + lowestPrice + ", refDividendYield="
				+ refDividendYield + "]";
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(Double closingPrice) {
		this.closingPrice = closingPrice;
	}

	public Long getTurnOverInValue() {
		return turnOverInValue;
	}

	public void setTurnOverInValue(Long turnOverInValue) {
		this.turnOverInValue = turnOverInValue;
	}

	public String getChangeInPrice() {
		return changeInPrice;
	}

	public void setChangeInPrice(String changeInPrice) {
		this.changeInPrice = changeInPrice;
	}

	public Long getTradingVolume() {
		return tradingVolume;
	}

	public void setTradingVolume(Long tradingVolume) {
		this.tradingVolume = tradingVolume;
	}

	public Integer getNumberOfTransactions() {
		return numberOfTransactions;
	}

	public void setNumberOfTransactions(Integer numberOfTransactions) {
		this.numberOfTransactions = numberOfTransactions;
	}

	public Double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(Double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public Double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public Double getRefDividendYield() {
		return refDividendYield;
	}

	public void setRefDividendYield(Double refDividendYield) {
		this.refDividendYield = refDividendYield;
	}
}
