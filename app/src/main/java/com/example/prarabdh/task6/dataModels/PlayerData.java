package com.example.prarabdh.task6.dataModels;

import android.content.Context;
import android.util.ArrayMap;

import com.example.prarabdh.task6.dataModels.LeaderboardDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {

    public static String udrUserId = "";
    public static String udrAchievementsUnlocked = "";
    public static String udrAvatar = "";
    public static String udrCategoriesUnlocked = "";
    public static String udrEmail = "";
    public static String udrLoss = "";
    public static int udrPoints = 0;
    public static Map<String, String> udrQuestionsAttempted = new HashMap<String, String>();
    public static String udrUserName = "";
    public static String udrWin = "";
    public static  int[] pointsToUnlock = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    public static Context context11 = null;
    // for leaderboard
    public static ArrayList<LeaderboardDataModel> datasetLeaderboard = new ArrayList<LeaderboardDataModel>();
    public static LeaderboardDataModel currentusermodel;
    public static int l1called=0;
}
