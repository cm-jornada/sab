package simple.auth.boot.inner.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mysql.management.MysqldResource;

@Component
public class SabedMySqlServer implements InitializingBean {
	private MysqldResource mysqlInstance;
	private DriverManagerDataSource dataSource;
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;

	/**
	 * 获得当前应用主目录
	 * 
	 * @return 当前应用启动程序所在目录.
	 */
	public static String getPlatformBaseDir() {
		return System.getProperty("user.dir");
	}

	public void startup() {
		mysqlInstance = new MysqldResource();
		Properties props = new Properties();
		try {
			props.load(ClassLoader.getSystemResourceAsStream ("inner.db.properties") );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String port = props.getProperty("port");
		if (StringUtils.isEmpty(port))
			props.put("port", port = String.valueOf((int) (Math.random() * 40000)));
		final Set<Object> keys = props.keySet();
		final Map<String, String> options = new HashMap<String, String>(keys.size());
		for (final Object key : keys) {
			final String val = props.getProperty(key.toString());
			if ("".equals(val))
				options.put(key.toString(), null);
			else
				options.put(key.toString(), val.replace("{$contextPath}", getPlatformBaseDir()));
		}
		if (!mysqlInstance.isRunning())
			mysqlInstance.start("sab_MySQL", options);
	}

	/**
	 * 判断mysql是否正在运行
	 */
	public boolean isRunning() {
		return null == mysqlInstance ? false : mysqlInstance.isRunning();
	}

	public void shutdown() {
		if (mysqlInstance != null)
			mysqlInstance.shutdown();
	}
	
	public DataSource getDataSource(){
		return dataSource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		startup();
		if(isRunning()){
			
			dataSource = new DriverManagerDataSource();
			dataSource.setUrl("jdbc:mysql://127.0.0.1:3336/mysql?useServerPrepStmts=false&useUnicode=true&characterEncoding=UTF-8");
			dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
			dataSource.setUsername("root");
			dataSource.setPassword("");
			
//			Connection conn = null;
//			String driverName = "";
//			try {
//				String url = "";
//				driverName = "org.gjt.mm.mysql.Driver";
//				url = "jdbc:mysql://127.0.0.1:3336/mysql?useServerPrepStmts=false&useUnicode=true&characterEncoding=UTF-8";
//				Class.forName(driverName);
//				conn = DriverManager.getConnection(url, "root", "");
//			} catch (final ClassNotFoundException e) {
//				throw new Exception("没有找到数据库驱动类[" + driverName + "]", e);
//			} catch (final SQLException e) {
//				throw new Exception("数据库连接过程发生错误，请检查配置是否正确。", e);
//			}
//			
//			if(null == conn){
//				throw new RuntimeException("创建数据库连接失败！");
//			}
//
//			System.out.println("mysql is start");
			
//			String USER_SQL="CREATE TABLE `USER` (`ID` varchar(32) NOT NULL,`USER_NAME` varchar(32) NOT NULL,`PASSWORD` varchar(32) NOT NULL,`ENABLE` varchar(32) NULL,`PHONE` varchar(32) NULL,`EMAIL` varchar(32) NULL,`EXTEND_FIELD` text NULL,`CREATE_TIME` datetime NULL,`UPDATE_TIME` datetime NULL,PRIMARY KEY (`ID`))";
//			PreparedStatement localPreparedStatement = conn.prepareStatement(USER_SQL);
//			boolean localResultSet = localPreparedStatement.execute();
//			System.out.println("localResultSet" + localResultSet);
		}
	}
}
