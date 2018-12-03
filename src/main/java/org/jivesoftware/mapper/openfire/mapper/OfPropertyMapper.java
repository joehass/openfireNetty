package org.jivesoftware.mapper.openfire.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface OfPropertyMapper {

    @Insert("insert into ofProperty values(#{property},#{value})")
    int insertProperty(@Param("property")String property,@Param("value") String value);
}
