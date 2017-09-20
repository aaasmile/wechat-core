package com.d1m.wechat.util.mybatis;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by d1m on 2016/10/19.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonArrayHandler extends BaseTypeHandler<JSONArray> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, JSONArray jsonArray, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, jsonArray.toJSONString());
    }

    @Override
    public JSONArray getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return JSONArray.parseArray(resultSet.getString(s));
    }

    @Override
    public JSONArray getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return JSONArray.parseArray(resultSet.getString(i));
    }

    @Override
    public JSONArray getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JSONArray.parseArray(callableStatement.getString(i));
    }
}
