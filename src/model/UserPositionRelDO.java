package model;

/**
 * 用户与岗位关联表
 * @author zhangyankun
 * @date 2020.11.21
 */
public class UserPositionRelDO {

    public UserPositionRelDO() {
    }

    /**
     * 全参数构造
     * @param id
     * @param userId
     * @param positionId
     */
    public UserPositionRelDO(Integer id, Integer userId, Integer positionId) {
        this.id = id;
        this.userId = userId;
        this.positionId = positionId;
    }

    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 岗位ID
     */
    private Integer positionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }
}
