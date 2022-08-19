package com.lontologie.handlers;



import cn.hutool.core.util.ArrayUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lontologie.collect.StringList;
import com.lontologie.util.JacksonUtil;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClickHouse用字符串集合类型处理器
 *
 * @author guo jia
 */
public class ClickHouseArrayToStringListTypeHandler implements TypeHandler<StringList> {

    @Override
    public void setParameter(PreparedStatement ps, int i, StringList parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, ArrayUtil.toArray(parameter,String.class));
    }


    @Override
    public StringList  getResult(ResultSet rs, String columnName) throws SQLException {
        return toList(rs.getString(columnName));
    }

    @Override
    public StringList  getResult(ResultSet rs, int columnIndex) throws SQLException {
        return toList(rs.getString(columnIndex));
    }

    @Override
    public StringList getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toList(cs.getString(columnIndex));
    }

    private StringList toList(String parameter) {
        return JacksonUtil.toObject(parameter.replace("'", "\""), new TypeReference<>() {
        });
    }

}
