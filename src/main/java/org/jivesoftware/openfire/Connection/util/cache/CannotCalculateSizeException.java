package org.jivesoftware.openfire.Connection.util.cache;

/**
 * 为了抛出一个计算缓存长度的异常
 */
public class CannotCalculateSizeException extends Exception {
    public static final long serialVersionUID = 1754096832201752388L;

    public CannotCalculateSizeException() {
    }

    public CannotCalculateSizeException(Object obj) {
        super("Unable to determine size of " + obj.getClass() + " instance");
    }
}
