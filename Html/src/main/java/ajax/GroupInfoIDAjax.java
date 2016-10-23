package ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.json.Json;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.context.ApplicationContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

import model.DataAnalysisBean;
import model.DataAnalysisService;
import model.GroupInfoService;
import model.StockInfoService;



//資料庫json網址
@WebServlet(
		urlPatterns={"/gropuId.view"}
)

public class GroupInfoIDAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GroupInfoService groupInfoService ;
	private StockInfoService stockInfoService;
	//init取得dataAnalysisService 在servet關掉時traction
	public void init() throws ServletException {
		ApplicationContext context = 
				WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		groupInfoService = (GroupInfoService) context.getBean("groupInfoService");
		stockInfoService = (StockInfoService)context.getBean("stockInfoService");
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			this.writeJsonOutput(request,response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void writeJsonOutput(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException{
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//要印出頁面 
		HashMap<String, List<String>> groups = groupInfoService.getGroupInfos(stockInfoService.select(null));
		
		//製作jsonarray
		JsonArrayBuilder builder = Json.createArrayBuilder();
//		Iterator<String> iter= groups.values().stream().findFirst().get().iterator();
		
		//慢慢撈出來
		Iterator<String> industryCategory = groups.keySet().iterator();
	
		while(industryCategory.hasNext()){
			String str =industryCategory.next();//產業類別
			//groups.get(str)比對產業類別,用stream把他撈出來
			groups.get(str).stream().forEach(s->builder.add(Json.createObjectBuilder()
					.add(str,s)	//把資料塞到json格是裡面 key value
					.build()));
			}
		
		//印出來
		out.write(builder.build().toString());
		out.close();
		return;
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
}
