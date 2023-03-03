package com.c.cpayid.feature.engine;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DICKY <akbar.attijani@gmail.com>
 */

public class ApplicationVariable {

    public static String applicationId;
    public static String serverId;

    public final static String[] applicationIds = { "com.c.arareload", "com.c.atmpay", "com.c.aka", "com.c.allpayment", "com.c.arkareload", "com.c.arrifaireload", "com.c.arsus", "com.c.ayureload", "com.c.mitrabumdes",
            "com.c.barokah", "com.c.bintangtimur","com.c.boniang", "com.c.cflazz", "com.c.deltakomunika", "com.c.kweni", "com.c.dompetabata", "com.c.dompetali", "com.c.dompetbanjargondang",
            "com.c.dompetyoga", "com.c.donireload", "com.c.afriddonireload", "com.c.elviro", "com.c.fanreload", "com.c.gtr", "com.c.gadutjaya", "com.c.gayoreload", "com.c.hari",
            "com.c.harja", "com.c.indicash", "com.c.jrealod", "com.c.jsstore", "com.c.jendralpay", "com.c.krealod", "com.c.lisatra", "com.c.loketpulsa", "com.c.mahara", "com.c.mitracsoftware",
            "com.c.MultiPayment", "com.c.muslimreload", "com.c.nadhifa", "com.c.nadyareload", "com.c.nakula", "com.c.ncp", "com.c.ozpay", "com.c.rcf", "com.c.restuserver", "com.c.anatakarya", "com.setiareload",
            "com.c.metrireload", "com.c.trbrealod", "com.c.temanpulsa", "com.c.tretan", "com.c.turereload", "com.c.vjreload"};

    public static String getServerId() {
        if (applicationId != null) {
            return serverIds.get(applicationId);
        }

        return null;
    }

    public final static Map<String, String> applicationName = new LinkedHashMap<String, String>() {{
        put("com.c.arareload", "Ara Reload");
        put("com.c.atmpay", "ATM Pay");
        put("com.c.aka", "Aka Reload");
        put("com.c.allpayment", "All Payment");
        put("com.c.arkareload", "Arka Reload");
        put("com.c.arrifaireload", "Arrfai Reload");
        put("com.c.arsus", "Arsus Store");
        put("com.c.ayureload", "Ayu Reload");
        put("com.c.mitrabumdes", "Mitra Bumdes");
        put("com.c.barokah", "Barokah Reload");
        put("com.c.bintangtimur", "Bintang Timur");
        put("com.c.boniang", "Boniang Store");
        put("com.c.cflazz", "C Flazz");
        put("com.c.deltakomunika", "Delta Komunika");
        put("com.c.kweni", "Doi Payment");
        put("com.c.dompetabata", "Dompet Abata");
        put("com.c.dompetali", "Dompet Ali");
        put("com.c.dompetbanjargondang", "Dompet Banjar Gondang");
        put("com.c.dompetyoga", "Dompet Yoga");
        put("com.c.donireload", "Doni Reload");
        put("com.c.afriddonireload", "Donie Reload");
        put("com.c.elviro", "Elvaro Celullar");
        put("com.c.fanreload", "Fan Reload");
        put("com.c.gtr", "GTR Pulsa");
        put("com.c.gadutjaya", "Gadut Jaya");
        put("com.c.gayoreload", "Gayo Reload");
        put("com.c.hari", "Hari Payment");
        put("com.c.harja", "Harja Celluler");
        put("com.c.indicash", "Indicash Mobile");
        put("com.c.jrealod", "JR Reload");
        put("com.c.jsstore", "JS Store");
        put("com.c.jendralpay", "Jendral Pay");
        put("com.c.krealod", "K-Reload");
        put("com.c.lisatra", "Lisatra Pandawa Cell");
        put("com.c.loketpulsa", "Loket Pulsa");
        put("com.c.mahara", "Mahara Reload");
        put("com.c.mitracsoftware", "Mitra C-Software");
        put("com.c.MultiPayment", "Multi Payment");
        put("com.c.muslimreload", "Muslim Reload");
        put("com.c.nadhifa", "Nadhifa Payment");
        put("com.c.nadyareload", "Nadya Reload");
        put("com.c.nakula", "Nakula Reload");
        put("com.c.ncp", "Nur Cell Payment");
        put("com.c.ozpay", "OZPAY");
        put("com.c.rcf", "RCF Pulsa");
        put("com.c.restuserver", "Restu Server Pulsa");
        put("com.c.anatakarya", "Ridho Bae");
        put("com.setiareload", "Setia Reload");
        put("com.c.metrireload", "Sienfast");
        put("com.c.trbrealod", "TRB Reload");
        put("com.c.temanpulsa", "Teman Pulsa");
        put("com.c.tretan", "Tretan Payment");
        put("com.c.turereload", "Ture Reload");
        put("com.c.vjreload", "VJ Refil");
        put("com.c.ptmufi", "PT MUFI CAKRA SEMESTA");
        put("com.c.avatar", "Avatar Payment");
        put("com.c.someslink", "SomesLink");
        put("com.c.orie", "Orie Fastronik");
        put("com.c.konterpulsa", "Konter Pulsa");
        put("com.c.payovi", "Payovi");
        put("com.c.ibnu", "Ibnu Shop");
        put("com.c.rashel", "Rashel Payment");
        put("com.c.bomen", "Bomen Cell");
        put("com.c.fast", "Fast Payment");
        put("com.c.agenpay", "AgenPay Flazz");
        put("com.c.faizpay", "Faiz Travel");
        put("com.c.wnipay", "WNI Pay");
        put("com.c.hamdalah", "Hamdalah Pay");
        put("com.c.blantik", "Blantik Celluler");
        put("com.c.voucherdatacs", "Voucher Data");
        put("com.c.tigaputra", "Tiga Putra Reload");
        put("com.c.nonitiket", "NONITIKET");
        put("com.c.albadar", "Al-Badar");
        put("com.c.startuppulsa", "Startup Pulsa");
        put("com.c.visapay", "Visa Pay");
    }};

