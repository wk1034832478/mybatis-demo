package org.mybatis.example;

/**
 * @Author 王凯
 * @Created 2019/7/2 0002-17:27
 */
public class Blog {
    private int id;
    private String name;
    public Blog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Blog() {}

    @Override
    public String toString() {
        return "id:" + this.id + ", name；" + this.name ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
