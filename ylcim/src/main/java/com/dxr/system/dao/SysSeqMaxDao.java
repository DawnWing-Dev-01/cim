package com.dxr.system.dao;

import java.util.Date;

import com.dawnwing.framework.supers.dao.impl.BasalDao;
import com.dxr.system.entity.SysSeqMax;

/**
 * @description: <流水序列数据操作类>
 * @author: w.xL
 * @date: 2018-4-17
 */
public class SysSeqMaxDao extends BasalDao<SysSeqMax> {

    private Object syncLock = new Object();

    /**
     * 获取Seq, 每次自增1, 每天从头开始
     * @param numberId
     * @return
     */
    public Integer updateAndGetCurrentSeq(String seqId) {
        Integer nowNum = null;
        synchronized (syncLock) {
            StringBuffer updateHql = new StringBuffer("update sys_seq_max ");
            updateHql.append("set max_no = ( case ");
            updateHql.append("when date_format(last_date, '%Y-%m-%d') "
                    + "= date_format(now(), '%Y-%m-%d') ");
            updateHql.append("then max_no + 1 ");
            updateHql.append("else 1 ");
            updateHql.append("end ), last_date = now() ");
            updateHql.append("where 1 = 1 ");
            updateHql.append("and seq_id = ? ");

            // 更新SysSeqMax表中的相关记录
            Integer updateRows = super.querySql(updateHql.toString(), seqId);
            /* 得到当前的序列号 start */
            SysSeqMax sysSeqMax = super.get(seqId);
            if (sysSeqMax == null) {
                // 表中没有相应记录，则生成一条，序列号为1
                nowNum = 1;
                sysSeqMax = new SysSeqMax();
                sysSeqMax.setSeqId(seqId);
                sysSeqMax.setMaxNo(nowNum);
                sysSeqMax.setLastDate(new Date());
                super.save(sysSeqMax);
            } else {
                if (updateRows == 0) {
                    // 更新的行数为0,表示序列号没有更新
                    nowNum = 1;
                    sysSeqMax.setSeqId(seqId);
                    sysSeqMax.setMaxNo(nowNum);
                    sysSeqMax.setLastDate(new Date());
                    super.save(sysSeqMax);
                }
                // 表中有记录，就得到序列号
                nowNum = sysSeqMax.getMaxNo();
            }
        }
        return nowNum;
    }
}
