package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fei on 2019/11/24.
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql = "select count(*) from tab_route where cid=?";
        // 1.定义sql模板，一定要注意最后一个1后的空格
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();// 条件们
        // 2.判断参数是否有值
        if(cid != 0){
            // 最好两端都加上空格
            sb.append(" and cid = ? ");
            //添加？对应的值
            params.add(cid);
        }

        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            //最好两端都加上空格
            sb.append(" and rname like ? ");

            // 拼接字符串千万不能从左往右，非常容易写错
            params.add("%"+rname+"%");
        }

        sql = sb.toString();//这一步一定不要忘记，就是sql重新去赋值，要不你的sql永远还是原来的sql
        // 返回的是Integer.class
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        // limit的第一个问号代表从那开始，第二个问号代表查询多少条记录
        //String sql = "select * from tab_route where cid = ? and rname like ? limit ? , ?";
        String sql = "select * from tab_route where 1 = 1 ";
        // 1.定义sql模板，一定要注意最后一个1后的空格
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();// 条件们
        // 2.判断参数是否有值
        if(cid != 0){
            // 最好两端都加上空格
            sb.append(" and cid = ? ");
            //添加？对应的值
            params.add(cid);
        }

        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            //最好两端都加上空格
            sb.append(" and rname like ? ");

            // 拼接字符串千万不能从左往右，非常容易写错
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件
        sql = sb.toString();//这一步一定不要忘记，就是sql重新去赋值，要不你的sql永远还是原来的sql
        params.add(start);
        params.add(pageSize);
        // params.toArray()代表将集合转成数组

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
