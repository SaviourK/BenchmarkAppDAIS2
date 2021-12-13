package cz.laryngektomie.benchmark.dao;

public class DaoBase {

    protected String tableName;

    public DaoBase(String tableName) {
        this.tableName = tableName;
    }

    protected String fillSql(String sql, Object... values) {
        return String.format(sql, values);
    }
}
