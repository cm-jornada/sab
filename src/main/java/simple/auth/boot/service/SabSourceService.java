package simple.auth.boot.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import simple.auth.boot.dmo.Resource;
import simple.auth.boot.dmo.Role;
import simple.auth.boot.mapper.ResourceMapper;


@Component
public class SabSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ResourceMapper resourceMapper;
    
    @Autowired
	private JdbcTemplate jdbcTemplate;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();

    /*
     * 一定要加上@PostConstruct注解 在Web服务器启动时，提取系统中的所有权限。
     */
    @PostConstruct
    private void loadResourceDefine() {
        this.initData();
        this.loadResource();
    }
    
    public void initData(){
		String USER_SQL="CREATE TABLE if not exists `TB_USER` (`ID` varchar(32) NOT NULL,`USER_NAME` varchar(32) NOT NULL,`PASSWORD` varchar(32) NOT NULL,`ENABLE` varchar(32) NULL,`PHONE` varchar(32) NULL,`EMAIL` varchar(32) NULL,`EXTEND_FIELD` text NULL,`CREATE_TIME` datetime NULL,`UPDATE_TIME` datetime NULL,PRIMARY KEY (`ID`))";
		jdbcTemplate.execute(USER_SQL);
		String RESOURCE_SQL="CREATE TABLE if not exists `TB_RESOURCE` (`ID` varchar(32) NOT NULL,`FATHER_ID` varchar(32) NOT NULL,`CODE` varchar(32) NOT NULL,`TITLE` varchar(32) NULL,`URL` varchar(32) NULL,`ICON` varchar(32) NULL,`STATUS` varchar(2) NULL,`LEVEL` int(11),`CREATE_TIME` datetime NULL,`UPDATE_TIME` datetime NULL,PRIMARY KEY (`ID`))";
		jdbcTemplate.execute(RESOURCE_SQL);
		String ROLE_SQL="CREATE TABLE if not exists `TB_ROLE` (`ID` varchar(32) NOT NULL,`NAME` varchar(32) NOT NULL,`DESCRIPTION` varchar(32) NOT NULL,`STATUS` varchar(2) NULL,`CREATE_TIME` datetime NULL,`UPDATE_TIME` datetime NULL,PRIMARY KEY (`ID`))";
		jdbcTemplate.execute(ROLE_SQL);
		String ROLE_RESOURCE_SQL="CREATE TABLE if not exists `TB_ROLE_RESOURCE` (`ID` varchar(32) NOT NULL,`ROLE_ID` varchar(32) NOT NULL,`RESOURCE_ID` varchar(32) NOT NULL,`STATUS` varchar(2) NULL,`CREATE_TIME` datetime NULL,`UPDATE_TIME` datetime NULL,PRIMARY KEY (`ID`))";
		jdbcTemplate.execute(ROLE_RESOURCE_SQL);
		String USER_ROLE_SQL="CREATE TABLE if not exists `TB_USER_ROLE` (`ID` varchar(32) NOT NULL,`ROLE_ID` varchar(32) NOT NULL,`USER_ID` varchar(32) NOT NULL,`STATUS` varchar(2) NULL,`CREATE_TIME` datetime NULL,`UPDATE_TIME` datetime NULL,PRIMARY KEY (`ID`))";
		jdbcTemplate.execute(USER_ROLE_SQL);
		
		String RES_QUERY_SQL = "select count(1) from `TB_RESOURCE`";
		Long RES_COUNT = jdbcTemplate.queryForObject(RES_QUERY_SQL, Long.class);
		if(null == RES_COUNT || 0 == RES_COUNT){
			String INDERT_SQL = "insert into `TB_RESOURCE`(`ID`,`FATHER_ID`,`CODE`,`TITLE`,`URL`,`ICON`,`LEVEL`,`STATUS`,`CREATE_TIME`,`UPDATE_TIME`) values(('6649220DAE4B46258EF8BA4CB700DA44','6252C9E7D42E44849D5A60DD3A10DF2E','RES_INDEX',null,'/index',null,'0','1','2017-02-24 18:12:27','2017-02-24 18:12:27'),('E2A5CB5A76AA44AF916DAC48397B1224','6252C9E7D42E44849D5A60DD3A10DF2E','RES_APPLICATION',null,'/',null,'0','1','2017-02-24 18:17:15','2017-02-24 18:17:15')";
			jdbcTemplate.execute(INDERT_SQL);
		}
		
		String USER_QUERY_SQL = "select count(1) from `TB_USER`";
		Long USER_COUNT = jdbcTemplate.queryForObject(USER_QUERY_SQL, Long.class);
		if(null == USER_COUNT || 0 == USER_COUNT){
			String INDERT_SQL = "insert into `TB_USER`(`ID`,`USER_NAME`,`PASSWORD`,`ENABLE`,`PHONE`,`EMAIL`,`EXTEND_FIELD`,`CREATE_TIME`,`UPDATE_TIME`) values('16E93D47C7804EEF8EA2397E52DC2AD2', 'test2', '123456', '1', null, null, null, '2017-02-24 11:42:42.093', '2017-02-24 11:42:42.093'),('54655AB8C9544A51ADA3418EB659BE44', 'admin', 'admin', '1', null, null, null, '2017-02-24 11:43:01.277', '2017-02-24 11:43:01.277'),('5F0D2E4A01EB40F1978CE930C82F933B', 'unesd', 'unesd', '1', null, null, null, '2017-02-23 11:35:05.063', '2017-02-23 13:58:00.803')";
			jdbcTemplate.execute(INDERT_SQL);
		}
		
		String ROLE_QUERY_SQL = "select count(1) from `TB_ROLE`";
		Long ROLE_COUNT = jdbcTemplate.queryForObject(ROLE_QUERY_SQL, Long.class);
		if(null == ROLE_COUNT || 0 == ROLE_COUNT){
			String INDERT_SQL = "insert into `TB_ROLE`(`ID`,`NAME`,`DESCRIPTION`,`STATUS`,`CREATE_TIME`,`UPDATE_TIME`) values('4F1707A85184492A8BA3B2D28D53691A', 'ROLE_ADMIN', 'ROLE_ADMIN', '1', '2017-02-24 08:57:24.380', '2017-02-24 08:57:24.380'),('62C85183BF9C48B2B9E78B741A84DAFF', 'ROLE_SUPER', 'ROLE_SUPER', '1', '2017-02-24 08:56:25.273', '2017-02-24 08:56:25.273'),('BB3F535DA6DE450FAC0CA2E91082A666', 'ROLE_USER', 'ROLE_USER', '1', '2017-02-24 08:57:04.363', '2017-02-24 08:57:04.363')";
			jdbcTemplate.execute(INDERT_SQL);
		}
		
		String U_ROLE_QUERY_SQL = "select count(1) from `TB_USER_ROLE`";
		Long U_ROLE_COUNT = jdbcTemplate.queryForObject(U_ROLE_QUERY_SQL, Long.class);
		if(null == U_ROLE_COUNT || 0 == U_ROLE_COUNT){
			String INDERT_SQL = "insert into `TB_USER_ROLE`(`ID`,`USER_ID`,`ROLE_ID`,`STATUS`,`CREATE_TIME`,`UPDATE_TIME`) values('3023101782444DA59849A1666DDAA1A7', '54655AB8C9544A51ADA3418EB659BE44', 'BB3F535DA6DE450FAC0CA2E91082A666', '1', '2017-02-24 11:46:37.480', '2017-02-24 11:46:37.480'),('35F9483E0BBF40278156C0DCDC89B01A', '5F0D2E4A01EB40F1978CE930C82F933B', '62C85183BF9C48B2B9E78B741A84DAFF', '1', '2017-02-24 11:46:01.850', '2017-02-24 11:46:01.850'),('7E98B09D216E42328D8176172DEF34CB', '16E93D47C7804EEF8EA2397E52DC2AD2', 'BB3F535DA6DE450FAC0CA2E91082A666', '1', '2017-02-24 11:48:49.533', '2017-02-24 11:48:49.533'),('8CBAE9CBD81844119A497D528D3D2929', '54655AB8C9544A51ADA3418EB659BE44', '4F1707A85184492A8BA3B2D28D53691A', '1', '2017-02-24 11:48:16.427', '2017-02-24 11:48:16.427')";
			jdbcTemplate.execute(INDERT_SQL);
		}
		
		String R_ROLE_QUERY_SQL = "select count(1) from `TB_ROLE_RESOURCE`";
		Long R_ROLE_COUNT = jdbcTemplate.queryForObject(R_ROLE_QUERY_SQL, Long.class);
		if(null == R_ROLE_COUNT || 0 == R_ROLE_COUNT){
			String INDERT_SQL = "insert into `TB_ROLE_RESOURCE`(`ID`,`ROLE_ID`,`RESOURCE_ID`,`STATUS`,`CREATE_TIME`,`UPDATE_TIME`) values('296C82227BF94BB89B5248FF425358CD', '62C85183BF9C48B2B9E78B741A84DAFF', 'E2A5CB5A76AA44AF916DAC48397B1224', '1', '2017-02-24 18:34:15.933', '2017-02-24 18:34:15.933'),('5BDDC1BAED6C4630AC1417A4F4C8C4B0', 'BB3F535DA6DE450FAC0CA2E91082A666', '6649220DAE4B46258EF8BA4CB700DA44', '1', '2017-02-24 18:19:10.383', '2017-02-24 18:19:10.383'),('AEB2281C707147E5A5A0EB47A693F7DD', 'BB3F535DA6DE450FAC0CA2E91082A666', 'E2A5CB5A76AA44AF916DAC48397B1224', '1', '2017-02-24 18:20:39.247', '2017-02-24 18:20:39.247')";
			jdbcTemplate.execute(INDERT_SQL);
		}
    }
    
    
    public void loadResource(){
        /*
         * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
         */
        List<Resource> resList = resourceMapper.loadAll();
        if (!CollectionUtils.isEmpty(resList)) {
            for (Resource resource : resList) {
                List<ConfigAttribute> attList = new ArrayList<ConfigAttribute>();
                if (!CollectionUtils.isEmpty(resource.getRoleList())) {
                    for (Role role : resource.getRoleList()) {
                        ConfigAttribute ca = new SecurityConfig(role.getName());
                        attList.add(ca);
                    }
                }
                resourceMap.put(resource.getUrl(), attList);
            }
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<ConfigAttribute>();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        if (resourceMap == null) {
            loadResourceDefine();
        }
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);
            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
                return resourceMap.get(resURL);
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
