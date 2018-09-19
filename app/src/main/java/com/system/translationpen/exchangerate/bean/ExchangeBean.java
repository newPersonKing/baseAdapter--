package com.system.translationpen.exchangerate.bean;

import java.util.List;

public class ExchangeBean {


    /**
     * type : 亚洲
     * data : [{"curno":"BTN","cname":"不丹努扎姆","ename":"Bhutanese Ngultrum","num":"100","middle":"7007.5","update":"2018-08-20 13:25:34"},{"curno":"UZS","cname":"乌兹别克斯坦索姆","ename":"Uzbekistani Som","num":"100","middle":"777000","update":"2018-08-17 23:53:20"},{"curno":"YER","cname":"也门里亚尔","ename":"Yemeni Rial","num":"100","middle":"24990","update":"2018-08-20 12:04:26"},{"curno":"CNY","cname":"人民币","ename":"Chinese Yuan","num":"100","middle":"685.67","update":"2018-08-20 17:42:02"},{"curno":"ILS","cname":"以色列新谢克尔","ename":"Israeli New Shekel","num":"100","middle":"366.68","update":"2018-08-20 18:39:11"},{"curno":"IQD","cname":"伊拉克第纳尔","ename":"Iraqi Dinar","num":"100","middle":"119000","update":"2018-08-20 12:04:27"},{"curno":"IRR","cname":"伊朗里亚尔","ename":"Iranian Rial","num":"100","middle":"4200000","update":"2018-08-20 12:04:26"},{"curno":"QAR","cname":"卡塔尔里亚尔","ename":"Qatari Riyal","num":"100","middle":"363.98","update":"2018-08-20 13:46:01"},{"curno":"INR","cname":"印度卢比","ename":"Indian Rupee","num":"100","middle":"6969.4","update":"2018-08-20 17:29:11"},{"curno":"IDR","cname":"印度盾","ename":"Indonesian Rupiah","num":"100","middle":"1458500","update":"2018-08-20 17:25:39"},{"curno":"SYP","cname":"叙利亚镑","ename":"Syrian Pound","num":"100","middle":"51498","update":"2018-08-20 12:04:26"},{"curno":"KGS","cname":"吉尔吉斯斯坦索姆","ename":"Kyrgyzstani Som","num":"100","middle":"6943.07","update":"2018-08-17 18:00:11"},{"curno":"KZT","cname":"哈萨克斯坦坚戈","ename":"Kazakhstani Tenge","num":"100","middle":"36107","update":"2018-08-20 18:31:59"},{"curno":"TMT","cname":"土库曼斯坦马纳特","ename":"Turkmenistani Manat","num":"100","middle":"340","update":"2018-08-17 23:39:25"},{"curno":"TRY","cname":"土耳其里拉","ename":"Turkish Lira","num":"100","middle":"608.96","update":"2018-08-20 18:14:14"},{"curno":"TJS","cname":"塔吉克斯坦索莫尼","ename":"Tajikistani Somoni","num":"100","middle":"941.95","update":"2018-08-20 10:00:13"},{"curno":"BDT","cname":"孟加拉塔卡","ename":"Bangladeshi Taka","num":"100","middle":"8375","update":"2018-08-20 17:25:39"},{"curno":"NPR","cname":"尼泊尔卢比","ename":"Nepalese Rupee","num":"100","middle":"11195","update":"2018-08-20 18:19:39"},{"curno":"PKR","cname":"巴基斯坦卢比","ename":"Pakistani Rupee","num":"100","middle":"12336","update":"2018-08-20 16:57:09"},{"curno":"BHD","cname":"巴林第纳尔","ename":"Bahraini Dinar","num":"100","middle":"37.69","update":"2018-08-20 16:10:58"},{"curno":"BND","cname":"文莱元","ename":"Brunei Dollar","num":"100","middle":"137.11","update":"2018-08-20 17:54:12"},{"curno":"LKR","cname":"斯里兰卡卢比","ename":"Sri Lankan Rupee","num":"100","middle":"16045","update":"2018-08-20 17:52:09"},{"curno":"SGD","cname":"新加坡币","ename":"Singapore Dollar","num":"100","middle":"137.14","update":"2018-08-20 17:39:14"},{"curno":"TWD","cname":"新台币","ename":"Taiwan New Dollar","num":"100","middle":"3074.6","update":"2018-08-20 17:44:14"},{"curno":"JPY","cname":"日元","ename":"Japanese Yen","num":"100","middle":"11065.7","update":"2018-08-20 17:44:14"},{"curno":"KPW","cname":"朝鲜元","ename":"North Korean Won","num":"100","middle":"90000","update":"2018-08-20 17:00:00"},{"curno":"KHR","cname":"柬埔寨瑞尔","ename":"Cambodian Riel","num":"100","middle":"400900","update":"2018-08-20 12:04:26"},{"curno":"SAR","cname":"沙特阿拉伯里亚尔","ename":"Saudi Arabian Riyal","num":"100","middle":"375.03","update":"2018-08-20 17:00:21"},{"curno":"THB","cname":"泰铢","ename":"Thai Baht","num":"100","middle":"3303","update":"2018-08-20 18:04:12"},{"curno":"HKD","cname":"港币","ename":"Hong Kong Dollar","num":"100","middle":"784.96","update":"2018-08-20 17:44:14"},{"curno":"MOP","cname":"澳门币","ename":"Macau Pataca","num":"100","middle":"808.48","update":"2018-08-20 17:25:00"},{"curno":"KWD","cname":"科威特第纳尔","ename":"Kuwaiti Dinar","num":"100","middle":"30.32","update":"2018-08-20 14:08:58"},{"curno":"JOD","cname":"约旦第纳尔","ename":"Jordanian Dinar","num":"100","middle":"70.85","update":"2018-08-20 18:37:17"},{"curno":"MMK","cname":"缅元","ename":"Burmese Kyat","num":"100","middle":"151400","update":"2018-08-20 16:27:03"},{"curno":"LAK","cname":"老挝普斯","ename":"Lao Kip","num":"100","middle":"849400","update":"2018-08-20 15:13:44"},{"curno":"PHP","cname":"菲律宾比索","ename":"Philippine Peso","num":"100","middle":"5340","update":"2018-08-20 18:33:43"},{"curno":"MNT","cname":"蒙古图格里克","ename":"Mongolian Tughrik","num":"100","middle":"246100","update":"2018-08-20 12:04:27"},{"curno":"VND","cname":"越南盾","ename":"Vietnamese Dong","num":"100","middle":"2329200","update":"2018-08-20 12:04:26"},{"curno":"AZN","cname":"阿塞拜疆马纳特","ename":"Azerbaijani Manat","num":"100","middle":"169.95","update":"2018-08-20 14:00:04"},{"curno":"AFN","cname":"阿富汗尼","ename":"Afghan Afghani","num":"100","middle":"7240","update":"2018-08-20 12:04:43"},{"curno":"OMR","cname":"阿曼里亚尔","ename":"Omani Rial","num":"100","middle":"38.48","update":"2018-08-20 14:32:06"},{"curno":"AED","cname":"阿联酋迪拉姆","ename":"United Arab Emirates Dirham","num":"100","middle":"367.27","update":"2018-08-20 15:18:15"},{"curno":"KRW","cname":"韩元","ename":"South Korean Won","num":"100","middle":"112247","update":"2018-08-20 18:39:08"},{"curno":"MVR","cname":"马尔代夫拉菲亚","ename":"Mauritian Rupee","num":"100","middle":"1557","update":"2018-08-20 12:04:26"},{"curno":"MYR","cname":"马来西亚吉特","ename":"Malaysian Ringgit","num":"100","middle":"409.91","update":"2018-08-20 17:52:09"}]
     */

    private String type;
    private List<ExChangeMultipleItem> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ExChangeMultipleItem> getData() {
        return data;
    }

    public void setData(List<ExChangeMultipleItem> data) {
        this.data = data;
    }
}
