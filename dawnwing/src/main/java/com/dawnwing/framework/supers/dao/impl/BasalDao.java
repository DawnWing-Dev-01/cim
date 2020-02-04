package com.dawnwing.framework.supers.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.core.SimplePage;
import com.dawnwing.framework.supers.dao.api.IBasalDao;

public class BasalDao<T> implements IBasalDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Class<T> entityClass;

    public BasalDao() {
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    public boolean isExist(String className, String id) {
        String hql = "select t from " + className
                + " t where 1 = 1 and t.id = ?";
        Query query = getSession().createQuery(hql);
        query.setString(0, id);
        Object obj = query.uniqueResult();
        return obj != null;
    }

    /**
     * <保存实体> <完整保存实体>
     * 
     * @param t
     *            实体参数
     * @see com.itv.launcher.util.IBaseDao#save(java.lang.Object)
     */
    @Override
    public void save(T t) {
        this.getSession().save(t);
    }

    /**
     * <保存或者更新实体>
     * 
     * @param t
     *            实体
     * @see com.itv.launcher.util.IBaseDao#saveOrUpdate(java.lang.Object)
     */
    @Override
    public void saveOrUpdate(T t) {
        this.getSession().saveOrUpdate(t);
    }

    /**
     * <load> <加载实体的load方法>
     * 
     * @param id
     *            实体的id
     * @return 查询出来的实体
     * @see com.itv.launcher.util.IBaseDao#load(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T load(String id) {
        T load = (T) this.getSession().load(getEntityClass(), id);
        return load;
    }

    /**
     * <get> <查找的get方法>
     * 
     * @param id
     *            实体的id
     * @return 查询出来的实体
     * @see com.itv.launcher.util.IBaseDao#get(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(String id) {
        T load = (T) this.getSession().get(getEntityClass(), id);
        return load;
    }

    /**
     * <contains>
     * 
     * @param t
     *            实体
     * @return 是否包含
     * @see com.itv.launcher.util.IBaseDao#contains(java.lang.Object)
     */
    @Override
    public boolean contains(T t) {
        return this.getSession().contains(t);
    }

    /**
     * <delete> <删除表中的t数据>
     * 
     * @param t
     *            实体
     * @see com.itv.launcher.util.IBaseDao#delete(java.lang.Object)
     */
    @Override
    public void delete(T t) {
        this.getSession().delete(t);
    }

    /**
     * <根据ID删除数据>
     * 
     * @param Id
     *            实体id
     * @return 是否删除成功
     * @see com.itv.launcher.util.IBaseDao#deleteById(java.io.Serializable)
     */
    @Override
    public boolean deleteById(String Id) {
        T t = get(Id);
        if (t == null) {
            return false;
        }
        delete(t);
        return true;
    }

    /**
     * <删除所有>
     * 
     * @param entities
     *            实体的Collection集合
     * @see com.itv.launcher.util.IBaseDao#deleteAll(java.util.Collection)
     */
    @Override
    public void deleteAll(Collection<T> entities) {
        for (Object entity : entities) {
            this.getSession().delete(entity);
        }
    }

    /**
     * <执行Hql语句>
     * 
     * @param hqlString
     *            hql
     * @param values
     *            不定参数数组
     * @see com.itv.launcher.util.IBaseDao#queryHql(java.lang.String,
     *      java.lang.Object[])
     */
    @Override
    public void queryHql(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.executeUpdate();
    }

    /**
     * <执行Sql语句>
     * 
     * @param sqlString
     *            sql
     * @param values
     *            不定参数数组
     * @see com.itv.launcher.util.IBaseDao#querySql(java.lang.String,
     *      java.lang.Object[])
     */
    @Override
    public Integer querySql(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.executeUpdate();
    }

    /**
     * <根据HQL语句查找唯一实体>
     * 
     * @param hqlString
     *            HQL语句
     * @param values
     *            不定参数的Object数组
     * @return 查询实体
     * @see com.itv.launcher.util.IBaseDao#getByHQL(java.lang.String,
     *      java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getByHQL(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    /**
     * <根据SQL语句查找唯一实体>
     * 
     * @param sqlString
     *            SQL语句
     * @param values
     *            不定参数的Object数组
     * @return 查询实体
     * @see com.itv.launcher.util.IBaseDao#getBySQL(java.lang.String,
     *      java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    /**
     * <根据HQL语句，得到对应的list>
     * 
     * @param hqlString
     *            HQL语句
     * @param values
     *            不定参数的Object数组
     * @return 查询多个实体的List集合
     * @see com.itv.launcher.util.IBaseDao#getListByHQL(java.lang.String,
     *      java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> getListByHQL(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    /**
     * <根据SQL语句，得到对应的list>
     * 
     * @param sqlString
     *            HQL语句
     * @param values
     *            不定参数的Object数组
     * @return 查询多个实体的List集合
     * @see com.itv.launcher.util.IBaseDao#getListBySQL(java.lang.String,
     *      java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> getListBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    /**
     * <refresh>
     * 
     * @param t
     *            实体
     * @see com.itv.launcher.util.IBaseDao#refresh(java.lang.Object)
     */
    @Override
    public void refresh(T t) {
        this.getSession().refresh(t);
    }

    /**
     * <update>
     * 
     * @param t
     *            实体
     * @see com.itv.launcher.util.IBaseDao#update(java.lang.Object)
     */
    @Override
    public void update(T t) {
        this.getSession().update(t);
    }

    /**
     * <根据HQL得到记录数>
     * 
     * @param hql
     *            HQL语句
     * @param values
     *            不定参数的Object数组
     * @return 记录总数
     * @see com.itv.launcher.util.IBaseDao#countByHql(java.lang.String,
     *      java.lang.Object[])
     */
    @Override
    public Long countByHql(String hql, Object... values) {
        Long count = null;
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        count = (Long) query.uniqueResult();
        return count != null ? count : new Long(0);
    }

    /**
     * <HQL分页查询>
     * 
     * @param hql
     *            HQL语句
     * @param countHql
     *            查询记录条数的HQL语句
     * @param pageNo
     *            下一页
     * @param pageSize
     *            一页总条数
     * @param values
     *            不定Object数组参数
     * @return PageResults的封装类，里面包含了页码的信息以及查询的数据List集合
     * @see com.itv.launcher.util.IBaseDao#findPageByFetchedHql(java.lang.String,
     *      java.lang.String, int, int, java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public SimplePage<T> findPageByFetchedHql(String hql, String countHql,
            int pageNo, int pageSize, Object... values) {
        SimplePage<T> retValue = new SimplePage<T>();
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        int currentPage = pageNo > 1 ? pageNo : 1;
        retValue.setNowPage(currentPage);
        retValue.setPageSize(pageSize);
        if (countHql == null) {
            ScrollableResults results = query.scroll();
            results.last();
            retValue.setTotalNum(results.getRowNumber() + 1);// 设置总记录数
        } else {
            Long count = countByHql(countHql, values);
            retValue.setTotalNum(count.intValue());
        }
        retValue.next();
        List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize)
                .setMaxResults(pageSize).list();
        if (itemList == null) {
            itemList = new ArrayList<T>();
        }
        retValue.setResults(itemList);
        return retValue;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @param sessionFactory
     *            the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 
     * @return session
     */
    public Session getSession() {
        // 需要开启事物，才能得到CurrentSession
        return sessionFactory.getCurrentSession();
    }

    /**
     * 
     * 设置每行批处理参数
     * 
     * @param ps
     * @param pos
     *            ?占位符索引，从0开始
     * @param data
     * @throws SQLException
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unused")
    private void setParameter(PreparedStatement ps, int pos, Object data)
            throws SQLException {
        if (data == null) {
            ps.setNull(pos + 1, Types.VARCHAR);
            return;
        }
        Class<? extends Object> dataCls = data.getClass();
        if (String.class.equals(dataCls)) {
            ps.setString(pos + 1, (String) data);
        } else if (boolean.class.equals(dataCls)) {
            ps.setBoolean(pos + 1, ((Boolean) data));
        } else if (int.class.equals(dataCls)) {
            ps.setInt(pos + 1, (Integer) data);
        } else if (double.class.equals(dataCls)) {
            ps.setDouble(pos + 1, (Double) data);
        } else if (Date.class.equals(dataCls)) {
            Date val = (Date) data;
            ps.setTimestamp(pos + 1, new Timestamp(val.getTime()));
        } else if (BigDecimal.class.equals(dataCls)) {
            ps.setBigDecimal(pos + 1, (BigDecimal) data);
        } else {
            // 未知类型
            ps.setObject(pos + 1, data);
        }
    }

    /**
     * 对filterList排序, 让占位符的排在前面
     * @param filterList
     */
    public void sortArrayList(List<FilterBean> filterList) {
        Collections.sort(filterList, new Comparator<FilterBean>() {

            @Override
            public int compare(FilterBean o1, FilterBean o2) {
                return o1.getIsNamed().compareTo(o2.getIsNamed());
            }

        });
    }

    public String setFilterBean(List<FilterBean> filterList, String hql) {
        StringBuffer buffer = new StringBuffer(hql);
        /*for (FilterBean filterBean : filterList) {
            buffer.append(" and ");
            buffer.append(filterBean.getProperty());
            buffer.append(" ");
            buffer.append(filterBean.getFactor());
            buffer.append(" ? ");
        }*/

        // 对filterList排序, 让占位符的排在前面
        sortArrayList(filterList);
        for (FilterBean filterBean : filterList) {
            buffer.append(" and ");
            buffer.append(filterBean.getProperty());
            buffer.append(" ");
            buffer.append(filterBean.getFactor());
            if (filterBean.getIsNamed()) {
                String namedPara = getNamedPara(filterBean.getProperty());
                buffer.append(" :" + namedPara + " ");
            } else {
                buffer.append(" ? ");
            }
        }

        return buffer.toString();
    }

    /**
     * @param filterList
     * @param hql
     * @return
     */
    public Query createQuery(List<FilterBean> filterList, String hql) {
        Query query = getSession().createQuery(hql);
        /*int i = 0;
        for (FilterBean filterBean : filterList) {
            if (filterBean.getParameter() instanceof String) { // String
                query.setString(i, (String) filterBean.getParameter());
            } else if (filterBean.getParameter() instanceof Date) { // Date
                query.setTimestamp(i, (Date) filterBean.getParameter());
            } else if (filterBean.getParameter() instanceof Long) { // Long
                query.setLong(i, (Long) filterBean.getParameter());
            } else if (filterBean.getParameter() instanceof Double) { // Double
                query.setDouble(i, (Double) filterBean.getParameter());
            } else if (filterBean.getParameter() instanceof Integer) { // Integer
                query.setDouble(i, (Integer) filterBean.getParameter());
            } else if (filterBean.getParameter() instanceof Float) { // Float
                query.setDouble(i, (Float) filterBean.getParameter());
            }
            i++;
        }*/
        // 对filterList排序, 让占位符的排在前面
        sortArrayList(filterList);
        int i = 1;
        for (FilterBean filterBean : filterList) {
            if (filterBean.getIsNamed()) {
                String namedPara = getNamedPara(filterBean.getProperty());
                query.setParameter(namedPara, filterBean.getParameter());
            } else {
                query.setParameter(i, filterBean.getParameter());
                i++;
            }
        }

        return query;
    }
    
    public SQLQuery createSqlQuery(List<FilterBean> filterList, String hql) {
        SQLQuery query = getSession().createSQLQuery(hql);
        // 对filterList排序, 让占位符的排在前面
        sortArrayList(filterList);
        int i = 1;
        for (FilterBean filterBean : filterList) {
            if (filterBean.getIsNamed()) {
                String namedPara = getNamedPara(filterBean.getProperty());
                query.setParameter(namedPara, filterBean.getParameter());
            } else {
                query.setParameter(i, filterBean.getParameter());
                i++;
            }
        }
        return query;
    }

    /**
     * @param hql
     * @return
     */
    public Query createQuery(String hql) {
        return getSession().createQuery(hql);
    }

    /**
     * 使用命名参数, 代替？占位符
     * @param property
     * @return
     */
    private String getNamedPara(String property) {
        // 去掉字符串中的括号
        String namedPara = property.replace("(", "").replace(")", "");
        int dotindex = namedPara.indexOf(".");
        if (dotindex != -1) {
            namedPara = namedPara.substring(dotindex + 1);
        }
        return namedPara;
    }

    @Override
    public List<T> findListBySql(String sql, RowMapper<T> map, Object... values) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 排序方法
     * @param hql
     * @param orderField 排序字段, 包含ascOrDesc
     * @param ascOrDesc 升序or 降序
     * @return
     */
    public String orderBy(String hql, String... orderFields) {
        StringBuffer buffer = new StringBuffer(hql);
        if (!(orderFields != null && orderFields.length != 0)) {
            return buffer.toString();
        }

        buffer.append("order by ");
        int index = 0;

        for (String orderField : orderFields) {
            buffer.append(orderField);
            if (index < (orderFields.length - 1)) {
                buffer.append(",");
            }
            buffer.append(" ");

            index++;
        }
        return buffer.toString();
    }

}
