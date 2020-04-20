package common;

import java.util.*;
import java.util.stream.Stream;

/**
 * 流的 peek 和 pushBack 操作
 * Stream: 随着时间的推移，逐渐产生可用数据
 * 作用：抽象出像工厂流水线一样处理数据的标准过程
 *
 * Stream的标准接口：next,hasNext
 * next 方法读取一个数据后，这个数据就相当于 **流过去了** 无法重复读取
 * @param <T>
 */

public class PeekIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;

    private Deque<T> cache;

    private Stack<T> stack;

    private final static int CACHE_SIZE = 10;

    public PeekIterator(Stream<T> stream) {
        iterator = stream.iterator();
        cache = new LinkedList<>();
        stack = new Stack<>();
    }

    /**
     * 查看当前 Stream 中节点，但不消耗当前节点
     * @return
     */
    public T peek() {
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        if (!cache.isEmpty()) {
            return cache.peekLast();
        }
        return null;
    }

    /**
     * Stream: abcdefg
     * 当通过 next 得到 c 时，执行一次 putBack 操作，下一次 next 时，得到 c
     * 即又回退了一次
     *
     * 这里我们通过 Stack 来 cache 住这一从putBack 的 c，如果再 putBack，会 push: b，
     * 这里 push 的元素是 queue 的 **队尾元素**
     * 当 next 时，如果 stack 中有值，说明 putBack 过了，则把栈顶元素 b 弹出
     *          —————————
     *         |a b c d | e f g ： (框起来的是cache)
     *         —————————
     *               |          此时指定 next 会得到 e
     *
     * == putBack===
     *
     *          —————————
     *         |a b c d | e f g ： (框起来的是cache)
     *         —————————
     *             |            此时指定 next 会得到 d
     *
     */
    public void putBack() {
        if (cache.isEmpty()) {
            return;
        }
        stack.push(cache.pollLast());
    }

    @Override
    public boolean hasNext() {
        /// 如果栈中有值，说明被 putBack 过
        return !stack.isEmpty() || iterator.hasNext();
    }

    @Override
    public T next() {
        T val;
        if (!stack.isEmpty()) {
            val = stack.pop();
        } else {
            val = iterator.next();
        }

        /// 确保 cache 中保存的元素最多有 CACHE_SIZE 个
        while (cache.size() > CACHE_SIZE - 1) {
            cache.poll();
        }
        /// 将 iterator 获取的值存入 cache 中
        cache.offer(val);
        return val;
    }
}