package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by fei on 2019/11/24.
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        // 1.从redis中查询
        // 1.1获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        // 1.2可使用sortedset排序查询
        //Set<String> categorys = jedis.zrange("category",0,-1);
        // 1.3查询sortedset中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category",0,-1);
        //
        List<Category> cs = null;
        // 2.判断查询的集合是否为空
        if(categorys == null || categorys.size() == 0){
            //System.out.println("从数据库查询。。。");
            // 3.如果为空，需要从数据库查询，再将数据存入redis
            // 3.1从数据库查询
            cs = categoryDao.findAll();// [{cid:1,cname:'国内游'},{},{}]
            // 3.2 将集合数据存储到redis中的一个叫做category的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            //System.out.println("从redis中查询。。。");
            // 4.如果不为空，将set的数据存入list
            cs = new ArrayList<Category>();// []
            for (Tuple tuple : categorys) {
                Category category = new Category();
                // 门票 酒店 香港车票 出境游
                //System.out.println("tuple.getElement()"+tuple.getElement());
                category.setCname(tuple.getElement());
                // 1 2 3
                category.setCid((int)tuple.getScore());
                cs.add(category);
                //System.out.println("cs"+cs);
                // [Category{cid=1, cname='门票'}]
                // [Category{cid=1, cname='门票'}, Category{cid=2, cname='酒店'}]
                // [Category{cid=1, cname='门票'}, Category{cid=2, cname='酒店'}, Category{cid=3, cname='香港车票'}]
            }
        }

        return cs;
    }
}
