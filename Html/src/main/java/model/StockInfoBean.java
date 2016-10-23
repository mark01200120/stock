package model;

import java.util.Set;

public class StockInfoBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String stockId;
	private String stockName;
	private String groupName;
	private String isinCode;
	
//	private StockCompanyBean stockCompany;
//	public StockCompanyBean getStockCompany() {
//		return stockCompany;
//	}
//	public void setStockCompany(StockCompanyBean stockCompany) {
//		this.stockCompany = stockCompany;
//	}
	
	private Set<DataAnalysisBean> dataAnalysis;
	public Set<DataAnalysisBean> getDataAnalysis() {
		return dataAnalysis;
	}
	public void setDataAnalysis(Set<DataAnalysisBean> dataAnalysis) {
		this.dataAnalysis = dataAnalysis;
	}
	
//	private Set<MembersBean> members;	
//	public Set<MembersBean> getMembers() {
//		return members;
//	}
//	public void setMembers(Set<MembersBean> members) {
//		this.members = members;
//	}
	
	@Override
	public String toString() {
		return "StockInfoBean [stockid=" + stockId + ", stockname=" + stockName + ", groupname=" + groupName
				+ ", isincode=" + isinCode + "]";
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getIsinCode() {
		return isinCode;
	}
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}
}
