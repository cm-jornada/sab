//package simple.auth.boot.inner.db;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SabMySqlInit implements InitializingBean {
//
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		String USER_SQL="CREATE TABLE `USER` (`ID` varchar(32) NOT NULL,`USER_NAME` varchar(32) NOT NULL,`PASSWORD` varchar(32) NOT NULL,`ENABLE` varchar(32) NULL,`PHONE` varchar(32) NULL,`EMAIL` varchar(32) NULL,`EXTEND_FIELD` text NULL,`CREATE_TIME` datetime NULL,`UPDATE_TIME` datetime NULL,PRIMARY KEY (`ID`))";
//		jdbcTemplate.execute(USER_SQL);
//		System.out.println("SabMySqlInit");
//	}
//
//}
