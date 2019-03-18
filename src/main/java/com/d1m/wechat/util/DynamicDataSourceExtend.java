package com.d1m.wechat.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.d1m.common.ds.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by jone.wang on 2019/2/21.
 * Description: 增加支持使用wechatId切换数据源
 * <p>
 * {@link com.d1m.common.ds.DynamicDataSource}
 */
@SuppressWarnings("unchecked")
@Slf4j
public class DynamicDataSourceExtend extends DynamicDataSource {
    @Override
    public DataSource getDataSource(String tenant) {
        DruidDataSource ds = (DruidDataSource) this.getDefaultDataSource();
        Connection conn = null;
        HashMap map = null;

        try {
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tenant WHERE status = 1 and base_domain = ? or alias_domain = ? union select t.* from tenant t  left join wechat w on t.id = w.tenant_id where w.id = ?");
            ps.setString(1, tenant);
            ps.setString(2, tenant);
            ps.setString(3, tenant);
            ResultSet rs = ps.executeQuery();
            map = new HashMap();
            if (rs.next()) {
                map.put("driverClassName", "com.mysql.jdbc.Driver");
                map.put("url", rs.getString("ds_url"));
                map.put("username", rs.getString("ds_username"));
                map.put("password", rs.getString("ds_password"));
            }

            rs.close();
            ps.close();
        } catch (Exception var15) {
            log.error(var15.getMessage(), var15);
        } finally {
            try {
                if (Objects.nonNull(conn)) {
                    conn.close();
                }
            } catch (SQLException var14) {
                log.error(var14.getMessage(), var14);
            }

        }

        return map != null && !map.isEmpty() ? this.createDataSource(ds.cloneDruidDataSource(), map) : null;

    }
}
