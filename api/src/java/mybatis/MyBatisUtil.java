/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybatis;
/**
 *
 * @author a-rac
 */
import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class MyBatisUtil { 
  private static SqlSession session = null;
public static SqlSession getSession(){
    try{
        Reader reader=  Resources.getResourceAsReader("xml/mybatis/mybatis-config.xml");
        SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader); 
        session =sqlMapper.openSession();
    }catch(IOException ex){
    }
    return session;
}
}

