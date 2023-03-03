package com.c.cpayid.feature.Model;

import java.util.ArrayList;

public class MSubMenu {
    String name,icon,link,urutan,sub,status;
    ArrayList<MSubMenu> menu;

    public ArrayList<MSubMenu> getMenu() {
        return menu;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getLink() {
        return link;
    }

    public String getUrutan() {
        return urutan;
    }

    public String getSub() {
        return sub;
    }

    public String getStatus() {
        return status;
    }
}
