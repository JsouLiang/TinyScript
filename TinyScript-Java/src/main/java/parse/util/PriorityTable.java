package parse.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 优先级表
 */
public class PriorityTable {
    private List<List<String>> table = new ArrayList<>();

    public PriorityTable() {
        /// 一元逻辑运算符
        table.add(Arrays.asList("&", "|", "^"));
        /// 二元比较运算符
        table.add(Arrays.asList("==", "!=", ">", "<", ">=", "<="));
        /// 二元操作符
        table.add(Arrays.asList("+", "-"));
        table.add(Arrays.asList("*", "/"));
        /// 位移运算
        table.add(Arrays.asList("<<", ">>"));
    }

    /**
     * 优先级的数量
     * @return
     */
    public int size() {
        return table.size();
    }

    /**
     * 获得某一优先级的操作符
     * @param level 优先级
     * @return
     */
    public List<String> get(int level) {
        return table.get(level);
    }
}