    public final static Map<String, String> serverIds = new LinkedHashMap<String, String>() {{
        put("com.c.arareload", "SRVID00000022");
        put("com.c.atmpay", "SRVID00000075");
        put("com.c.aka", "SRVID00000067");
        put("com.c.allpayment", "SRVID00000050");
        put("com.c.arkareload", "SRVID00000025");
        put("com.c.arrifaireload", "SRVID00000007");
        put("com.c.arsus", "SRVID00000037");
        put("com.c.ayureload", "SRVID00000047");
        put("com.c.mitrabumdes", "SRVID00000053");
        put("com.c.barokah", "SRVID00000061");
        put("com.c.bintangtimur", "SRVID00000068");
        put("com.c.boniang", "SRVID00000045");
        put("com.c.cflazz", "SRVID00000028");
        put("com.c.deltakomunika", "SRVID00000040");
        put("com.c.kweni", "SRVID00000042");
        put("com.c.dompetabata", "SRVID00000001");
        put("com.c.dompetali", "SRVID00000026");
        put("com.c.dompetbanjargondang", "Dompet Banjar Gondang");
        put("com.c.dompetyoga", "SRVID00000059");
        put("com.c.donireload", "Doni Reload");
        put("com.c.afriddonireload", "Donie Reload");
        put("com.c.elviro", "SRVID00000048");
        put("com.c.fanreload", "Fan Reload");
        put("com.c.gtr", "SRVID00000060");
        put("com.c.gadutjaya", "SRVID00000021");
        put("com.c.gayoreload", "SRVID00000051");
        put("com.c.hari", "SRVID00000070");
        put("com.c.harja", "SRVID00000055");
        put("com.c.indicash", "SRVID00000065");
        put("com.c.jrealod", "SRVID00000041");
        put("com.c.jsstore", "JS Store");
        put("com.c.jendralpay", "SRVID00000044");
        put("com.c.krealod", "SRVID00000016");
        put("com.c.lisatra", "SRVID00000063");
        put("com.c.loketpulsa", "Loket Pulsa");
        put("com.c.mahara", "SRVID00000058");
        put("com.c.mitracsoftware", "Mitra C-Software");
        put("com.c.MultiPayment", "SRVID00000031");
        put("com.c.muslimreload", "Muslim Reload");
        put("com.c.nadhifa", "SRVID00000064");
        put("com.c.nadyareload", "Nadya Reload");
        put("com.c.nakula", "SRVID00000046");
        put("com.c.ncp", "SRVID00000035");
        put("com.c.ozpay", "SRVID00000056");
        put("com.c.rcf", "SRVID00000069");
        put("com.c.restuserver", "Restu Server Pulsa");
        put("com.c.anatakarya", "Ridho Bae");
        put("com.setiareload", "SRVID00000004");
        put("com.c.metrireload", "Sienfast");
        put("com.c.trbrealod", "SRVID00000029");
        put("com.c.temanpulsa", "SRVID00000052");
        put("com.c.tretan", "SRVID00000066");
        put("com.c.turereload", "SRVID00000036");
        put("com.c.vjreload", "VJ Refil");
        put("com.c.ptmufi", "SRVID00000086");
        put("com.c.avatar", "SRVID00000085");
        put("com.c.someslink", "SRVID00000084");
        put("com.c.orie", "SRVID00000083");
        put("com.c.konterpulsa", "SRVID00000082");
        put("com.c.payovi", "SRVID00000081");
        put("com.c.ibnu", "SRVID00000080");
        put("com.c.rashel", "SRVID00000079");
        put("com.c.bomen", "SRVID00000078");
        put("com.c.fast", "SRVID00000077");
        put("com.c.agenpay", "SRVID00000076");
        put("com.c.faizpay", "SRVID00000074");
        put("com.c.wnipay", "SRVID00000073");
        put("com.c.hamdalah", "SRVID00000072");
        put("com.c.blantik", "SRVID00000071");
        put("com.c.voucherdatacs", "SRVID00000062");
        put("com.c.tigaputra", "SRVID00000057");
        put("com.c.nonitiket", "SRVID00000049");
        put("com.c.startuppulsa", "SRVID00000039");
        put("com.c.visapay", "SRVID00000087");
    }};

