package com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper;

import android.content.Context;
import android.graphics.Color;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

import java.util.ArrayList;

/**
 * Created by charl on 16/09/2016.
 */
public class ColorHelper {

    public static int getColorResourceByName(String nameColor) {
        switch (nameColor) {

            case "red_50":
                return R.color.red_50;
            case "red_100":
                return R.color.red_100;
            case "red_200":
                return R.color.red_200;
            case "red_300":
                return R.color.red_300;
            case "red_400":
                return R.color.red_400;
            case "red_500":
                return R.color.red_500;
            case "red_600":
                return R.color.red_600;
            case "red_700":
                return R.color.red_700;
            case "red_800":
                return R.color.red_800;
            case "red_900":
                return R.color.red_900;
            case "red_A100":
                return R.color.red_A100;
            case "red_A200":
                return R.color.red_A200;
            case "red_A400":
                return R.color.red_A400;
            case "red_A700":
                return R.color.red_A700;

            case "deep_purple_50":
                return R.color.deep_purple_50;
            case "deep_purple_100":
                return R.color.deep_purple_100;
            case "deep_purple_200":
                return R.color.deep_purple_200;
            case "deep_purple_300":
                return R.color.deep_purple_300;
            case "deep_purple_400":
                return R.color.deep_purple_400;
            case "deep_purple_500":
                return R.color.deep_purple_500;
            case "deep_purple_600":
                return R.color.deep_purple_600;
            case "deep_purple_700":
                return R.color.deep_purple_700;
            case "deep_purple_800":
                return R.color.deep_purple_800;
            case "deep_purple_900":
                return R.color.deep_purple_900;
            case "deep_purple_A100":
                return R.color.deep_purple_A100;
            case "deep_purple_A200":
                return R.color.deep_purple_A200;
            case "deep_purple_A400":
                return R.color.deep_purple_A400;
            case "deep_purple_A700":
                return R.color.deep_purple_A700;

            case "light_blue_50":
                return R.color.light_blue_50;
            case "light_blue_100":
                return R.color.light_blue_100;
            case "light_blue_200":
                return R.color.light_blue_200;
            case "light_blue_300":
                return R.color.light_blue_300;
            case "light_blue_400":
                return R.color.light_blue_400;
            case "light_blue_500":
                return R.color.light_blue_500;
            case "light_blue_600":
                return R.color.light_blue_600;
            case "light_blue_700":
                return R.color.light_blue_700;
            case "light_blue_800":
                return R.color.light_blue_800;
            case "light_blue_900":
                return R.color.light_blue_900;
            case "light_blue_A100":
                return R.color.light_blue_A100;
            case "light_blue_A200":
                return R.color.light_blue_A200;
            case "light_blue_A400":
                return R.color.light_blue_A400;
            case "light_blue_A700":
                return R.color.light_blue_A700;

            case "green_50":
                return R.color.green_50;
            case "green_100":
                return R.color.green_100;
            case "green_200":
                return R.color.green_200;
            case "green_300":
                return R.color.green_300;
            case "green_400":
                return R.color.green_400;
            case "green_500":
                return R.color.green_500;
            case "green_600":
                return R.color.green_600;
            case "green_700":
                return R.color.green_700;
            case "green_800":
                return R.color.green_800;
            case "green_900":
                return R.color.green_900;
            case "green_A100":
                return R.color.green_A100;
            case "green_A200":
                return R.color.green_A200;
            case "green_A400":
                return R.color.green_A400;
            case "green_A700":
                return R.color.green_A700;

            case "yellow_50":
                return R.color.yellow_50;
            case "yellow_100":
                return R.color.yellow_100;
            case "yellow_200":
                return R.color.yellow_200;
            case "yellow_300":
                return R.color.yellow_300;
            case "yellow_400":
                return R.color.yellow_400;
            case "yellow_500":
                return R.color.yellow_500;
            case "yellow_600":
                return R.color.yellow_600;
            case "yellow_700":
                return R.color.yellow_700;
            case "yellow_800":
                return R.color.yellow_800;
            case "yellow_900":
                return R.color.yellow_900;
            case "yellow_A100":
                return R.color.yellow_A100;
            case "yellow_A200":
                return R.color.yellow_A200;
            case "yellow_A400":
                return R.color.yellow_A400;
            case "yellow_A700":
                return R.color.yellow_A700;

            case "deep_orange_50":
                return R.color.deep_orange_50;
            case "deep_orange_100":
                return R.color.deep_orange_100;
            case "deep_orange_200":
                return R.color.deep_orange_200;
            case "deep_orange_300":
                return R.color.deep_orange_300;
            case "deep_orange_400":
                return R.color.deep_orange_400;
            case "deep_orange_500":
                return R.color.deep_orange_500;
            case "deep_orange_600":
                return R.color.deep_orange_600;
            case "deep_orange_700":
                return R.color.deep_orange_700;
            case "deep_orange_800":
                return R.color.deep_orange_800;
            case "deep_orange_900":
                return R.color.deep_orange_900;
            case "deep_orange_A100":
                return R.color.deep_orange_A100;
            case "deep_orange_A200":
                return R.color.deep_orange_A200;
            case "deep_orange_A400":
                return R.color.deep_orange_A400;
            case "deep_orange_A700":
                return R.color.deep_orange_A700;

            case "blue_grey_50":
                return R.color.blue_grey_50;
            case "blue_grey_100":
                return R.color.blue_grey_100;
            case "blue_grey_200":
                return R.color.blue_grey_200;
            case "blue_grey_300":
                return R.color.blue_grey_300;
            case "blue_grey_400":
                return R.color.blue_grey_400;
            case "blue_grey_500":
                return R.color.blue_grey_500;
            case "blue_grey_600":
                return R.color.blue_grey_600;
            case "blue_grey_700":
                return R.color.blue_grey_700;
            case "blue_grey_800":
                return R.color.blue_grey_800;
            case "blue_grey_900":
                return R.color.blue_grey_900;

            case "pink_50":
                return R.color.pink_50;
            case "pink_100":
                return R.color.pink_100;
            case "pink_200":
                return R.color.pink_200;
            case "pink_300":
                return R.color.pink_300;
            case "pink_400":
                return R.color.pink_400;
            case "pink_500":
                return R.color.pink_500;
            case "pink_600":
                return R.color.pink_600;
            case "pink_700":
                return R.color.pink_700;
            case "pink_800":
                return R.color.pink_800;
            case "pink_900":
                return R.color.pink_900;
            case "pink_A100":
                return R.color.pink_A100;
            case "pink_A200":
                return R.color.pink_A200;
            case "pink_A400":
                return R.color.pink_A400;
            case "pink_A700":
                return R.color.pink_A700;

            case "indigo_50":
                return R.color.indigo_50;
            case "indigo_100":
                return R.color.indigo_100;
            case "indigo_200":
                return R.color.indigo_200;
            case "indigo_300":
                return R.color.indigo_300;
            case "indigo_400":
                return R.color.indigo_400;
            case "indigo_500":
                return R.color.indigo_500;
            case "indigo_600":
                return R.color.indigo_600;
            case "indigo_700":
                return R.color.indigo_700;
            case "indigo_800":
                return R.color.indigo_800;
            case "indigo_900":
                return R.color.indigo_900;
            case "indigo_A100":
                return R.color.indigo_A100;
            case "indigo_A200":
                return R.color.indigo_A200;
            case "indigo_A400":
                return R.color.indigo_A400;
            case "indigo_A700":
                return R.color.indigo_A700;

            case "cyan_50":
                return R.color.cyan_50;
            case "cyan_100":
                return R.color.cyan_100;
            case "cyan_200":
                return R.color.cyan_200;
            case "cyan_300":
                return R.color.cyan_300;
            case "cyan_400":
                return R.color.cyan_400;
            case "cyan_500":
                return R.color.cyan_500;
            case "cyan_600":
                return R.color.cyan_600;
            case "cyan_700":
                return R.color.cyan_700;
            case "cyan_800":
                return R.color.cyan_800;
            case "cyan_900":
                return R.color.cyan_900;
            case "cyan_A100":
                return R.color.cyan_A100;
            case "cyan_A200":
                return R.color.cyan_A200;
            case "cyan_A400":
                return R.color.cyan_A400;
            case "cyan_A700":
                return R.color.cyan_A700;

            case "light_green_50":
                return R.color.light_green_50;
            case "light_green_100":
                return R.color.light_green_100;
            case "light_green_200":
                return R.color.light_green_200;
            case "light_green_300":
                return R.color.light_green_300;
            case "light_green_400":
                return R.color.light_green_400;
            case "light_green_500":
                return R.color.light_green_500;
            case "light_green_600":
                return R.color.light_green_600;
            case "light_green_700":
                return R.color.light_green_700;
            case "light_green_800":
                return R.color.light_green_800;
            case "light_green_900":
                return R.color.light_green_900;
            case "light_green_A100":
                return R.color.light_green_A100;
            case "light_green_A200":
                return R.color.light_green_A200;
            case "light_green_A400":
                return R.color.light_green_A400;
            case "light_green_A700":
                return R.color.light_green_A700;

            case "amber_50":
                return R.color.amber_50;
            case "amber_100":
                return R.color.amber_100;
            case "amber_200":
                return R.color.amber_200;
            case "amber_300":
                return R.color.amber_300;
            case "amber_400":
                return R.color.amber_400;
            case "amber_500":
                return R.color.amber_500;
            case "amber_600":
                return R.color.amber_600;
            case "amber_700":
                return R.color.amber_700;
            case "amber_800":
                return R.color.amber_800;
            case "amber_900":
                return R.color.amber_900;
            case "amber_A100":
                return R.color.amber_A100;
            case "amber_A200":
                return R.color.amber_A200;
            case "amber_A400":
                return R.color.amber_A400;
            case "amber_A700":
                return R.color.amber_A700;

            case "brown_50":
                return R.color.brown_50;
            case "brown_100":
                return R.color.brown_100;
            case "brown_200":
                return R.color.brown_200;
            case "brown_300":
                return R.color.brown_300;
            case "brown_400":
                return R.color.brown_400;
            case "brown_500":
                return R.color.brown_500;
            case "brown_600":
                return R.color.brown_600;
            case "brown_700":
                return R.color.brown_700;
            case "brown_800":
                return R.color.brown_800;
            case "brown_900":
                return R.color.brown_900;

            case "purple_50":
                return R.color.purple_50;
            case "purple_100":
                return R.color.purple_100;
            case "purple_200":
                return R.color.purple_200;
            case "purple_300":
                return R.color.purple_300;
            case "purple_400":
                return R.color.purple_400;
            case "purple_500":
                return R.color.purple_500;
            case "purple_600":
                return R.color.purple_600;
            case "purple_700":
                return R.color.purple_700;
            case "purple_800":
                return R.color.purple_800;
            case "purple_900":
                return R.color.purple_900;
            case "purple_A100":
                return R.color.purple_A100;
            case "purple_A200":
                return R.color.purple_A200;
            case "purple_A400":
                return R.color.purple_A400;
            case "purple_A700":
                return R.color.purple_A700;

            case "blue_50":
                return R.color.blue_50;
            case "blue_100":
                return R.color.blue_100;
            case "blue_200":
                return R.color.blue_200;
            case "blue_300":
                return R.color.blue_300;
            case "blue_400":
                return R.color.blue_400;
            case "blue_500":
                return R.color.blue_500;
            case "blue_600":
                return R.color.blue_600;
            case "blue_700":
                return R.color.blue_700;
            case "blue_800":
                return R.color.blue_800;
            case "blue_900":
                return R.color.blue_900;
            case "blue_A100":
                return R.color.blue_A100;
            case "blue_A200":
                return R.color.blue_A200;
            case "blue_A400":
                return R.color.blue_A400;
            case "blue_A700":
                return R.color.blue_A700;

            case "teal_50":
                return R.color.teal_50;
            case "teal_100":
                return R.color.teal_100;
            case "teal_200":
                return R.color.teal_200;
            case "teal_300":
                return R.color.teal_300;
            case "teal_400":
                return R.color.teal_400;
            case "teal_500":
                return R.color.teal_500;
            case "teal_600":
                return R.color.teal_600;
            case "teal_700":
                return R.color.teal_700;
            case "teal_800":
                return R.color.teal_800;
            case "teal_900":
                return R.color.teal_900;
            case "teal_A100":
                return R.color.teal_A100;
            case "teal_A200":
                return R.color.teal_A200;
            case "teal_A400":
                return R.color.teal_A400;
            case "teal_A700":
                return R.color.teal_A700;

            case "lime_50":
                return R.color.lime_50;
            case "lime_100":
                return R.color.lime_100;
            case "lime_200":
                return R.color.lime_200;
            case "lime_300":
                return R.color.lime_300;
            case "lime_400":
                return R.color.lime_400;
            case "lime_500":
                return R.color.lime_500;
            case "lime_600":
                return R.color.lime_600;
            case "lime_700":
                return R.color.lime_700;
            case "lime_800":
                return R.color.lime_800;
            case "lime_900":
                return R.color.lime_900;
            case "lime_A100":
                return R.color.lime_A100;
            case "lime_A200":
                return R.color.lime_A200;
            case "lime_A400":
                return R.color.lime_A400;
            case "lime_A700":
                return R.color.lime_A700;

            case "orange_50":
                return R.color.orange_50;
            case "orange_100":
                return R.color.orange_100;
            case "orange_200":
                return R.color.orange_200;
            case "orange_300":
                return R.color.orange_300;
            case "orange_400":
                return R.color.orange_400;
            case "orange_500":
                return R.color.orange_500;
            case "orange_600":
                return R.color.orange_600;
            case "orange_700":
                return R.color.orange_700;
            case "orange_800":
                return R.color.orange_800;
            case "orange_900":
                return R.color.orange_900;
            case "orange_A100":
                return R.color.orange_A100;
            case "orange_A200":
                return R.color.orange_A200;
            case "orange_A400":
                return R.color.orange_A400;
            case "orange_A700":
                return R.color.orange_A700;

            case "grey_50":
                return R.color.grey_50;
            case "grey_100":
                return R.color.grey_100;
            case "grey_200":
                return R.color.grey_200;
            case "grey_300":
                return R.color.grey_300;
            case "grey_400":
                return R.color.grey_400;
            case "grey_500":
                return R.color.grey_500;
            case "grey_600":
                return R.color.grey_600;
            case "grey_700":
                return R.color.grey_700;
            case "grey_800":
                return R.color.grey_800;
            case "grey_900":
                return R.color.grey_900;

            case "black":
                return R.color.black;
            case "white":
                return R.color.white;


            default:
                return 0;
        }

    }

