package org.mybatis.example;

/**
 * @Author 王凯
 * @Created 2019/7/2 0002-17:28
 */
public interface BlogMapper {

    public Blog selectBlog(int id);

    @
    public Blog selectBlogThroughAnnotation(int id);
}
