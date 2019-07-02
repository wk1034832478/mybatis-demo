package org.mybatis.example;

import org.apache.ibatis.annotations.Select;

/**
 * @Author 王凯
 * @Created 2019/7/2 0002-17:28
 */
public interface BlogMapper {

    Blog selectBlog(int id);

    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlogThroughAnnotation(int id);
}
