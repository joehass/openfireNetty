package org.jivesoftware.mapper.openfire.mapper;

import org.apache.ibatis.annotations.Select;

public interface OfIdMapper {

    @Select("SELECT count(*) FROM ofID ")
   int count();
}
