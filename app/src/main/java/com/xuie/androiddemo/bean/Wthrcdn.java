package com.xuie.androiddemo.bean;

import java.util.List;

/**
 * Created by xuie on 16-6-8.
 */
public class Wthrcdn {

    /**
     * desc : OK
     * status : 1000
     * data : {"wendu":"26","ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","forecast":[{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"中雨","low":"低温 24℃","date":"8日星期三"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"中到大雨","low":"低温 24℃","date":"9日星期四"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 29℃","type":"大雨","low":"低温 25℃","date":"10日星期五"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 28℃","type":"大暴雨","low":"低温 25℃","date":"11日星期六"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"阵雨","low":"低温 26℃","date":"12日星期天"}],"yesterday":{"fl":"微风","fx":"无持续风向","high":"高温 32℃","type":"阵雨","low":"低温 27℃","date":"7日星期二"},"aqi":"21","city":"深圳"}
     */

    private String desc;
    private int status;
    /**
     * wendu : 26
     * ganmao : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
     * forecast : [{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"中雨","low":"低温 24℃","date":"8日星期三"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"中到大雨","low":"低温 24℃","date":"9日星期四"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 29℃","type":"大雨","low":"低温 25℃","date":"10日星期五"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 28℃","type":"大暴雨","low":"低温 25℃","date":"11日星期六"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"阵雨","low":"低温 26℃","date":"12日星期天"}]
     * yesterday : {"fl":"微风","fx":"无持续风向","high":"高温 32℃","type":"阵雨","low":"低温 27℃","date":"7日星期二"}
     * aqi : 21
     * city : 深圳
     */

    private DataEntity data;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String wendu;
        private String ganmao;
        /**
         * fl : 微风
         * fx : 无持续风向
         * high : 高温 32℃
         * type : 阵雨
         * low : 低温 27℃
         * date : 7日星期二
         */

        private YesterdayEntity yesterday;
        private String aqi;
        private String city;
        /**
         * fengxiang : 无持续风向
         * fengli : 微风级
         * high : 高温 30℃
         * type : 中雨
         * low : 低温 24℃
         * date : 8日星期三
         */

        private List<Weather> forecast;

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayEntity getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayEntity yesterday) {
            this.yesterday = yesterday;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Weather> getWeather() {
            return forecast;
        }

        public void setWeather(List<Weather> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayEntity {
            private String fl;
            private String fx;
            private String high;
            private String type;
            private String low;
            private String date;

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

    }
}
