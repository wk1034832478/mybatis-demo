package org.mybatis.example;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author 王凯
 * @Created 2019/7/2 0002-17:28
 */
public interface BlogMapper {

    Blog selectBlog(int id);

    @Select("SELECT * FROM wangkai.blog WHERE id = #{id}")
    Blog selectBlogThroughAnnotation(int id);

    Integer selectByProcedure(Map<String, Object> map);

    List<Blog> selectByName(@Param("name") String name);
}
