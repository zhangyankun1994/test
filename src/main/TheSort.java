package main;

import dto.UserPositionSortDTO;
import model.PositionDO;
import model.UserDO;
import model.UserPositionRelDO;

import java.util.*;

/**
 * @author zhangyankun
 * @date 2020.11.21
 *
 * 简述:
 * 由于本地电脑没有完整的开发环境,所以
 * 采用成员变量模仿数据库数据的方式,将数据放置在本类中,
 * 通过改变数据即可改变测试的结果
 *
 * 根据业务设计了三张表,用户表,用户岗位关联表,岗位表
 * 因为考虑到岗位可能是很多个, 并且可以根据岗位进行筛选
 * 等原因, 所以将岗位单独设计成为一张表, 并且有一个
 * sort排序字段, 就可以根据这个sort字段完成当前业务
 * 表结构参考DO对象
 *
 * 代码分为三部分,第一个部分主要将用户和岗位数据关联起来
 * 组成个DTO, 这时如果一个用户有多个岗位的话,数据也是多条
 * 并没有进行合并, 第二步排序, 第三步将相同用户的岗位合并
 *
 * 最后将最终DTO结果输入, 展示时候根据情况取出数据
 *
 * 通过main方法代替了测试案例, 缺点就是每次都需要修改测试数据
 *
 */
public class TheSort {

    /**
     * 初始化数据
     * 模仿数据库表中的数据
     */
    /**
     * 用户表
     */
    private static final List<UserDO> USER_DO_LIST = new ArrayList<>();
    /**
     * 关联表
     */
    private static final List<UserPositionRelDO> USER_POSITION_DO_LIST = new ArrayList<>();
    /**
     * 岗位表
     */
    private static final List<PositionDO> POSITION_DO_LIST = new ArrayList<>();

    static {
        // 用户表
        // 马HT的职位是董事长,总经理
        USER_DO_LIST.add(new UserDO(1,"马HT"));
        // 监事
        USER_DO_LIST.add(new UserDO(2,"周ZY"));
        // 监事
        USER_DO_LIST.add(new UserDO(3,"黄QH"));
        // 副董事长
        USER_DO_LIST.add(new UserDO(4,"卢S"));
        // 执行董事
        USER_DO_LIST.add(new UserDO(5,"奚D"));
        // 其他职位
        USER_DO_LIST.add(new UserDO(6,"刘H"));
        // 无职位
        USER_DO_LIST.add(new UserDO(7,"无ZW"));

        // 关联表
        USER_POSITION_DO_LIST.add(new UserPositionRelDO(1,1,1));
        USER_POSITION_DO_LIST.add(new UserPositionRelDO(2,1,5));
        USER_POSITION_DO_LIST.add(new UserPositionRelDO(3,2,4));
        USER_POSITION_DO_LIST.add(new UserPositionRelDO(4,3,4));
        USER_POSITION_DO_LIST.add(new UserPositionRelDO(5,4,2));
        USER_POSITION_DO_LIST.add(new UserPositionRelDO(6,5,3));
        USER_POSITION_DO_LIST.add(new UserPositionRelDO(7,6,6));

        // 岗位表
        POSITION_DO_LIST.add(new PositionDO(1,"董事长",1));
        POSITION_DO_LIST.add(new PositionDO(2,"副董事长",2));
        POSITION_DO_LIST.add(new PositionDO(3,"执行董事",3));
        POSITION_DO_LIST.add(new PositionDO(4,"监事",4));
        POSITION_DO_LIST.add(new PositionDO(5,"总经理",5));
        POSITION_DO_LIST.add(new PositionDO(6,"其他职位",6));
    }

    /**
     * 通过main方法代替测试用例
     * 执行main方法即可获取打印的数据
     * @param args
     */
    public static void main(String[] args) {
        theSort();
    }

