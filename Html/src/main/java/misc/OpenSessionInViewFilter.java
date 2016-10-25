package misc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebFilter(
		urlPatterns={"/*"},
		initParams={
				@WebInitParam(name="sessionFactoryBeanName", value="sessionFactory")
		}
)
public class OpenSessionInViewFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		try {
			sessionFactory.getCurrentSession().beginTransaction();
			chain.doFilter(request, response);
			sessionFactory.getCurrentSession().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			sessionFactory.getCurrentSession().getTransaction().rollback();
			chain.doFilter(request, response);
		}
	}
	private FilterConfig filterConfig;
	private SessionFactory sessionFactory;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		String sessionFactoryBeanName = filterConfig.getInitParameter("sessionFactoryBeanName");
		if(sessionFactoryBeanName==null || sessionFactoryBeanName.length()==0) {
			sessionFactoryBeanName = "sessionFactory";
		}
		ServletContext application = filterConfig.getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
		this.sessionFactory = (SessionFactory) context.getBean(sessionFactoryBeanName);
	}
	@Override
	public void destroy() {

	}
}