    public static String getColorCode(Context context, String nameColor) {
        return context.getResources().getString(getColorResourceByName(nameColor));
    }

    public static int parseColor(String colorString){
        return Color.parseColor(colorString);
    }

    public static int getColor (Context context,int colorResourceID)
    {
        return Color.parseColor( context.getResources().getString(colorResourceID));
    }

    public static ArrayList<Integer> getDefaultListColorByID() {

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add( R.color.red_A200);
        colors.add( R.color.red_A400);
        colors.add( R.color.red_A700);
        colors.add( R.color.deep_purple_A200);
        colors.add( R.color.deep_purple_A400);
        colors.add( R.color.deep_purple_A700);
        colors.add( R.color.light_blue_A200);
        colors.add( R.color.light_blue_A400);
        colors.add( R.color.light_blue_A700);
        colors.add( R.color.green_A200);
        colors.add( R.color.green_A400);
        colors.add( R.color.green_A700);
        colors.add( R.color.yellow_A200);
        colors.add( R.color.yellow_A400);
        colors.add( R.color.yellow_A700);
        colors.add( R.color.deep_orange_A200);
        colors.add( R.color.deep_orange_A400);
        colors.add( R.color.deep_orange_A700);
        colors.add( R.color.blue_grey_700);
        colors.add( R.color.blue_grey_800);
        colors.add( R.color.blue_grey_900);
        colors.add( R.color.pink_A200);
        colors.add( R.color.pink_A400);
        colors.add( R.color.pink_A700);
        colors.add( R.color.indigo_A200);
        colors.add( R.color.indigo_A400);
        colors.add( R.color.indigo_A700);
        colors.add( R.color.cyan_A200);
        colors.add( R.color.cyan_A400);
        colors.add( R.color.cyan_A700);
        colors.add( R.color.light_green_A200);
        colors.add( R.color.light_green_A400);
        colors.add( R.color.light_green_A700);
        colors.add( R.color.amber_A200);
        colors.add( R.color.amber_A400);
        colors.add( R.color.amber_A700);
        colors.add( R.color.brown_700);
        colors.add( R.color.brown_800);
        colors.add( R.color.brown_900);
        colors.add( R.color.purple_A200);
        colors.add( R.color.purple_A400);
        colors.add( R.color.purple_A700);
        colors.add( R.color.blue_A200);
        colors.add( R.color.blue_A400);
        colors.add( R.color.blue_A700);
        colors.add( R.color.teal_A200);
        colors.add( R.color.teal_A400);
        colors.add( R.color.teal_A700);
        colors.add( R.color.lime_A200);
        colors.add( R.color.lime_A400);
        colors.add( R.color.lime_A700);
        colors.add( R.color.orange_A200);
        colors.add( R.color.orange_A400);
        colors.add( R.color.orange_A700);
        colors.add( R.color.grey_700);
        colors.add( R.color.grey_800);
        colors.add( R.color.grey_900);



        return  colors;

    }

