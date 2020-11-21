package model;

/**
 * 用户表
 * @author zhangyankun
 * @date 2020.11.21
 */
public class UserDO {

    public UserDO() {}

    /**
     * 全部参数构造
     * @param id ID
     * @param name 名称
     */
    public UserDO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
