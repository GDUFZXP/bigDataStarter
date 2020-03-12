package com.vivo;

import org.junit.Test;
import org.roaringbitmap.IntConsumer;
import org.roaringbitmap.RoaringBitmap;

public class RoaringBitmapTest {
    @Test
    public void testBitMap(){
        RoaringBitmap bitmap = RoaringBitmap.bitmapOf(1,111,435435324,45634);
        byte[] bytes = serialize(bitmap);
        RoaringBitmap resultBitmap = deserialize(bytes);
        resultBitmap.forEach(new IntConsumer() {
            @Override
            public void accept(int i) {
                System.out.println(i);
            }
        });

    }


    public static RoaringBitmap deserialize(byte[] tmpArray){
        final byte[] array = tmpArray;
        if (null == array) {
            return null;
        }
        RoaringBitmap ret = new RoaringBitmap();
        try {
            ret.deserialize(new java.io.DataInputStream(new java.io.InputStream() {
                int c = 0;

                @Override
                public int read() {
                    return array[c++] & 0xff;
                }

                @Override
                public int read(byte b[]) {
                    return read(b, 0, b.length);
                }

                @Override
                public int read(byte[] b, int off, int l) {
                    System.arraycopy(array, c, b, off, l);
                    c += l;
                    return l;
                }
            }));
        } catch (Exception e) {
            return null;
        }
        return ret;
    }

    public byte[] serialize(RoaringBitmap mrb) {
        mrb.runOptimize(); //提高压缩率
        final byte[] array = new byte[mrb.serializedSizeInBytes()];
        try {
            mrb.serialize(new java.io.DataOutputStream(new java.io.OutputStream() {
                int c = 0;

                @Override
                public void close() {
                }

                @Override
                public void flush() {
                }

                @Override
                public void write(int b) {
                    array[c++] = (byte) b;
                }

                @Override
                public void write(byte[] b) {
                    write(b, 0, b.length);
                }

                @Override
                public void write(byte[] b, int off, int l) {
                    System.arraycopy(b, off, array, c, l);
                    c += l;
                }
            }));
        } catch (Exception e) {
            return null;
        }
        return array;
    }
}
