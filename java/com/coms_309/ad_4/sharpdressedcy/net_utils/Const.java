package com.coms_309.ad_4.sharpdressedcy.net_utils;

public class Const {
    // Pre-URL
//    public static final String PREREQ = "localhost:8081/";
     public static final String PREREQ = "http://proj309-ad-04.misc.iastate.edu:8080/";
    // PROJECT
    public static final String URL_TODAY_CLOTHING = PREREQ + "dailyOutfit";
    public static final String URL_SUBMIT_ARTICLE = "/newclothing/";
    public static final String URL_SUBMIT_CLOSET  = PREREQ + "addCloset";
    public static final String URL_LOGIN          = PREREQ + "validateUser/";
    public static final String URL_CLOSET         = "/closet/";
    public static final String URL_ALL_USERS      = "/users";
    public static final String URL_SEARCH         = "/search/";
    public static final String URL_SINGLE_ITEM    = "/item/";
    public static final String URL_ADD_PERMISSION = "/addPrivacyMember/";
    public static final String URL_SUB_PERMISSION = "/removePrivacyMember/";
    public static final String URL_ADD_USER       = PREREQ + "newuser/";
    public static final String URL_OUTFIT         = "/outfit/";
}
