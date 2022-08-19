package com.lontologie.handlers;



import cn.hutool.core.util.ArrayUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lontologie.collect.LongList;

import com.lontologie.util.JacksonUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClickHouseArrayToLongListTypeHandler implements TypeHandler<LongList> {
    @Override
    public void setParameter(PreparedStatement ps, int i, LongList parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, ArrayUtil.toArray(parameter,Long.class));
    }

    @Override
    public LongList getResult(ResultSet rs, String columnName) throws SQLException {
        return toList(rs.getString(columnName));
    }

    @Override
    public LongList getResult(ResultSet rs, int columnIndex) throws SQLException {
        return toList(rs.getString(columnIndex));
    }

    @Override
    public LongList getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toList(cs.getString(columnIndex));
    }
    private LongList toList(String parameter) {
        return JacksonUtil.toObject(parameter.replace("'", "\""), new TypeReference<>() {
        });
    }
}
