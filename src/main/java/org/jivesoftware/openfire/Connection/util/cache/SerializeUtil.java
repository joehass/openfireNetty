package org.jivesoftware.openfire.Connection.util.cache;

import org.jivesoftware.openfire.Connection.redis.CacheObject;

import java.io.*;

public class SerializeUtil{
    public static int objectIndex = 0;

    public SerializeUtil() {
    }

    public static String serialize(CacheObject object){
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String result = new String(byteArrayOutputStream.toByteArray(), "ISO-8859-1");
            String var5 = result;
            return var5;
        } catch (Exception var17) {
            System.err.println("序列化异常：" + var17.getMessage());
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.reset();
                    objectOutputStream.close();
                } catch (IOException var16) {
                    ;
                }
            }

            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.reset();
                    byteArrayOutputStream.close();
                } catch (IOException var15) {
                    ;
                }
            }

        }

        return null;
    }

    public static CacheObject unserialize(String strObject, String name, String key) {
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;

        try {
            byteArrayInputStream = new ByteArrayInputStream(strObject.getBytes("ISO-8859-1"));
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            CacheObject object = (CacheObject)objectInputStream.readObject();
            ++objectIndex;
            CacheObject var7 = object;
            return var7;
        } catch (Exception var19) {
            System.err.println(name + "==" + key + "==反序列化异常：" + var19.getMessage());
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException var18) {
                    ;
                }
            }

            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException var17) {
                    ;
                }
            }

        }

        return null;
    }
}
