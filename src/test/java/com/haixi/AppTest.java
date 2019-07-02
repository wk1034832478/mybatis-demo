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
     * sqlSessuion是非线程安全的，这里的变量仅仅是为了简化测试代码量
     * 真实环境中sqlsession应当放入到局部方法内
     */
    private SqlSession sqlSession;
    /**
     * 读取xml配置文件
     */
    @Before
    public void testBefore() {
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 一旦创建了 SqlSessionFactory，就不再需要它了， 需要保持在局部方法更可靠
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            AppLog.log("数据库连接完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
   public void test() {
        sqlSession = this.sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlog(1);
        AppLog.log( blog );
   }

    @Test
    public void test2() {
        sqlSession = this.sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = blogMapper.selectBlogThroughAnnotation(1);
        AppLog.log( blog );
    }

    @Test
    public void test3() {
        sqlSession = this.sqlSessionFactory.openSession();
    }

    @After
    public void testAfter() {
        sqlSession.close();
    }

}
