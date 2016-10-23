package model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


//read me
public class DataAnalysisService {

	private DataAnalysisDAO dataAnalysisDAO;

	public DataAnalysisService(DataAnalysisDAO dataAnalysisDAO) {
		this.dataAnalysisDAO = dataAnalysisDAO;
	} 	
	/*
	 * 搜尋的服務,判斷三種送法,都把他塞進list 去讀取
	 * 
	 */
	private SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<DataAnalysisBean> selectByFilter(String stockId, Date start, Date end) {

		return dataAnalysisDAO.select(stockId, start, end);

	}
	
	public List<DataAnalysisBean> select(DataAnalysisBean bean) throws ParseException {
		List<DataAnalysisBean> result = null;
		if (bean != null && bean.getStockId().trim() != null && bean.getBuildDate()==null) {
			List<DataAnalysisBean> temp = dataAnalysisDAO.select(bean.getStockId());
			if (temp != null) {
				result =temp;
			}
		}
		else if(bean != null && bean.getStockId().trim()!=null && bean.getBuildDate()!=null){
			DataAnalysisBean temp2= dataAnalysisDAO.select(bean.getStockId(),sFormat.parse(bean.getBuildDate()));
				if (temp2 != null) {
					result = new ArrayList<DataAnalysisBean>();
					result.add(temp2);
				}
		}
		else if(bean !=null && bean.getStockId().trim()==null && bean.getBuildDate()!=null){
			List<DataAnalysisBean> temp3 =  dataAnalysisDAO.select(bean.getBuildDate());
				if(temp3 != null)
				{
					result = temp3;
				}
		}
		else {
			result = dataAnalysisDAO.select();
		}
		return result;
	}
	
	
	/*
	 * 爬蟲程式
	 */
	public void update(Date date, HashMap<String, List<String>> groupInfos, List<GroupInfoBean> groupIds)
			throws ParseException {

		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		Integer y = Integer.parseInt(year.format(date)) - 1911;
		String qdate = y + "/" + month.format(date) + "/" + day.format(date);

		String targetURL = "http://www.twse.com.tw/ch/trading/exchange/MI_INDEX/MI_INDEX.php";

		if (groupIds != null) {
			// 以ID組別(產業)數量為單位跑迴圈
			for (int ng = 0; ng < groupIds.size(); ng++) {
				try {
					Document doc = Jsoup.connect(
							targetURL + "?download=&qdate=" + qdate + "&selectType=" + groupIds.get(ng).getGroupId())
							.userAgent("Mozilla").get();
					List<String> stockIds = groupInfos.get(groupIds.get(ng).getGroupName());
					// 以股票ID的數量來跑迴圈
					for (int si = 0; si < stockIds.size(); si++) {
						String stockId = stockIds.get(si);
						for (int i = 0; i < doc.select("tbody").select("tr").size(); i++) {
							if (doc.select("tbody").select("tr").get(i).child(0).text().equals(stockId)) {

								DataAnalysisBean dataAnalysisBean = new DataAnalysisBean();
								dataAnalysisBean.setStockId(stockId);
								dataAnalysisBean.setBuildDate(
										year.format(date) + "/" + month.format(date) + "/" + day.format(date));
								dataAnalysisBean.setTradingVolume(Long.parseLong(
										doc.select("tbody").select("tr").get(i).child(2).text().replaceAll(",", "")));
								dataAnalysisBean.setNumberOfTransactions(Integer.parseInt(
										doc.select("tbody").select("tr").get(i).child(3).text().replaceAll(",", "")));
								dataAnalysisBean.setTurnOverInValue(Long.parseLong(
										doc.select("tbody").select("tr").get(i).child(4).text().replaceAll(",", "")));

								try {
									dataAnalysisBean.setOpenPrice(Double.parseDouble(doc.select("tbody").select("tr")
											.get(i).child(5).text().replaceAll(",", "")));
								} catch (NumberFormatException e1) {
									dataAnalysisBean.setOpenPrice(null);
								}

								try {
									dataAnalysisBean.setHighestPrice(Double.parseDouble(doc.select("tbody").select("tr")
											.get(i).child(6).text().replaceAll(",", "")));
								} catch (NumberFormatException e1) {
									dataAnalysisBean.setHighestPrice(null);
								}
								try {
									dataAnalysisBean.setLowestPrice(Double.parseDouble(doc.select("tbody").select("tr")
											.get(i).child(7).text().replaceAll(",", "")));
								} catch (NumberFormatException e1) {
									dataAnalysisBean.setLowestPrice(null);
								}
								try {
									dataAnalysisBean.setClosingPrice(Double.parseDouble(doc.select("tbody").select("tr")
											.get(i).child(8).text().replaceAll(",", "")));
								} catch (NumberFormatException e1) {
									dataAnalysisBean.setClosingPrice(null);
								}

								if (doc.select("tbody").select("tr").get(i).child(9).text().equals("＋")) {
									dataAnalysisBean.setChangeInPrice(
											"+" + doc.select("tbody").select("tr").get(i).child(10).text());
								} else if (doc.select("tbody").select("tr").get(i).child(9).text().equals("－")) {
									dataAnalysisBean.setChangeInPrice(
											"-" + doc.select("tbody").select("tr").get(i).child(10).text());
								} else {
									dataAnalysisBean.setChangeInPrice("0");
								}
								// 參考值利率(現金股利/昨日收盤價)

								dataAnalysisDAO.update(dataAnalysisBean.getStockId(), dataAnalysisBean.getBuildDate(),
										dataAnalysisBean.getOpenPrice(), dataAnalysisBean.getClosingPrice(),
										dataAnalysisBean.getTurnOverInValue(), dataAnalysisBean.getChangeInPrice(),
										dataAnalysisBean.getTradingVolume(), dataAnalysisBean.getNumberOfTransactions(),
										dataAnalysisBean.getHighestPrice(), dataAnalysisBean.getLowestPrice(),
										dataAnalysisBean.getRefDividendYield());

							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 爬蟲,需要的參數第一個為要搜尋的日期,第二個跑GroupInfoService.getGroupInfos,第三個跑GroupInfoService.select
	public int Crawler(Date date, HashMap<String, List<String>> groupInfos, List<GroupInfoBean> groupIds) {

		int count = 0;
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		Integer y = Integer.parseInt(year.format(date)) - 1911;
		String qdate = y + "/" + month.format(date) + "/" + day.format(date);

		String targetURL = "http://www.twse.com.tw/ch/trading/exchange/MI_INDEX/MI_INDEX.php";

		if (groupIds != null) {
			// 以ID組別(產業)數量為單位跑迴圈
			for (int ng = 0; ng < groupIds.size(); ng++) {
				try {
					Document doc = Jsoup.connect(
							targetURL + "?download=&qdate=" + qdate + "&selectType=" + groupIds.get(ng).getGroupId())
							.userAgent("Mozilla").get();
					List<String> stockIds = groupInfos.get(groupIds.get(ng).getGroupName());
					// 以股票ID的數量來跑迴圈
					for (int si = 0; si < stockIds.size(); si++) {
						String stockId = stockIds.get(si);
						for (int i = 0; i < doc.select("tbody").select("tr").size(); i++) {
							if (doc.select("tbody").select("tr").get(i).child(0).text().equals(stockId)) {

								DataAnalysisBean dataAnalysisBean = new DataAnalysisBean();
								dataAnalysisBean.setStockId(stockId);
								dataAnalysisBean.setBuildDate(
										year.format(date) + "/" + month.format(date) + "/" + day.format(date));
								dataAnalysisBean.setTradingVolume(Long.parseLong(
										doc.select("tbody").select("tr").get(i).child(2).text().replaceAll(",", "")));
								dataAnalysisBean.setNumberOfTransactions(Integer.parseInt(
										doc.select("tbody").select("tr").get(i).child(3).text().replaceAll(",", "")));
								dataAnalysisBean.setTurnOverInValue(Long.parseLong(
										doc.select("tbody").select("tr").get(i).child(4).text().replaceAll(",", "")));

								try {
									dataAnalysisBean.setOpenPrice(Double.parseDouble(doc.select("tbody").select("tr")
											.get(i).child(5).text().replaceAll(",", "")));
								} catch (NumberFormatException e1) {
									dataAnalysisBean.setOpenPrice(null);
								}

								try {
									dataAnalysisBean.setHighestPrice(Double.parseDouble(doc.select("tbody").select("tr")
											.get(i).child(6).text().replaceAll(",", "")));
								} catch (NumberFormatException e1) {
									dataAnalysisBean.setHighestPrice(null);
								}
								try {
									dataAnalysisBean.setLowestPrice(Double.parseDouble(doc.select("tbody").select("tr")
											.get(i).child(7).text().replaceAll(",", "")));
								} catch (NumberFormatException e1) {
									dataAnalysisBean.setLowestPrice(null);
								}
								try {
									dataAnalysisBean.setClosingPrice(Double.parseDouble(doc.select("tbody").select("tr")
											.get(i).child(8).text().replaceAll(",", "")));
								} catch (NumberFormatException e1) {
									dataAnalysisBean.setClosingPrice(null);
								}

								if (doc.select("tbody").select("tr").get(i).child(9).text().equals("＋")) {
									dataAnalysisBean.setChangeInPrice(
											"+" + doc.select("tbody").select("tr").get(i).child(10).text());
								} else if (doc.select("tbody").select("tr").get(i).child(9).text().equals("－")) {
									dataAnalysisBean.setChangeInPrice(
											"-" + doc.select("tbody").select("tr").get(i).child(10).text());
								} else {
									dataAnalysisBean.setChangeInPrice("0");
								}
								dataAnalysisDAO.insert(dataAnalysisBean);
								count++;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return count;
		} else {
			return 0;
		}
	}
}