package model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InstantlyInfoService {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws HttpException, IOException {

		ApplicationContext context = null;

		try {
			context = new ClassPathXmlApplicationContext("beans.config.xml");
			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			sessionFactory.getCurrentSession().beginTransaction();

			InstantlyInfoService instantlyInfoService = (InstantlyInfoService) context.getBean("instantlyInfoService");

			StockInfoService stockInfoService = (StockInfoService) context.getBean("stockInfoService");

			// System.out.println(Double.parseDouble((String)"5.34"));

			instantlyInfoService.insert(stockInfoService.select_All_Id());

			sessionFactory.getCurrentSession().getTransaction().commit();
		} finally {
			((ConfigurableApplicationContext) context).close();
		}

	}

	public void insertAll(List<String> stockids) {

	}

	public void insert(List<String> stockids) {

		for (int i = 0; i < stockids.size(); i++) {

			String stockId = stockids.get(i);

			// String stockId = "2478";

			HttpClient client = null;
			HttpMethod method = null;
			try {
				String twse = "http://mis.twse.com.tw/stock/fibest.jsp?stock=1101&lang=zh-TW";
				client = new HttpClient();
				method = new GetMethod(twse);
				client.executeMethod(method);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				String urls = "http://mis.twse.com.tw/stock/api/getStockInfo.jsp?json=1&delay=0&ex_ch=tse_";
				Date date = new Date();
				String str = urls + stockId + ".tw&_=" + date.getTime();
				method = new GetMethod(str);

				method.getParams().setContentCharset("utf-8");
				client.executeMethod(method);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String jsonString = null;
			try {
				jsonString = method.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}

			JSONObject msgArray = null;
			try {
				msgArray = (JSONObject) new JSONObject(jsonString).getJSONArray("msgArray").get(0);
			} catch (JSONException e1) {
			}

			if (msgArray != null) {

				InstantlyInfoBean instantlyInfoBean = new InstantlyInfoBean();
				instantlyInfoBean.setStockId(stockId);
				instantlyInfoBean.setStockName((String) msgArray.get("n"));

				// Double finalPrice;最近成交價
				try {
					instantlyInfoBean.setFinalPrice(Double.parseDouble((String) msgArray.get("z")));
					System.out.println(stockId);
				} catch (Exception e) {
					instantlyInfoBean.setFinalPrice(null);
				}
				try {
					instantlyInfoBean.setTemporalVolume(Integer.parseInt((String) msgArray.get("tv")));
				} catch (Exception e) {
					instantlyInfoBean.setTemporalVolume(null);
				}
				try {
					instantlyInfoBean.setVolume(Integer.parseInt((String) msgArray.get("v")));
				} catch (Exception e) {
					instantlyInfoBean.setVolume(null);
				}
				Date fdate = new Date();
				try {
					fdate.setTime(Long.parseLong((String) msgArray.get("tlong")));
				} catch (Exception e) {
					fdate.setTime(0);
				}
				try {
					instantlyInfoBean.setInfomationTime(new SimpleDateFormat("HH:mm:ss").format(fdate));
				} catch (Exception e) {
					instantlyInfoBean.setInfomationTime(null);
				}
				try {
					instantlyInfoBean.setInfomationDate(new

					SimpleDateFormat("yyyy-MM-dd").format(fdate));
				} catch (Exception e) {
					instantlyInfoBean.setInfomationDate(null);
				}
				try {
					instantlyInfoBean.setHigh(Double.parseDouble((String) msgArray.get("h")));
				} catch (Exception e) {
					instantlyInfoBean.setHigh(null);
				}
				try {
					instantlyInfoBean.setLow(Double.parseDouble((String) msgArray.get("l")));
				} catch (Exception e) {
					instantlyInfoBean.setLow(null);
				}

				try {
					instantlyInfoBean.setOpenPrice(Double.parseDouble((String) msgArray.get("o")));
				} catch (Exception e) {
					instantlyInfoBean.setOpenPrice(null);
				}
				try {
					instantlyInfoBean.setLimitUpPoint(Double.parseDouble((String) msgArray.get("u")));
				} catch (Exception e) {
					instantlyInfoBean.setLimitUpPoint(null);
				}
				try {
					instantlyInfoBean.setLimitDownPoint(Double.parseDouble((String) msgArray.get("w")));
				} catch (Exception e) {
					instantlyInfoBean.setLimitDownPoint(null);
				}
				try {
					instantlyInfoBean.setClosingPrice(Double.parseDouble((String) msgArray.get("y")));
				} catch (Exception e) {
					instantlyInfoBean.setClosingPrice(null);
				}
				instantlyInfoBean.setA((String) msgArray.get("a"));
				instantlyInfoBean.setF((String) msgArray.get("f"));
				instantlyInfoBean.setB((String) msgArray.get("b"));
				instantlyInfoBean.setG((String) msgArray.get("g"));

				instantlyInfoDAO.insert(instantlyInfoBean);

				// System.out.println(instantlyInfoBean);
			}
		}
	}

	private InstantlyInfoDAO instantlyInfoDAO;

	public InstantlyInfoDAO getInstantlyInfoDAO() {
		return instantlyInfoDAO;
	}

	public InstantlyInfoService(InstantlyInfoDAO instantlyInfoDAO) {
		this.instantlyInfoDAO = instantlyInfoDAO;
	}

}
