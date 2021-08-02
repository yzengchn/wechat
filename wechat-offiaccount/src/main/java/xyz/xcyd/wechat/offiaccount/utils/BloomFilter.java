package xyz.xcyd.wechat.offiaccount.utils;

import java.util.BitSet;

/**
 * 布隆过滤器
 */
public class BloomFilter {

    /**
     * 位数组的大小
     */
    private static final int DEFAULT_SIZE = 2 << 24;

    /**
     * 通过数组创建6个不同的哈希函数
     */
    private static final int[] SEEDS = new int[]{3, 13, 46, 71, 91, 134};

    /**
     * 位数组 （位数组中元素只能是0和1）
     */
    private BitSet bitSet = new BitSet(DEFAULT_SIZE);

    /**
     * 存放包含 hash函数的类的数组
     */
    private Hash[] func = new Hash[SEEDS.length];


    /**
     * 初始化多个包含hash函数的数组，每个数组元素中的hash 函数不一样
     */
    public BloomFilter() {
        for(int i = 0; i < SEEDS.length; i++){
            func[i] = new Hash(DEFAULT_SIZE, SEEDS[i]);
        }
    }

    /**
     * 添加元素到位数组
     * @param value
     */
    public void add(Object value) {
        // 遍历hash函数数组，得到多个hash值分别放入位数组
        for(Hash f : func){
            bitSet.set(f.hash(value), true);
        }
    }

    /**
     * 判断指定元素是否位于位数组中
     * @param value
     * @return
     */
    public boolean contains(Object value) {
        boolean ret = true;
        // 遍历hash函数数组，分别get看是否存在再总计
        for(Hash f : func){
            ret = ret && bitSet.get(f.hash(value));
        }
        return ret;
    }

    /**
     * 用于操作hash
     */
    public static class Hash{
        private int cap;
        private int seed;

        public Hash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        /**
         * 计算哈希值
         * @param value
         * @return
         */
        public int hash(Object value){
            int h;
            return value == null ? 0 : Math.abs(seed * (cap - 1) & ((h = value.hashCode()) ^ (h >>> 16)));
        }
    }

    public static void main(String[] args) {
        BloomFilter bloomFilter = new BloomFilter();
        bloomFilter.add("a");
        bloomFilter.add("b");
        bloomFilter.add("c");
        System.out.println(bloomFilter.contains("c"));
        System.out.println(bloomFilter.contains("d"));
    }

}
