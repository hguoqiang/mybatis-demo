package org.hgq.sqlsession;

import org.hgq.utils.ParameterMapping;

import java.util.List;

/**
 * @description:
 * @author: huangguoqiang
 * @create: 2021-08-11 17:37
 **/
public class BoundSql {
    private String sql;
    private List<ParameterMapping> parameterMappings;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }
}
