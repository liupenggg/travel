package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * Created by fei on 2019/11/25.
 */
public interface SellerDao {
    /**
     * 根据sid查询
     * @param sid
     * @return
     */
    public Seller findById(int sid);
}
