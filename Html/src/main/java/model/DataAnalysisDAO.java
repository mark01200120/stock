package model;

import java.util.Date;
import java.util.List;

public interface DataAnalysisDAO {

	// 查詢
	public abstract List<DataAnalysisBean> select();

	public abstract List<DataAnalysisBean> select(String stockId);

	public abstract List<DataAnalysisBean> select(java.util.Date buildDate);

	public abstract DataAnalysisBean select(String stockId, java.util.Date buildDate);
	
	public abstract List<DataAnalysisBean> select(String stockId, Date start, Date end);

	// 新增
	public abstract DataAnalysisBean insert(DataAnalysisBean dataAnalysisBean);

	// 刪除
	public abstract boolean delete(String stockId, java.util.Date buildDate);

	// 修改
	public abstract DataAnalysisBean update(String stockId, String buildDate, Double openPrice, Double closingPrice,
			Long turnOverInValue, String changeInPrice, Long tradingVolume, Integer numberOfTransactions,
			Double highestPrice, Double lowestPrice, Double refDividendYield);

}
