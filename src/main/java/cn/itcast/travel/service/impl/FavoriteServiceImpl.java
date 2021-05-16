package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fei on 2019/11/26.
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;// 如果这个对象有值，则为true，反之，则为false
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    @Override
     public void addCount(String rid) {
        // 4.查询收藏次数
        int count = favoriteDao.findCountByRid(Integer.parseInt(rid));
        favoriteDao.addCount(count,Integer.parseInt(rid));
    }

    @Override
    public List<Route> findByUid(int uid) {
        List<Favorite> cs = null;
        cs = favoriteDao.findByUid(uid);
        //
        List<Route> pp = null;
        pp = new ArrayList<Route>();// []
        //
        for (int i = 0; i < cs.size(); i++) {
            int rid = cs.get(i).getRid();//1,6
            pp.add(routeDao.findOne(rid));
        }
        return pp;
    }

    @Override
    public PageBean findRouteOrderPageBean(String cid, String currentPage, String cname, String highPrice, String lowPrice, String pageSize) {
        int icid=0;
        if(cid != "" && cid != null && cid != "null"){
            icid = Integer.parseInt(cid);
        }

        int icurrentPage=1;
        if(currentPage != "" && currentPage != null && currentPage != "null"){
            icurrentPage = Integer.parseInt(currentPage);
        }

        int ihighPrice=-1;
        if(highPrice != "" && highPrice != null && highPrice != "null"){
            ihighPrice = Integer.parseInt(highPrice);
        }

        int ilowPrice=-1;
        if(lowPrice != "" && lowPrice != null && lowPrice != "null"){
            ilowPrice = Integer.parseInt(lowPrice);
        }

        int ipageSize=0;
        if(pageSize != "" && pageSize != null && pageSize != "null"){
            ipageSize = Integer.parseInt(pageSize);
        }
        /*
        * order排序书说明：
        * 0为不排序
        * 1为根据单个路线总收藏排序
        * 2 根据最新事件排序
        * 3 根据是否主题排序
        * */
        int order=1;
        PageBean<Route> pageBean=new PageBean<>();


        pageBean.setCurrentPage(icurrentPage);
        pageBean.setPageSize(ipageSize);
        int startPage=(icurrentPage-1)*ipageSize;
        List<Route> list=favoriteDao.findRouteByPage(icid,startPage,ipageSize,cname,ihighPrice,ilowPrice,order);
        for (Route route : list) {
            System.out.println(route.getCount());
        }
        pageBean.setList(list);

        int totalCount = favoriteDao.findTotalCount(icid,cname,ihighPrice,ilowPrice,order);
        pageBean.setTotalCount(totalCount);
        int totalPage=totalCount%ipageSize==0?totalCount/ipageSize:((totalCount/ipageSize)+1);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }
}

