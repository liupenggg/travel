package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * Created by fei on 2019/11/25.
 */
public interface RouteImgDao {

    /**
     * 根据route的id去查询它的图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
