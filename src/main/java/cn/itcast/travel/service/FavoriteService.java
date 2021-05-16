package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * Created by fei on 2019/11/26.
 */
public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    void addCount(String rid);

    public List<Route> findByUid(int uid);

    public PageBean findRouteOrderPageBean(String cid, String currentPage, String cname, String highPrice, String lowPrice, String pageSize);
}