    public static Map<String, String> lottieUtl = new LinkedHashMap<String, String>() {{
        put("com.c.arareload", "https://assets4.lottiefiles.com/packages/lf20_3nbg0xrl.json");
        put("com.c.atmpay", "https://assets3.lottiefiles.com/private_files/lf30_ykdoon9j.json");
        put("com.c.aka", "https://assets2.lottiefiles.com/packages/lf20_Yf29Ko.json");
        put("com.c.allpayment", "https://assets2.lottiefiles.com/packages/lf20_gap15l60.json");
        put("com.c.arkareload", "https://assets10.lottiefiles.com/packages/lf20_dkz94xcg.json");
        put("com.c.arrifaireload", "https://assets8.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.arsus", "https://assets6.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.ayureload", "https://assets9.lottiefiles.com/packages/lf20_Stt1R6.json");
        put("com.c.mitrabumdes", "https://assets5.lottiefiles.com/packages/lf20_x9pEKm.json");
        put("com.c.barokah", "https://assets10.lottiefiles.com/packages/lf20_pcyqh4qg.json");
        put("com.c.bintangtimur", "https://assets4.lottiefiles.com/packages/lf20_gap15l60.json");
        put("com.c.boniang", "https://assets8.lottiefiles.com/packages/lf20_ctopYC.json");
        put("com.c.cflazz", "https://assets10.lottiefiles.com/packages/lf20_dkz94xcg.json");
        put("com.c.deltakomunika", "https://assets9.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.kweni", "https://assets7.lottiefiles.com/packages/lf20_gbfwtkzw.json");
        put("com.c.dompetabata", "https://assets4.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.dompetali", null);
        put("com.c.dompetbanjargondang", null);
        put("com.c.dompetyoga", null);
        put("com.c.donireload", null);
        put("com.c.afriddonireload", null);
        put("com.c.elviro", "https://assets4.lottiefiles.com/packages/lf20_pcyqh4qg.json");
        put("com.c.fanreload", null);
        put("com.c.gtr", "https://assets2.lottiefiles.com/private_files/lf30_slenfa7o.json");
        put("com.c.gadutjaya", "https://assets10.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.gayoreload", "https://assets2.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.hari", "https://assets10.lottiefiles.com/packages/lf20_9xbnvzyh.json");
        put("com.c.harja", null);
        put("com.c.indicash", "https://assets6.lottiefiles.com/private_files/lf30_6miolmlf.json");
        put("com.c.jrealod", "https://assets3.lottiefiles.com/packages/lf20_poqmycwy.json");
        put("com.c.jsstore", null);
        put("com.c.jendralpay", "https://assets8.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.krealod", null);
        put("com.c.lisatra", null);
        put("com.c.loketpulsa", null);
        put("com.c.mahara", "https://assets5.lottiefiles.com/private_files/lf30_ltjkg0de.json");
        put("com.c.mitracsoftware", null);
        put("com.c.MultiPayment", null);
        put("com.c.muslimreload", null);
        put("com.c.nadhifa", "https://assets7.lottiefiles.com/packages/lf20_u3u9pfsc.json");
        put("com.c.nadyareload", null);
        put("com.c.nakula", null);
        put("com.c.ncp", "https://assets7.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.ozpay", "https://assets3.lottiefiles.com/packages/lf20_gbfwtkzw.json");
        put("com.c.rcf", "https://assets8.lottiefiles.com/private_files/lf30_b0iey3ml.json");
        put("com.c.restuserver", "https://assets8.lottiefiles.com/packages/lf20_ctopYC.json");
        put("com.c.anatakarya", null);
        put("com.setiareload", "https://assets8.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.metrireload", null);
        put("com.c.trbrealod", null);
        put("com.c.temanpulsa", "https://assets5.lottiefiles.com/private_files/lf30_rdnblpgy.json");
        put("com.c.tretan", "https://assets10.lottiefiles.com/packages/lf20_qjosmr4w.json");
        put("com.c.turereload", "https://assets2.lottiefiles.com/packages/lf20_rwq6ciql.json");
        put("com.c.vjreload", null);
        put("com.c.ptmufi", "https://assets4.lottiefiles.com/packages/lf20_pqtbhoqo.json");
        put("com.c.avatar", "https://assets7.lottiefiles.com/packages/lf20_PH5rXo.json");
        put("com.c.someslink", "https://assets2.lottiefiles.com/packages/lf20_eihovywu.json");
        put("com.c.orie", "https://assets3.lottiefiles.com/packages/lf20_yyytgjwe.json");
        put("com.c.konterpulsa", "https://assets6.lottiefiles.com/private_files/lf30_b0iey3ml.json");
        put("com.c.payovi", "https://assets3.lottiefiles.com/packages/lf20_poqmycwy.json");
        put("com.c.ibnu", "https://assets2.lottiefiles.com/packages/lf20_m2igjaux.json");
        put("com.c.rashel", "https://assets3.lottiefiles.com/packages/lf20_ddedjbwt.json");
        put("com.c.bomen", "https://assets6.lottiefiles.com/packages/lf20_7qmf7wc8.json");
        put("com.c.fast", "https://assets2.lottiefiles.com/private_files/lf30_rdnblpgy.json");
        put("com.c.agenpay", "https://assets10.lottiefiles.com/private_files/lf30_b0iey3ml.json");
        put("com.c.faizpay", "https://assets3.lottiefiles.com/packages/lf20_9xbnvzyh.json");
        put("com.c.wnipay", "https://assets1.lottiefiles.com/packages/lf20_nrzmbfyy.json");
        put("com.c.hamdalah", "https://assets6.lottiefiles.com/packages/lf20_nwgje0cj.json");
        put("com.c.blantik", "https://assets1.lottiefiles.com/packages/lf20_gap15l60.json");
        put("com.c.voucherdatacs", "https://assets7.lottiefiles.com/packages/lf20_a2chheio.json");
        put("com.c.tigaputra", "https://assets6.lottiefiles.com/packages/lf20_blzaxbdh.json");
        put("com.c.nonitiket", "https://assets2.lottiefiles.com/packages/lf20_6itgt9qk.json");
        put("com.c.startuppulsa", "https://assets9.lottiefiles.com/packages/lf20_gap15l60.json");
        put("com.c.visapay", "https://assets9.lottiefiles.com/packages/lf20_gap15l60.json");
    }};

    public static String nameChanger() {
        return applicationName.get(ApplicationVariable.applicationId);
    }
}
