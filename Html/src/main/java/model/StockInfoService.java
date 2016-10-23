package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StockInfoService {
	private StockInfoDAO stockInfoDao = null;

	public StockInfoService(StockInfoDAO stockInfoDao) {
		this.stockInfoDao = stockInfoDao;
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.config.xml");
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		try {
			sessionFactory.getCurrentSession().beginTransaction();

			StockInfoService service = (StockInfoService) context.getBean("stockInfoService");
//			StockInfoBean bean = new StockInfoBean();
//			bean.setStockId("1101");
			// bean.setStockName("台泥");
//			bean.setGroupName("水泥工業");
			// bean.setIsinCode("TW0001101004");
			// service.insert(bean);
			// service.delete(bean);
			// service.update(bean);
			service.refreshStockInfo();
//			System.out.println(service.select_All_Group());
//			System.out.println(service.select_All_Id().size());
//			System.out.println(service.select(bean));

			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (Exception e) {
			sessionFactory.getCurrentSession().getTransaction().rollback();
		}
	}

	public void refreshStockInfo() throws Exception {
		Document doc = Jsoup.connect("http://isin.twse.com.tw/isin/C_public.jsp?strMode=2").get();
		for (int i = 0; i < doc.select("tr").size(); i++) {
			if (doc.select("tr").get(i).childNodeSize() > 6) {
				if ("ESVUFR".equals(doc.select("tr").get(i).child(5).text())) {
					StockInfoBean bean = new StockInfoBean();
					bean.setStockId(doc.select("tr").get(i).child(0).text().substring(0, 4).trim());
					bean.setStockName(doc.select("tr").get(i).child(0).text().substring(5).replaceAll("　", ""));
					bean.setGroupName(doc.select("tr").get(i).child(4).text());
					bean.setIsinCode(doc.select("tr").get(i).child(1).text());
					this.insert(bean);
				}
			}
		}
	}

	public List<StockInfoBean> select(StockInfoBean bean) {
		List<StockInfoBean> result = null;
		if (bean != null && bean.getStockId() != null) {
			StockInfoBean temp = stockInfoDao.select(bean.getStockId());
			if (temp != null) {
				result = new ArrayList<StockInfoBean>();
				result.add(temp);
			}
		} else {
			result = stockInfoDao.select();
		}
		return result;
	}
	
	public List<String> select_All_Group() {
		List<String> result = stockInfoDao.select_All_Group();		
		return result;
	}
	
	public List<String> select_All_Id() {
		List<String> result = stockInfoDao.select_All_Id();		
		return result;
	}

	public StockInfoBean insert(StockInfoBean bean) {
		StockInfoBean result = null;
		if (bean != null) {
			result = stockInfoDao.insert(bean);
		}
		return result;
	}

	public StockInfoBean update(StockInfoBean bean) {
		StockInfoBean result = null;
		if (bean != null) {
			result = stockInfoDao.update(bean.getStockName(), bean.getGroupName(), bean.getIsinCode(),
					bean.getStockId());
		}
		return result;
	}

	public boolean delete(StockInfoBean bean) {
		boolean result = false;
		if (bean != null) {
			result = stockInfoDao.delete(bean.getStockId());
		}
		return result;
	}
}