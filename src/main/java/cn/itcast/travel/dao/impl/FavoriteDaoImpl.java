package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fei on 2019/11/26.
 */
public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {

        Favorite favorite = null;
        String sql = "select * from tab_favorite where rid=? and uid=?";

        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
            if (favorite != null){
                return favorite;
            }
        }catch (EmptyResultDataAccessException e){
            return null;
        }


        return null;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid=?";
        // 第二个参数指定返回的类型
        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }

    @Override
    public void addCount(int count,int rid) {
        String sql = "Update tab_route set count=? where rid=?";
        template.update(sql,count,rid);
    }

    @Override
    public List<Favorite> findByUid(int uid) {
        String sql = "select * from tab_favorite where uid=?";

        return template.query(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),uid);
    }

    @Override
    public List<Route> findRouteByPage(int icid, int startPage, int ipageSize, String cname, int ihighPrice, int ilowPrice, int order) {
        String sql="select * from tab_route  WHERE 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList();
        if(icid!=0){
            sb.append(" and cid = ? ");
            params.add(icid);
        }
        //System.out.println("cname"+cname);
        if(cname != null && cname.length()>0 && !"null".equals(cname)){
            sb.append(" and rname like ? ");
            params.add("%"+cname+"%");

        }
        //System.out.println("ihighPrice"+ihighPrice);
        if(ihighPrice!=-1){
            sb.append(" and price < ?");
            params.add(ihighPrice);
        }
        //System.out.println("ilowPrice"+ilowPrice);
        if(ilowPrice!=-1){
            sb.append(" and price > ?");
            params.add(ilowPrice);

        }
        if(order==1){
            sb.append(" order by count desc ");
        }else if(order==2) {
            sb.append(" order by rdate desc ");
        }else if(order==3){
            sb.append(" order by isThemeTour  desc ");
        }
        sb.append(" limit ? , ? ");
        params.add(startPage);
        params.add(ipageSize);
        sql=sb.toString();


        System.out.println(sql);
        System.out.println(params);

        List<Route> list=template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        System.out.println("FavoriteDaoImp的："+list);
        return list ;
    }

    @Override
    public int findTotalCount(int icid, String cname, int ihighPrice, int ilowPrice, int order) {
        String sql="select count(*) from tab_route  WHERE 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList();
        if(icid!=0){
            sb.append(" and cid = ? ");
            params.add(icid);
        }
        if(cname!=null&&cname.length()>0&&!"null".equals(cname)){
            sb.append(" and rname like ? ");
            params.add("%"+cname+"%");
        }

        if(ihighPrice!=-1){
            sb.append(" and price < ?");
            params.add(ihighPrice);
        }
        if(ilowPrice!=-1){
            sb.append(" and price > ?");
            params.add(ilowPrice);

        }
        if(order==1){
            sb.append(" order by count desc ");
        }
        sql=sb.toString();

        Integer count = template.queryForObject(sql, Integer.class, params.toArray());
        return count;
    }
}
