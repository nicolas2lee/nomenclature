package tao.features.core.repositorymapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemRepositoryMapper {

    // TODO: 26/06/2018 need to avoid sql injection, need pre process tableName
    @Transactional(readOnly = true)
    @Select("SELECT ${selectedFields} FROM ${tableName} WHERE ${whereClause} order by ${orderByField} ${orderByDirection} ${limitClause}")
    List<Map<String, Object>> getAll(@Param("selectedFields") String selectedFields,
                                     @Param("tableName") String tableName,
                                     @Param("whereClause") String whereClause,
                                     @Param("orderByField") String orderByField,
                                     @Param("orderByDirection") String orderByDirection,
                                     @Param("limitClause") String limitClause
    );

    @Transactional(readOnly = true)
    @Select("SELECT count(*) FROM ${tableName}")
    Integer count(@Param("tableName") String tableName);
}
