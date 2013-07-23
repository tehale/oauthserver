/**
 * 
 */
package org.apache.amber.authserver.Dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.amber.authserver.common.OauthRegParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author ankush
 *
 */
public class RegisterAppDao {

	
	private static final Logger logger = Logger
			.getLogger(RegisterAppDao.class);
	@Autowired
	public JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate 
	 *               the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void saveClientApp(OauthRegParam client,String clientId,String clientSecret) throws Exception{
				createTable();
				jdbcTemplate.update("insert into clientapp (appname,appurl,redirecturl,appdiscription,clientId,clientsecret) values(?,?,?,?,?,?)", new Object[] { client.getName(),
				client.getAppUrl(),client.getRedirectUrl(),client.getDescription(),clientId,clientSecret
			});
		}
	

	public void deleteApp(String clientId){
		String deleteSql="delete from clientapp where clientId='"+clientId+"'";
		jdbcTemplate.update(deleteSql);
		
	}
	public String getRedirectUrl(String clientId){
		return jdbcTemplate.queryForObject("select redirecturl form clientapp where clientId = ?",new Object[]{clientId}, String.class);
		
	}
	
	public String getClientSecret(String clientId){
		String sql="select clientsecret from clientapp where clientId = ?";
		return jdbcTemplate.queryForObject(sql,new Object[]{clientId},String.class);
		
	}
	@SuppressWarnings("unchecked")
	public List<String> getAllClientIds(){
		String sql="select clientId from clientapp";
		return (List<String>)jdbcTemplate.queryForObject(sql, List.class);
		
	}
	@PostConstruct
	public void createTable(){
		String SQL_CREATE = "CREATE TABLE IF NOT EXISTS clientapp (\n"
	            + "appname varchar(100) NOT NULL UNIQUE,\n appurl varchar(100) NOT NULL,\n"
				+"appdiscription varchar(100) ,redirecturl varchar(100) NOT NULL,\n"
	            +"clientId varchar(100) NOT NULL UNIQUE,\n clientsecret varchar(100) NOT NULL UNIQUE,"
	            + "PRIMARY KEY (clientId,clientsecret)\n);";
		jdbcTemplate.update(SQL_CREATE);
		
	}
	
    
	
	
	
}
