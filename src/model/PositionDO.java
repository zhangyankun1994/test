package model;

/**
 * 岗位表
 * @author zhangyankun
 * @date 2020.11.21
 */
public class PositionDO {

    public PositionDO() {}

    /**
     * 全部参数构造
     * @param id ID
     * @param name 名称
     * @param sort 排序字段
     */
    public PositionDO(Integer id, String name, Integer sort) {
        this.id = id;
        this.name = name;
        this.sort = sort;
    }

    /**
     * 岗位ID
     */
    private Integer id;

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 排序字段
     * 数值越小越靠前
     */
    private Integer sort;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
