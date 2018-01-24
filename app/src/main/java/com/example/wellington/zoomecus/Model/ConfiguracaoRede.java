package com.example.wellington.zoomecus.Model;

import android.support.annotation.NonNull;

/**
 * Created by wellington on 23/01/18.
 */

public class ConfiguracaoRede implements Comparable <ConfiguracaoRede> {

    int level;
    String bssid;
    String rssid;

    public String getRssid() {
        return rssid;
    }

    public void setRssid(String rssid) {
        this.rssid = rssid;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }


    @Override
    public int compareTo(ConfiguracaoRede configuracaoRede) {

        int conparaLevel = ((ConfiguracaoRede)configuracaoRede).getLevel();

        return this.level - conparaLevel;
    }
}