    public static ArrayList<String> getDefaultListColorByValue() {

        ArrayList<String> colors = new ArrayList<String>();

        colors.add("#FFE082");
        colors.add("#FFC107");
        colors.add("#ff6f00");
        colors.add("#90CAF9");
        colors.add("#2196F3");
        colors.add("#0D47A1");
        colors.add("#607D8B");
        colors.add("#263238");
        colors.add("#BCAAA4");
        colors.add("#795548");
        colors.add("#3E2723");
        colors.add("#80DEEA");
        colors.add("#00BCD4");
        colors.add("#006064");
        colors.add("#FFAB91");
        colors.add("#FF5722");
        colors.add("#BF360C");
        colors.add("#673AB7");
        colors.add("#311B92");
        colors.add("#A5D6A7");
        colors.add("#4CAF50");
        colors.add("#1B5E20");
        colors.add("#EEEEEE");
        colors.add("#9E9E9E");
        colors.add("#212121");
        colors.add("#9FA8DA");
        colors.add("#3F51B5");
        colors.add("#1A237E");
        colors.add("#81D4FA");
        colors.add("#03A9F4");
        colors.add("#01579B");
        colors.add("#8BC34A");
        colors.add("#33691E");
        colors.add("#E6EE9C");
        colors.add("#CDDC39");
        colors.add("#827717");
        colors.add("#FFCC80");
        colors.add("#FF9800");
        colors.add("#E65100");
        colors.add("#F48FB1");
        colors.add("#E91E63");
        colors.add("#880E4F");
        colors.add("#F50057");
        colors.add("#CE93D8");
        colors.add("#9C27B0");
        colors.add("#4A148C");
        colors.add("#D500F9");
        colors.add("#EF9A9A");
        colors.add("#F44336");
        colors.add("#B71C1C");
        colors.add("#80CBC4");
        colors.add("#009688");
        colors.add("#004D40");
        colors.add("#FFF59D");
        colors.add("#FFEB3B");
        colors.add("#F57F17");



        return colors;

    }
}
