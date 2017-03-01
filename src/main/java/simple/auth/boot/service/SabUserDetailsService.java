/*
 * FileName: sabUserDetailsService.java
 * Author:   jornada
 * Date:     2017年1月13日 下午4:47:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package simple.auth.boot.service;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import simple.auth.boot.dmo.Role;
import simple.auth.boot.mapper.UserMapper;


/**
 * 〈一句话功能简述〉<br>
 * 〈user impl〉
 *
 * @author jornada
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("sabUserDetailsService")
public class SabUserDetailsService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(SabUserDetailsService.class);

    @Autowired
    private UserMapper userMapper;


    /**
     * override query user by user name
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            logger.error("username is empty");
            throw new UsernameNotFoundException("username is empty");
        }
        // query from cache
        // UserDetails user = userCache.get(username);
        simple.auth.boot.dmo.User user = userMapper.findByUserName(username);
        if (null == user) {
            logger.warn("get user is null, userName:{}", username);
            // return empty user
            return new User(username, "", new HashSet<GrantedAuthority>());
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if (!CollectionUtils.isEmpty(user.getRoleList())) {
            for (Role role : user.getRoleList()) {
                GrantedAuthority auth = new SimpleGrantedAuthority(role.getName());
                authorities.add(auth);
            }
        }
        // return user bean
        return new User(user.getUserName(), user.getPassword(), authorities);
    }

    /**
     * get current user info
     */
    public static UserDetails queryCurrentUser(HttpServletRequest request) {
        UserDetails user = null;
        HttpSession httpSession = request.getSession();
        // get user from SecurityContext
        SecurityContext ctx = (SecurityContext) httpSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (ctx != null) {
            Authentication auth = ctx.getAuthentication();
            user = (UserDetails) auth.getPrincipal();
        }
        return user;
    }
}
