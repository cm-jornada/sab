/*
 * FileName: SecurityConfig.java
 * Author:   jornada
 * Date:     2017年1月12日 下午5:31:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package simple.auth.boot.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 〈security config〉
 *
 * @author jornada
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource(name = "sabUserDetailsService")
	UserDetailsService userDetailsService;

	@Resource(name = "sabSecurityInterceptor")
	FilterSecurityInterceptor securityInterceptor;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	/**
	 * base config
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class)// 在正确的位置添加我们自定义的过滤器
				.authorizeRequests()

				.antMatchers("/reloadResource").permitAll() // 刷新资源权限
				.antMatchers("/**.*").authenticated()// .后缀认证

				.and().formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("username")
				.passwordParameter("password").defaultSuccessUrl("/loginSuccess").failureUrl("/loginFail").and()
				.logout().logoutSuccessUrl("/login").logoutUrl("/logout")// login
				.and().exceptionHandling().accessDeniedPage("/403")//
				.and().csrf().disable() // disable csrf
				.rememberMe().tokenRepository(new InMemoryTokenRepositoryImpl()).tokenValiditySeconds(86400);// remember
																												// me

	}

	/**
	 * fliter ignoring ,such as static
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
}
