package tao.core.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DynamicSqlItemMapper {
    List<Map<String, Object>> get(@Param("tableName") String tableName);
}
