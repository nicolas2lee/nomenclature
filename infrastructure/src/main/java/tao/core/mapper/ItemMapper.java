package tao.core.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemMapper {

    // TODO: 26/06/2018 need to avoid sql injection, need pre process tableName
    @Select("SELECT * FROM ${tableName}")
    List<Map<String, Object>> get(@Param("tableName") String tableName);
}
