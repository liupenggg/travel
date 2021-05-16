package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * Created by fei on 2019/11/26.
 */
public interface FavoriteDao {
    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid 查询收藏次数
     * @param rid
     * @return
     */
    public int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);

    void addCount(int count,int rid);

    public List<Favorite> findByUid(int uid);

    public List<Route> findRouteByPage(int icid, int startPage, int ipageSize, String cname, int ihighPrice, int ilowPrice, int order);

    public int findTotalCount(int icid, String cname, int ihighPrice, int ilowPrice, int order);
}
