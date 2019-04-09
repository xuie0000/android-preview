package com.xuie.android.ui.recycler.axis

import java.util.ArrayList

/**
 * @author xuie
 * @date 17-8-9
 */

class DataBean(var date: String, var time: String, var information: String) {
  companion object {

    private var dataBeen: MutableList<DataBean>? = null

    init {
      dataBeen = ArrayList()
      dataBeen!!.add(DataBean("2017.05.07", "13.55", "北京市朝阳区酒仙桥公司 已签收 签收人：邮件收发章"))
      dataBeen!!.add(DataBean("2017.05.07", "06.55", "北京市朝阳区酒仙桥公司 派件中"))
      dataBeen!!.add(DataBean("2017.05.07", "05.55", "北京市朝阳区酒仙桥 已收入"))
      dataBeen!!.add(DataBean("2017.05.06", "22.55", "北京转运中心 已发出，下一站 北京朝阳区大中华区"))
      dataBeen!!.add(DataBean("2017.05.06", "21.55", "北京转运中心 已收入"))
    }

    fun getDataBeen(): List<DataBean>? {
      return dataBeen
    }
  }
}
