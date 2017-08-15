package com.xuie.android.ui.recyclerView.axis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuie on 17-8-9.
 */

public class DataBean {
    public String date;
    public String time;
    public String information;

    public DataBean(String date, String time, String information) {
        this.date = date;
        this.time = time;
        this.information = information;
    }

    private static List<DataBean> dataBeen;
    static {
        dataBeen = new ArrayList<>();
        dataBeen.add(new DataBean("2017.05.07", "13.55", "北京市朝阳区酒仙桥公司 已签收 签收人：邮件收发章"));
        dataBeen.add(new DataBean("2017.05.07", "06.55", "北京市朝阳区酒仙桥公司 派件中"));
        dataBeen.add(new DataBean("2017.05.07", "05.55", "北京市朝阳区酒仙桥 已收入"));
        dataBeen.add(new DataBean("2017.05.06", "22.55", "北京转运中心 已发出，下一站 北京朝阳区大中华区"));
        dataBeen.add(new DataBean("2017.05.06", "21.55", "北京转运中心 已收入"));
    }

    public static List<DataBean> getDataBeen() {
        return dataBeen;
    }
}
