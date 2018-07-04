package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.sql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemRepositoryMapper {

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
    @Select("SELECT count(1) FROM ${tableName}")
    Integer count(@Param("tableName") String tableName);

    @Transactional(readOnly = true)
    @Select("SELECT ${selectedFields} FROM ${tableName} WHERE ${whereClause} and ${pk} = #{id} ")
    Map<String, Object> findById(@Param("selectedFields") String selectedFields,
                                 @Param("tableName") String tableName,
                                 @Param("whereClause") String whereClause,
                                 @Param("pk") String pk,
                                 @Param("id") String id);
}
