package com.vivo;

import org.roaringbitmap.IntConsumer;
import org.roaringbitmap.RoaringBitmap;

/**
 * @Author xupeng.zhang
 * @Date 2020/3/11 18:32
 */
public class RoaringBitmapDemo {
    public static void main(String[] args) {
        RoaringBitmap rr = RoaringBitmap.bitmapOf(9,3,4,6,7);
        rr.add(158);
        System.out.println(rr.select(3));
        System.out.println(rr.rank(3));
        System.out.println(rr.contains(6));
        System.out.println(rr.getLongCardinality());
        System.out.println("===========================");
        rr.forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });
        final int[] ints = rr.toArray();
        System.out.println(ints);

        byte[] bytes = new byte[rr.serializedSizeInBytes()];

    }
}