    /**
     * 核心处理方法
     * 处理整个查询及排序流程
     */
    public static void theSort(){
        // 获取用户和职位一一对应的DTO数据
        List<UserPositionSortDTO> userPositionSortDTOS = formatData();

        // 根据sort字段排序
        sort(userPositionSortDTOS);

        // 整理数据,将同一用户的多个职位数据合到一起
        Collection<List<UserPositionSortDTO>> transformData
                = transform(userPositionSortDTOS);

        // 处理完毕 输出数据
        System.out.println(transformData);
    }

    /**
     * 将同一用户的多个岗位放入一个集合
     * 本方法返回多个集合
     * @param userPositionSortDTOS
     * @return
     */
    private static Collection<List<UserPositionSortDTO>> transform(List<UserPositionSortDTO> userPositionSortDTOS){
        Map<Integer, List<UserPositionSortDTO>> map = new LinkedHashMap<>();
        for (UserPositionSortDTO userPositionSortDTO : userPositionSortDTOS) {
            map.computeIfAbsent(userPositionSortDTO.getUserId(),
                    k -> new ArrayList<>()).add(userPositionSortDTO);
        }
        Collection<List<UserPositionSortDTO>> values = map.values();
        return values;
    }

    /**
     * 排序方法
     * sort字段值越小越排在前面
     * sort值为-1时候排在最后
     * @param userPositionSortDTOS
     */
    private static void sort(List<UserPositionSortDTO> userPositionSortDTOS){
        userPositionSortDTOS.sort(new Comparator<UserPositionSortDTO>() {
            @Override
            public int compare(UserPositionSortDTO o1, UserPositionSortDTO o2) {
                if (o1.getSort() < 0) {
                    return 1;
                }
                if (o2.getSort() < 0) {
                    return -1;
                }
                return o1.getSort() - o2.getSort();
            }
        });
    }

    /**
     * 本方法模拟数据库查询数据
     * 通过多层for循环比较出当前用户
     * 对应的多个岗位
     * @return 包含用户和对应岗位的DTO
     */
    private static List<UserPositionSortDTO> formatData(){
        List<UserPositionSortDTO> userPositionSortDTOS = new ArrayList<>();
        for (UserDO userDO : USER_DO_LIST) {
            int beginSize = userPositionSortDTOS.size();
            Integer userId = userDO.getId();
            for (UserPositionRelDO userPositionRelDO : USER_POSITION_DO_LIST) {
                Integer relUserId = userPositionRelDO.getUserId();
                // 用户表与关系表比较
                if (userId.equals(relUserId)) {
                    Integer relPositionId = userPositionRelDO.getPositionId();
                    for (PositionDO positionDO : POSITION_DO_LIST) {
                        Integer positionId = positionDO.getId();
                        // 关系表与岗位表比较
                        if (relPositionId.equals(positionId)) {
                            // 当前用户匹配到的岗位
                            UserPositionSortDTO userPositionSortDTO = new UserPositionSortDTO();
                            userPositionSortDTO.setUserId(userId);
                            userPositionSortDTO.setUserName(userDO.getName());
                            userPositionSortDTO.setPositionId(positionId);
                            userPositionSortDTO.setPositionName(positionDO.getName());
                            userPositionSortDTO.setSort(positionDO.getSort());
                            userPositionSortDTOS.add(userPositionSortDTO);
                        }
                    }
                }
            }
            int endSize = userPositionSortDTOS.size();
            // 通过对起始集合长度的比较, 判断本次是否新增了数据
            // 如果没有新增,则说明本用户没有岗位, 则添加一个
            // 岗位为空的数据
            if (beginSize == endSize) {
                UserPositionSortDTO userPositionSortDTO = new UserPositionSortDTO();
                userPositionSortDTO.setUserId(userId);
                userPositionSortDTO.setUserName(userDO.getName());
                userPositionSortDTO.setSort(-1);
                userPositionSortDTOS.add(userPositionSortDTO);
            }
        }
        return userPositionSortDTOS;
    }
}

