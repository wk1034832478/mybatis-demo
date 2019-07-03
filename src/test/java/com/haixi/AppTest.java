package com.haixi;

import static org.junit.Assert.assertTrue;

import com.haixi.log.AppLog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
        // 配置文件位置
        String resource = "mybatis-config.xml";
        try {
            // 获取配置文件资源
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
        Blog blog = blogMapper.selectBlogThroughAnnotation(2);
        AppLog.log( blog );
    }

    @Test
    public void test3() {
        sqlSession = this.sqlSessionFactory.openSession();
    }

    @Test
    public void test4() {
        FileSystem fs;
        //方式1通过配置来获取fs
        /*Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://node1:9000");
        FileSystem fs = FileSystem.get(conf);
        System.setProperty("HADOOP_USER_NAME", "root");*/
        //方式2直接获取fs
        Configuration conf = new Configuration();
        conf.set("dfs.replication", "1");
        conf.set("dfs.block.size", "64m");
        try {
            AppLog.log( "正在连接hdfs" );
            fs = FileSystem.get(new URI("hdfs://119.162.211.98:9000"), conf, "wangkai");
            AppLog.log( fs.mkdirs( new Path("/wangkai2")) );
            AppLog.log( "hdfs操作完成" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试调用存储过程， 定义格式如下
     * call query_blog_by_id(#{id,mode=IN,jdbcType=INTEGER},#{name,mode=OUT,jdbcType=VARCHAR})
        */
    @Test
    public void test5() {
        sqlSession = this.sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        String a = "" ;
        Map<String, Object> map = new HashMap<>();
        map.put("id", 2);
        blogMapper.selectByProcedure(map);
        AppLog.log(  blogMapper.selectByProcedure(map) );
        AppLog.log( map );
    }


    @Test
    public void test6() {
        sqlSession = this.sqlSessionFactory.openSession();
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        System.out.println( blogMapper.selectByName("wangkai") );
    }

    @After
    public void testAfter() {
        sqlSession.close();
    }

}
