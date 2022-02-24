
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class utilOperation {
    public static String[] toStringArray(ArrayList list) {
        String[] output = new String[list.size()];
        list.toArray(output);
        return output;
    }
    public static double max(double a,double b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }
    public static double min(double a,double b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean findIn(char match,int count,String in) {
        int c = 0;
        for (int i =0;i < in.length();i++) {
            if (in.charAt(i) == match) {
                c++;
                if (c == count){
                    return true;
                }
            }
        }
        return false;
    }
    public static String buildTab(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < count;i++) {
            sb.append('\t');
        }
        return sb.toString();
    }
    public static void displayMap(HashMap root,int index) {
        for (Object key:root.keySet()) {
            Object value = root.get(key);
            System.out.print(buildTab(index) + key);
            if (value instanceof HashMap) {
                System.out.println();
                displayMap((HashMap) value,index+1);
            } else if (value instanceof double[]){
                System.out.println("\t" + Arrays.toString((double[]) value));
            } else if (value instanceof String[]){
                System.out.println("\t" + Arrays.toString((String[]) value));
            } else if (value instanceof Double){
                System.out.println("\t" + (Double)value);
            }
        }
    }
    public static String[] toStringArray(ArrayList list, boolean clearArray) {
        String[] output = toStringArray(list);
        if (clearArray) {
            list.clear();
        }
        return output;
    }
    public static String[] toStringArray(DefaultListModel<Linked> list) {
        Object[] out = list.toArray();
        String[] output = new String[list.size()];
        for (int i = 0;i < list.size();i++) {
            output[i] = ((Linked)out[i]).getData();
        }
        return output;
    }
    public static Linked[] toLinkedArray(DefaultListModel<Linked> list) {
        Linked[] output = new Linked[list.size()];
        for (int i = 0;i < list.size();i++) {
            output[i] = list.get(i);
        }
        return output;
    }
    public static String[] toStringArray(Linked[] list) {
        String[] output = new String[list.length];
        for (int i = 0;i < list.length;i++) {
            output[i] = ((Linked)list[i]).getData();
        }
        return output;
    }
    public static <T> T[] expand(T[] old,int newLen) {
        T[] newArray = (T[]) new Object[newLen];
        System.arraycopy(old, 0, newArray, 0, old.length);
        return newArray;
    }
    public static String[] expandStringArray(String[] old,int len) {
        String[] newArray = new String[len];
        System.arraycopy(old, 0, newArray, 0, old.length);

        //an alternative to using System.arraycopy would be a for-loop:
        // for(int i = 0; i < OrigArray.length; i++)
        //     newArray[i] = OrigArray[i];
        return newArray;
    }

    public static int[] concat(int [] a,int [] b){
        int[] ss = new int[a.length + b.length];
        int o = 0;
        for (int i = 0;i < a.length;i++) {
            ss[o] = a[i];
            o++;
        }
        for (int i = 0;i < b.length;i++) {
            ss[o] = b[i];
            o++;
        }
        return ss;
    }
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, (first.length + second.length));
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
