package org.jivesoftware.openfire.Connection.util.cache;

import java.io.Serializable;

public interface Cacheable  extends Serializable {
    int getCachedSize() throws CannotCalculateSizeException;
}
