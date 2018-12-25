package com.chiy.rfgc.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 判断目标字符串是否为空
     *
     * @param target
     * @return
     */
    public static Boolean isEmpty(String target) {
        if (target == null) {
            return true;
        }
        if (target.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断目标字符串是否不为空
     *
     * @param target
     * @return
     */
    public static Boolean isNotEmpty(String target) {
        if (target == null) {
            return false;
        }
        if (target.trim().length() <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 分裂字符串
     *
     * @param splitStr 需要分裂的字符串
     * @param spilt    此字符分裂
     * @return String[]
     */
    public static String[] splitString(String splitStr, String spilt) {
        if (isEmpty(splitStr) || isEmpty(spilt)) {
            return null;
        }
        String[] strA = splitStr.split(spilt);
        return strA;
    }

    /**
     * 分裂字符串
     *
     * @param splitStr 需要分裂的字符串
     * @param spilt    此字符分裂
     * @return Integer[]
     */
    public static Integer[] splitIntger(String splitStr, String spilt) {
        if (isEmpty(splitStr) || isEmpty(spilt)) {
            return null;
        }
        String[] strA = splitStr.split(spilt);
        Integer[] strB = new Integer[strA.length];
        for (int i = 0; i < strA.length; i++) {
            strB[i] = Integer.parseInt(strA[i]);
        }
        return strB;
    }

    /**
     * 获取给定时间前几小时的时间
     *
     * @param date 时间段的结束时间
     * @param hour 小时
     * @return String
     */
    public static String getbeginString(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//date 换成已经已知的Date对象
        cal.add(Calendar.HOUR_OF_DAY, hour);// before  hour
        return format.format(cal.getTime());
    }

    /**
     * 获取给定时间前几小时的时间
     *
     * @param date 时间段的结束时间
     * @param hour 小时
     * @return Date
     */
    public static Date getbeginDate(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//date 换成已经已知的Date对象
        cal.add(Calendar.HOUR_OF_DAY, hour);// before  hour
        return cal.getTime();
    }

    /**
     * 是否有 横断<br/> 参数为四个点的坐标
     *
     * @param px1
     * @param py1
     * @param px2
     * @param py2
     * @param px3
     * @param py3
     * @param px4
     * @param py4
     * @return
     */
    public static boolean isIntersect(double px1, double py1, double px2, double py2,
                                      double px3, double py3, double px4, double py4) {
        boolean flag = false;
        double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);
        if (d != 0) {
            double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3))
                    / d;
            double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1))
                    / d;
            if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 目标点是否在目标边上边上<br/>
     *
     * @param px0 目标点的经度坐标
     * @param py0 目标点的纬度坐标
     * @param px1 目标线的起点(终点)经度坐标
     * @param py1 目标线的起点(终点)纬度坐标
     * @param px2 目标线的终点(起点)经度坐标
     * @param py2 目标线的终点(起点)纬度坐标
     * @return
     */
    public static boolean isPointOnLine(double px0, double py0, double px1,
                                        double py1, double px2, double py2) {
        boolean flag = false;
        double ESP = 1e-9;// 无限小的正数
        if ((Math.abs(Multiply(px0, py0, px1, py1, px2, py2)) < ESP)
                && ((px0 - px1) * (px0 - px2) <= 0)
                && ((py0 - py1) * (py0 - py2) <= 0)) {
            flag = true;
        }
        return flag;
    }

    public static double Multiply(double px0, double py0, double px1, double py1,
                                  double px2, double py2) {
        return ((px1 - px0) * (py2 - py0) - (px2 - px0) * (py1 - py0));
    }

    /**
     * 判断目标点是否在多边形内(由多个点组成)<br/>
     *
     * @param px        目标点的经度坐标
     * @param py        目标点的纬度坐标
     * @param polygonXA 多边形的经度坐标集合
     * @param polygonYA 多边形的纬度坐标集合
     * @return
     */
    public static boolean isPointInPolygon(double px, double py,
                                           ArrayList<Double> polygonXA, ArrayList<Double> polygonYA) {
        boolean isInside = false;
        double ESP = 1e-9;
        int count = 0;
        double linePoint1x;
        double linePoint1y;
        double linePoint2x = 180;
        double linePoint2y;

        linePoint1x = px;
        linePoint1y = py;
        linePoint2y = py;

        for (int i = 0; i < polygonXA.size() - 1; i++) {
            double cx1 = polygonXA.get(i);
            double cy1 = polygonYA.get(i);
            double cx2 = polygonXA.get(i + 1);
            double cy2 = polygonYA.get(i + 1);
            // 如果目标点在任何一条线上
            if (isPointOnLine(px, py, cx1, cy1, cx2, cy2)) {
                return true;
            }
            // 如果线段的长度无限小(趋于零)那么这两点实际是重合的，不足以构成一条线段
            if (Math.abs(cy2 - cy1) < ESP) {
                continue;
            }
            // 第一个点是否在以目标点为基础衍生的平行纬度线
            if (isPointOnLine(cx1, cy1, linePoint1x, linePoint1y, linePoint2x,
                    linePoint2y)) {
                // 第二个点在第一个的下方,靠近赤道纬度为零(最小纬度)
                if (cy1 > cy2)
                    count++;
            }
            // 第二个点是否在以目标点为基础衍生的平行纬度线
            else if (isPointOnLine(cx2, cy2, linePoint1x, linePoint1y,
                    linePoint2x, linePoint2y)) {
                // 第二个点在第一个的上方,靠近极点(南极或北极)纬度为90(最大纬度)
                if (cy2 > cy1)
                    count++;
            }
            // 由两点组成的线段是否和以目标点为基础衍生的平行纬度线相交
            else if (isIntersect(cx1, cy1, cx2, cy2, linePoint1x, linePoint1y,
                    linePoint2x, linePoint2y)) {
                count++;
            }
        }
        if (count % 2 == 1) {
            isInside = true;
        }

        return isInside;
    }

    public static boolean isPointInGrid(double px, double py, String area) {
        if (isEmpty(area))
            return false;
        ArrayList<Double> polygonXA = new ArrayList<>();
        ArrayList<Double> polygonYA = new ArrayList<>();
        String[] areaPoint = area.split("-");
        for (String str : areaPoint) {
            String[] point = str.split(",");
            Double a = Double.valueOf(point[0]);
            Double b = Double.valueOf(point[1]);
            polygonXA.add(a);
            polygonYA.add(b);
        }
        return isPointInPolygon(px, py, polygonXA, polygonYA);
    }

    /*
    将经纬度改为一个小数点的格式xx.xx
     */
    public static String update(String code) {
        //判断.的个数
        int count = 0;
        for (int k = 0; k < code.length(); k++) {
            String s = String.valueOf(code.charAt(k));
            if (s.equals(".")) {
                count++;
            }
        }
        NumberFormat df = NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(6);
        //如果大于1，
        double sum;
        if (count > 1) {
            return null;
//            int num1 = code.indexOf(".");
//            int num2 = code.lastIndexOf(".");
//            String code1 = code.substring(0,num1);
//            String code2 = code.substring(num1+1,num2);
//            String code3 = code.substring(num2+1);
//            double s1 = Double.parseDouble(code1);
//            double s2 = Double.parseDouble(code2);
//            double s3 = Double.parseDouble(code3);
//            double s = s1 + (s2 + s3/60)/60;
//            sum = Double.parseDouble(df.format(s));
//            return sum+"";
        }
        return code;
    }

    public static String getstationdata(String data) {
        Integer sss;
        try {
            sss = Integer.valueOf(data);
        } catch (Exception e) {
            sss = 0;
        }
        return sss + "";
    }

    public static Integer getIntegerdata(String data) {
        int sss;
        double sssdd;
        try {
            sssdd = Double.valueOf(data);
            sss = (int) sssdd;
        } catch (Exception e) {
            sss = 0;
        }
        return sss;
    }

    public static Integer getIntegerdata(Integer data) {
        int sss;
        try {
            sss = data;
        } catch (Exception e) {
            sss = 0;
        }
        return sss;
    }

    public static Double getDoubledata(String data) {
        double sss;
        try {
            sss = Double.valueOf(data);
        } catch (Exception e) {
            sss = Double.valueOf(0);
        }
        return sss;
    }

    public static Double getDoubledata(Double data) {
        Integer sss;
        try {
            sss = Integer.valueOf(String.valueOf(data));
        } catch (Exception e) {
            sss = 0;
        }
        return Double.valueOf(sss);
    }

    public static int getfxbyjd(Double data) {
        int fx = 1;
        if (data >= 337.5 || data < 22.5) {
            fx = 1;//北风
        } else if (data >= 22.5 && data < 67.5) {
            fx = 2;// "东北风";
        } else if (data >= 67.5 && data < 112.5) {
            fx = 3;// "东风";
        } else if (data >= 112.5 && data < 157.5) {
            fx = 4;//"东南风";
        } else if (data >= 157.5 && data < 202.5) {
            fx = 5;//"南风";
        } else if (data >= 202.5 && data < 247.5) {
            fx = 6;// "西南风";
        } else if (data >= 247.5 && data < 292.5) {
            fx = 7;// "西风";
        } else if (data >= 292.5 && data < 337.5) {
            fx = 8;//"西北风";
        }
        return fx;
    }
}
