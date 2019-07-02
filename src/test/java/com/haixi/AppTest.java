package com.haixi;

import static org.junit.Assert.assertTrue;

import com.haixi.log.AppLog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.example.Blog;
import org.mybatis.example.BlogMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    private SqlSessionFactory sqlSessionFactory;
    /**
     * 读取xml配置文件
     */
    @Before
    public void testBefore() {
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            AppLog.log("数据库连接完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
   public void test() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlog(1);
        System.out.println( blog );
   }

    @After
    public void testAfter() {

    }

}
