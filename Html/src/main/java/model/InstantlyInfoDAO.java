package model;

import java.util.List;

public interface InstantlyInfoDAO {

	// 查詢
	public abstract List<InstantlyInfoBean> select();

	public abstract InstantlyInfoBean select(String stockId);

	// 新增
	public abstract InstantlyInfoBean insert(InstantlyInfoBean instantlyInfoBean);

	// 刪除
	public abstract boolean delete(String stockId);

	// 修改
	public abstract InstantlyInfoBean update(String stockId, String stockName, Double finalPrice, Integer temporalVolume,
			Integer volume, String infomationTime, String infomationDate, Double high, Double low, Double openPrice,
			Double limitUpPoint, Double limitDownPoint, Double closingPrice, String a, String f, String b, String g);

}
