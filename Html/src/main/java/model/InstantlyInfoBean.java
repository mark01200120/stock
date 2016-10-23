package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Entity
@Table(name = "INSTANTLYINFO")
public class InstantlyInfoBean {
	@Id
	private String stockId;
	private String stockName;
	private Double finalPrice;
	private Integer temporalVolume;
	private Integer volume;
	private String infomationTime;
	private String infomationDate;
	private Double high;
	private Double low;
	private Double openPrice;
	private Double limitUpPoint;
	private Double limitDownPoint;
	private Double closingPrice;
	private String a;
	private String f;
	private String b;
	private String g;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ApplicationContext context = null;
		try {
			context = new ClassPathXmlApplicationContext("beans.config.xml");

			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			sessionFactory.getCurrentSession().beginTransaction();

			Session session = sessionFactory.getCurrentSession();

			// 輸入time的方法
			InstantlyInfoBean up = session.load(InstantlyInfoBean.class, "1");
			Date date = new Date();
			up.setInfomationTime(new SimpleDateFormat("HH:mm:ss").format(date));
			System.out.println(up);

			sessionFactory.getCurrentSession().beginTransaction().commit();
		} finally {
			((ConfigurableApplicationContext) context).close();
		}
	}

	@Override
	public String toString() {
		return "InstantlyInfoBean [stockId=" + stockId + ", stockName=" + stockName + ", finalPrice=" + finalPrice
				+ ", temporalVolume=" + temporalVolume + ", volume=" + volume + ", infomationTime=" + infomationTime
				+ ", infomationDate=" + infomationDate + ", high=" + high + ", low=" + low + ", openPrice=" + openPrice
				+ ", limitUpPoint=" + limitUpPoint + ", limitDownPoint=" + limitDownPoint + ", closingPrice="
				+ closingPrice + ", a=" + a + ", f=" + f + ", b=" + b + ", g=" + g + "]";
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Integer getTemporalVolume() {
		return temporalVolume;
	}

	public void setTemporalVolume(Integer temporalVolume) {
		this.temporalVolume = temporalVolume;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public String getInfomationTime() {
		return infomationTime;
	}

	public void setInfomationTime(String infomationTime) {
		this.infomationTime = infomationTime;
	}

	public String getInfomationDate() {
		return infomationDate;
	}

	public void setInfomationDate(String infomationDate) {
		this.infomationDate = infomationDate;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getLimitUpPoint() {
		return limitUpPoint;
	}

	public void setLimitUpPoint(Double limitUpPoint) {
		this.limitUpPoint = limitUpPoint;
	}

	public Double getLimitDownPoint() {
		return limitDownPoint;
	}

	public void setLimitDownPoint(Double limitDownPoint) {
		this.limitDownPoint = limitDownPoint;
	}

	public Double getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(Double closingPrice) {
		this.closingPrice = closingPrice;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	
	
}
