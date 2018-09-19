package com.system.translationpen.exchangerate;

import com.system.translationpen.R;
import com.system.translationpen.exchangerate.until.LRU;

import java.util.HashMap;
import java.util.List;

public class Constant {


    public static HashMap<String,Integer> map=new HashMap<>();
    /*第一个默认的*/
    public static HashMap<Integer,String> firstPage=new HashMap<>();

    public static boolean isFirst=true;

    public static LRU<String,String> lru=new LRU<>();
    public static void init(){
        map.put("USD", R.drawable.usd);
        map.put("CNY", R.drawable.cny);
        map.put("HKD", R.drawable.hkd);
        map.put("JPY", R.drawable.jpy);
        map.put("TWD", R.drawable.twd);
        map.put("EUR", R.drawable.eur);
        map.put("GBP", R.drawable.gbp);
        map.put("SEK", R.drawable.sek);
        map.put("RUB", R.drawable.rub);
        map.put("USD", R.drawable.usd);
        map.put("CAD", R.drawable.cad);
        /*asa 图片没有*/
        map.put("ASA", R.drawable.cad);
        map.put("CUP", R.drawable.cup);
        map.put("AUD", R.drawable.aud);
        map.put("NZD", R.drawable.nzd);
        map.put("ZAR", R.drawable.zar);
        map.put("EGP", R.drawable.egp);
        map.put("XAF", R.drawable.xaf);

        map.put("AED", R.drawable.aed);
        map.put("AFN", R.drawable.afn);
        map.put("AZN", R.drawable.azn);
        map.put("BDT", R.drawable.bdt);
        map.put("BHD", R.drawable.bhd);
        map.put("BND", R.drawable.bnd);
        map.put("BTN", R.drawable.btn);
        map.put("CNY", R.drawable.cny);
        map.put("HKD", R.drawable.hkd);
        map.put("IDR", R.drawable.idr);
        map.put("ILS", R.drawable.ils);
        map.put("INR", R.drawable.inr);
        map.put("IQD", R.drawable.iqd);
        map.put("IRR", R.drawable.irr);
        map.put("JOD", R.drawable.jod);
        map.put("JPY", R.drawable.jpy);
        map.put("KGS", R.drawable.kgs);
        map.put("KHR", R.drawable.khr);
        map.put("KPW", R.drawable.kpw);
        map.put("KRW", R.drawable.krw);
        map.put("KWD", R.drawable.kwd);
        map.put("KZT", R.drawable.kzt);
        map.put("LAK", R.drawable.lak);
        map.put("LKR", R.drawable.lkr);
        map.put("MMK", R.drawable.mmk);
        map.put("MNT", R.drawable.mnt);
        map.put("MOP", R.drawable.mop);
        map.put("MVR", R.drawable.mvr);
        map.put("MYR", R.drawable.myr);
        map.put("NPR", R.drawable.npr);
        map.put("OMR", R.drawable.omr);
        map.put("PHP", R.drawable.php);
        map.put("PKR", R.drawable.pkr);
        map.put("QAR", R.drawable.qar);
        map.put("SAR", R.drawable.sar);
        map.put("SGD", R.drawable.sgd);
        map.put("SYP", R.drawable.syp);
        map.put("THB", R.drawable.thb);
        map.put("TJS", R.drawable.tjs);
        map.put("TMT", R.drawable.tmt);
        map.put("TRY", R.drawable.trytry);
        map.put("TWD", R.drawable.twd);
        map.put("UZS", R.drawable.uzs);
        map.put("VND", R.drawable.vnd);
        map.put("YER", R.drawable.yer);

        map.put("BYR", R.drawable.byr);
        map.put("CHF", R.drawable.chf);
        map.put("CZK", R.drawable.czk);
        map.put("DKK", R.drawable.dkk);
        map.put("EUR", R.drawable.eur);
        map.put("GBP", R.drawable.gbp);
        map.put("GEL", R.drawable.gel);
        map.put("HRK", R.drawable.hrk);
        map.put("HUF", R.drawable.huf);
        map.put("ISK", R.drawable.isk);
        map.put("LBP", R.drawable.lbp);
        map.put("LTL", R.drawable.ltl);
        map.put("LVL", R.drawable.lvl);
        map.put("MAD", R.drawable.mad);
        map.put("MDL", R.drawable.mdl);
        map.put("MKD", R.drawable.mkd);
        map.put("NOK", R.drawable.nok);
        map.put("PLN", R.drawable.pln);
        map.put("RON", R.drawable.ron);
        map.put("RSD", R.drawable.rsd);
        map.put("SEK", R.drawable.sek);
        map.put("UAH", R.drawable.uah);


        map.put("ASA", R.drawable.cad);
        map.put("AWG", R.drawable.awg);
        map.put("BBD", R.drawable.bbd);
        map.put("BMD", R.drawable.bmd);
        map.put("BOB", R.drawable.bob);
        map.put("BRL", R.drawable.brl);
        map.put("BSD", R.drawable.bsd);
        map.put("BZD", R.drawable.bzd);
        map.put("CAD", R.drawable.cad);
        map.put("CLP", R.drawable.clp);
        map.put("COP", R.drawable.cop);
        map.put("CRC", R.drawable.crc);
        map.put("CUP", R.drawable.cup);
        map.put("DOP", R.drawable.dop);
        map.put("FKP", R.drawable.fkp);
        map.put("GTQ", R.drawable.gtq);
        map.put("GYD", R.drawable.gyd);
        map.put("HNL", R.drawable.hnl);
        map.put("HTG", R.drawable.htg);
        map.put("JMD", R.drawable.jmd);
        map.put("KYD", R.drawable.kyd);
        map.put("MXN", R.drawable.mxn);
        map.put("NIO", R.drawable.nio);
        map.put("PAB", R.drawable.pab);
        map.put("PEN", R.drawable.pen);
        map.put("PYG", R.drawable.pyg);
        map.put("SRD", R.drawable.srd);
        map.put("SVC", R.drawable.svc);
        map.put("TTD", R.drawable.ttd);
        map.put("USD", R.drawable.usd);
        map.put("UYU", R.drawable.uyu);
        map.put("VEF", R.drawable.vef);
        map.put("XCD", R.drawable.xcd);


        map.put("AUD", R.drawable.aud);
        map.put("FJD", R.drawable.fjd);
        map.put("NZD", R.drawable.nzd);
        map.put("PGK", R.drawable.pgk);
        map.put("SBD", R.drawable.sbd);
        map.put("TOP", R.drawable.top);
        map.put("VUV", R.drawable.vuv);
        map.put("WST", R.drawable.wst);
        map.put("XPF", R.drawable.xpf);


        map.put("AOA", R.drawable.aoa);
        map.put("BIF", R.drawable.bif);
        map.put("BWP", R.drawable.bwp);
        map.put("CDF", R.drawable.cdf);
        map.put("CVE", R.drawable.cve);
        map.put("DJF", R.drawable.djf);
        map.put("DZD", R.drawable.dzd);
        map.put("EGP", R.drawable.egp);
        map.put("ERN", R.drawable.ern);
        map.put("ETB", R.drawable.etb);
        map.put("GHS", R.drawable.ghs);
        map.put("GIP", R.drawable.gip);
        map.put("GMD", R.drawable.gmd);
        map.put("GNF", R.drawable.gnf);
        map.put("KES", R.drawable.kes);
        map.put("KMF", R.drawable.kmf);
        map.put("LRD", R.drawable.lrd);
        map.put("LSL", R.drawable.lsl);
        map.put("LYD", R.drawable.lyd);
        map.put("MGA", R.drawable.mga);
        map.put("MRO", R.drawable.mro);
        map.put("MUR", R.drawable.mur);
        map.put("MWK", R.drawable.mwk);
        map.put("MZN", R.drawable.mzn);
        map.put("NAD", R.drawable.nad);
        map.put("NGN", R.drawable.ngn);
        map.put("RWF", R.drawable.rwf);
        map.put("SCR", R.drawable.scr);
        /*缺土*/
        map.put("SDF", R.drawable.sdg);
        /*缺土*/
        map.put("SHP", R.drawable.sdg);
        map.put("SLL", R.drawable.sll);
        map.put("SOS", R.drawable.sos);
        map.put("STD", R.drawable.std);
        map.put("SZL", R.drawable.szl);
        map.put("TND", R.drawable.tnd);
        map.put("TZS", R.drawable.tzs);
        map.put("UGX", R.drawable.ugx);
        map.put("XAF", R.drawable.xaf);
        map.put("XOF", R.drawable.xof);
        map.put("ZMW", R.drawable.zmw);
        map.put("ZWL", R.drawable.zwl);
        map.put("AMD", R.drawable.amd);
        map.put("BGN", R.drawable.bgn);
        map.put("BAM", R.drawable.bam);

        map.put("ANG", R.drawable.ang);
        map.put("ALL", R.drawable.all);

        firstPage.put(0,"USD");
        firstPage.put(1,"CNY");
        firstPage.put(2,"EUR");

        lru.put("USD","USD");
        lru.put("CNY","CNY");
        lru.put("EUR","EUR");
        lru.put("HKD","HKD");
        lru.put("AUD","AUD");
    }
}
