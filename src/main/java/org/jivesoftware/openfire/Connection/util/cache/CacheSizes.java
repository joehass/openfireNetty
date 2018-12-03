package org.jivesoftware.openfire.Connection.util.cache;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 这里设置缓存中key的字节长度
 */
public class CacheSizes {

    public static int sizeOfObject() {
        return 4;
    }

    public static int sizeOfString(String string) {
        return string == null ? 0 : 4 + string.getBytes().length;
    }

    public static int sizeOfInt() {
        return 4;
    }

    public static int sizeOfChar() {
        return 2;
    }

    public static int sizeOfBoolean() {
        return 1;
    }

    public static int sizeOfLong() {
        return 8;
    }

    public static int sizeOfDouble() {
        return 8;
    }

    public static int sizeOfDate() {
        return 12;
    }

    public static int sizeOfMap(Map<?, ?> map) throws CannotCalculateSizeException {
        if (map == null) {
            return 0;
        } else {
            int size = 36;
            Set<? extends Map.Entry> set = map.entrySet();

            Map.Entry entry;
            for (Iterator var3 = set.iterator(); var3.hasNext(); size += sizeOfAnything(entry.getValue())) {
                entry = (Map.Entry) var3.next();
                size += sizeOfAnything(entry.getKey());
            }

            return size;
        }
    }

    /**
     * 计算collection的长度
     *
     * @param list
     * @return
     */
    public static int sizeOfCollection(Collection list) throws CannotCalculateSizeException {
        if (list == null) {
            return 0;
        } else {
            int size = 36;
            Object[] values = list.toArray();

            for(int i = 0; i < values.length; ++i) {
                size += sizeOfAnything(values[i]);
            }

            return size;
        }
    }

    /**
     * 这里统计所有其他类型的长度
     *
     * @param object
     * @return
     * @throws CannotCalculateSizeException
     */
    public static int sizeOfAnything(Object object) throws CannotCalculateSizeException {
        if (object == null) {
            return 0;
        } else if (object instanceof Cacheable) {
            return ((Cacheable) object).getCachedSize();
        } else if (object instanceof String) {
            return sizeOfString((String) object);
        } else if (object instanceof Long) {
            return sizeOfLong();
        } else if (object instanceof Integer) {
            return sizeOfObject() + sizeOfInt();
        } else if (object instanceof Double) {
            return sizeOfObject() + sizeOfDouble();
        } else if (object instanceof Boolean) {
            return sizeOfObject() + sizeOfBoolean();
        } else if (object instanceof Map) {
            return sizeOfMap((Map) object);
        } else if (object instanceof long[]) {
            long[] array = (long[]) ((long[]) object);
            return sizeOfObject() + array.length * sizeOfLong();
        } else if (object instanceof Collection) {
            return sizeOfCollection((Collection) object);
        } else if (object instanceof byte[]) {
            byte[] array = (byte[]) ((byte[]) object);
            return sizeOfObject() + array.length;
        } else {
            boolean var1 = true;

            try {
                CacheSizes.NullOutputStream out = new CacheSizes.NullOutputStream();
                ObjectOutputStream outputStream = new ObjectOutputStream(out);
                outputStream.writeObject(object);
                int size = out.size();
                return size;
            } catch (IOException e) {
                throw new CannotCalculateSizeException(object);
            }
        }
    }

    /**
     * 计算null值长度
     */
    private static class NullOutputStream extends OutputStream {
        int size;

        private NullOutputStream() {
            this.size = 0;
        }

        @Override
        public void write(int b) throws IOException {
            ++this.size;
        }

        public void write(byte[] b) throws IOException {
            this.size += b.length;
        }

        public void write(byte[] b, int off, int len) {
            this.size += len;
        }

        public int size() {
            return this.size;
        }
    }
}

