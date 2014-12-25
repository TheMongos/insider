package db.mysql;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
public class DBConn{

	@Bean(destroyMethod = "close")
	public DataSource datasource() {
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:mysql://localhost:3306/insider");
		p.setDriverClassName("com.mysql.jdbc.Driver");
		p.setUsername("root");
		p.setPassword("");
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors(
				"org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
				"org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		ds.setPoolProperties(p);
		return (DataSource)ds;
	}

	@Bean public JdbcOperations tpl() {
		return new JdbcTemplate(datasource());
	}

//	static DataSource datasource;
//
//	static
//	{
//		PoolProperties p = new PoolProperties();
//		p.setUrl("jdbc:mysql://localhost:3306/mysql");
//		p.setDriverClassName("com.mysql.jdbc.Driver");
//		p.setUsername("root");
//		p.setPassword("");
//		p.setJmxEnabled(true);
//		p.setTestWhileIdle(false);
//		p.setTestOnBorrow(true);
//		p.setValidationQuery("SELECT 1");
//		p.setTestOnReturn(false);
//		p.setValidationInterval(30000);
//		p.setTimeBetweenEvictionRunsMillis(30000);
//		p.setMaxActive(100);
//		p.setInitialSize(10);
//		p.setMaxWait(10000);
//		p.setRemoveAbandonedTimeout(60);
//		p.setMinEvictableIdleTimeMillis(30000);
//		p.setMinIdle(10);
//		p.setLogAbandoned(true);
//		p.setRemoveAbandoned(true);
//		p.setJdbcInterceptors(
//				"org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
//				"org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
//		datasource = new DataSource();
//		datasource.setPoolProperties(p);
//
//		//import java.sql.ResultSet;
//		//import java.sql.Statement;
//
//		/*Connection con = null;
//			try {
//				con = datasource.getConnection();
//				Statement st = con.createStatement();
//				ResultSet rs = st.executeQuery("select * from user");
//				int cnt = 1;
//				while (rs.next()) {
//					System.out.println((cnt++)+". Host:" +rs.getString("Host")+
//							" User:"+rs.getString("User")+" Password:"+rs.getString("Password"));
//				}
//				rs.close();
//				st.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				if (con!=null) try {con.close();}catch (Exception ignore) {}
//			}*/
//	}
//
//	public static Connection getConnection() throws SQLException{
//		return datasource.getConnection();
//	}
}
