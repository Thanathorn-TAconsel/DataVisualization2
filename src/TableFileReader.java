import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TableFileReader {
    String[][] data;
    boolean[] isMeasure;
    String[] header;
    HashMap<String,Integer> headerIndex = new HashMap<>();
    ArrayList<String[]> str = new ArrayList<>();
    TableFileReader(String filename) throws Exception {
        loadData(filename);
    }
    ActionListener loadFileListener;
    TableFileReader(){

    }
    public void addLoadEvent(ActionListener loadFileListener) {
        this.loadFileListener = loadFileListener;
    }
    public Object[][] splitDM(Linked[] input) {
        Object[][] output = new Object[3][];
        int[] index = getHeader(utilOperation.toStringArray(input));
        ArrayList<String> dimension = new ArrayList<String>();
        ArrayList<String> measure = new ArrayList<String>();
        ArrayList<Integer> operation = new ArrayList<>();
        for (int i = 0;i < input.length;i++) {
            if (isMeasure[index[i]]) {
                measure.add(input[i].getData());
                operation.add(input[i].operation);
            } else {
                dimension.add(input[i].getData());
            }
        }
        output[0] = utilOperation.toStringArray(dimension);
        output[1] = utilOperation.toStringArray(measure);
        Integer[] in = new Integer[operation.size()];
        in = operation.toArray(in);
        output[2] = in;
        return output;
    }

    public HashMap buildMap(String[] dimension,String[] measure,DimensionFilter filter,MeasureFilter measureFilter,Integer[] operation,boolean calc) {
        if (data != null) {
            int[] dimensionIndex = getHeader(dimension);
            int[] measureIndex = getHeader(measure);
            HashMap output = new HashMap();
            for (int i = 0;i < data.length;i++) {
                insert(output,data[i],dimensionIndex,measureIndex,0,filter,measureFilter,operation,calc);
            }
            if (calc) {
                calc(output,0,operation);
            }
            System.out.println("Done");
            return output;
        }
        return null;
    }
    //0   1   2   3   4   5
    //SUM AVG MED MAX MIN COUNT
    public void calc(HashMap root, int index, Integer[] operation) {
        for (Object key:root.keySet()) {
            Object value = root.get(key);
            System.out.print(utilOperation.buildTab(index) + key);
            if (value instanceof HashMap) {
                //System.out.println();
                calc((HashMap) value,index+1,operation);
            } else if (value instanceof double[]){
                double[] sel = (double[]) value;
                double[] out = new double[sel.length / 4];
                for (int i = 0;i < out.length;i++) {
                    switch (operation[i]) {
                        case 0:
                            out[i] = sel[(i*4) +1];
                            break;
                        case 1:
                            out[i] = sel[(i*4) +1] / sel[(i*4) +0];
                            break;
                        case 2:
                            out[i] = (sel[(i*4) +3] - sel[(i*4) +2] / 2) + sel[(i*4) +2];
                            break;
                        case 3:
                            out[i] = sel[(i*4) +3];
                            break;
                        case 4:
                            out[i] = sel[(i*4) +2];
                            break;
                        case 5:
                            out[i] = sel[(i*4) +0];
                            break;
                    }
                }
                root.put(key,out);
                //System.out.println("\t" + Arrays.toString((double[]) value));
            } else if (value instanceof Double){
                System.out.println("\t" + (Double)value);
            }
        }
    }


    private void insert(HashMap root,String[] data,int[] dimension,int[] measure,int index,DimensionFilter dimensionFilter,MeasureFilter measureFilter,Integer[] operation,boolean calc) {
        if (dimensionFilter.inExcetion(data)) {
            return;
        }
        if (dimension.length == 0 && measure.length > 0) {
            for (int i = 0;i < measure.length;i++) {
                double[] value;
                if (root.containsKey(header[measure[i]])) {
                    value = (double[]) root.get(header[measure[i]]);
                    double get = Double.parseDouble(data[measure[i]]);
                    if (measureFilter.isValid(measure[i],operation[i],get)) {
                        value[0]++;
                        value[1] += get;
                        value[2] = utilOperation.min(value[2],get);
                        value[3] = utilOperation.max(value[3],get);
                    }

                    root.put(header[measure[i]],value);
                } else {
                    value = new double[4];
                    double get = Double.parseDouble(data[measure[i]]);
                    if (measureFilter.isValid(measure[i],operation[i],get)) {
                        value[0]= 1;
                        value[1] = get;
                        value[2] = get;
                        value[3] = get;
                    } else {
                        value[0]= 0;
                        value[1] = 0;
                        value[2] = Double.MAX_VALUE;
                        value[3] = Double.MIN_VALUE;
                    }
                    root.put(header[measure[i]],value);
                }
            }
        } else if (index == dimension.length - 1) {
            if (calc) {
                double[] ref;
                if (root.containsKey(data[dimension[index]])) {
                    ref = (double[]) root.get(data[dimension[index]]);
                    for (int i = 0;i < measure.length;i++) {
                        double sel = Double.parseDouble(data[measure[i]]);
                        if (measureFilter.isValid(measure[i],operation[i],sel)) {
                            ref[i*4]++;
                            ref[(i*4) + 1] += sel;
                            ref[(i*4) + 2] = utilOperation.min(ref[(i*2) + 2],sel);
                            ref[(i*4) + 3] = utilOperation.max(ref[(i*2) + 3],sel);
                        }
                    }
                } else {
                    ref = new double[measure.length*4];
                    for (int i = 0;i < measure.length;i++) {
                        double sel = Double.parseDouble(data[measure[i]]);
                        if (measureFilter.isValid(measure[i],operation[i],sel)) {
                            ref[i*4] = 1;
                            ref[(i*4) + 1] = sel;
                            ref[(i*4) + 2] = sel;
                            ref[(i*4) + 3] = sel;
                        } else {
                            ref[i*4] = 0;
                            ref[(i*4) + 1] = 0;
                            ref[(i*4) + 2] = Double.MAX_VALUE;
                            ref[(i*4) + 3] = Double.MIN_VALUE;
                        }
                    }
                    root.put(data[dimension[index]],ref);
                }
            } else {
                String[] ref;
                if (root.containsKey(data[dimension[index]])) {
                    ref = (String[]) root.get(data[dimension[index]]);
                    for (int i = 0;i < measure.length;i++) {
                        ref[i] = header[measure[i]];
                    }
                } else {
                    ref = new String[measure.length];
                    for (int i = 0;i < measure.length;i++) {
                        ref[i] = header[measure[i]];
                    }
                    root.put(data[dimension[index]],ref);
                }
            }


        } else {
            HashMap subRoot;
            if (root.containsKey(data[dimension[index]])) {
                subRoot = (HashMap) root.get(data[dimension[index]]);
            } else {
                subRoot = new HashMap();
                root.put(data[dimension[index]],subRoot);
            }
            insert(subRoot,data,dimension,measure,index + 1,dimensionFilter,measureFilter,operation,calc);
        }
    }


    public String[] getDimensionList() {
        ArrayList<String> prepareDimension = new ArrayList<>();
        if (header != null)
        for (int i = 0;i < header.length;i++) {
            if (!isMeasure[i]) {
                prepareDimension.add(header[i]);
            }
        }
        return (String[]) utilOperation.toStringArray(prepareDimension);
    }
    public String[] getMeasureList() {
        ArrayList<String> prepareMeasure = new ArrayList<>();
        if (header != null)
        for (int i = 0;i < header.length;i++) {
            if (isMeasure[i]) {
                prepareMeasure.add(header[i]);
            }
        }
        return (String[]) utilOperation.toStringArray(prepareMeasure);
    }
    public int getHeader(String key) {
        return headerIndex.get(key);
    }

    public int[] getHeader(String[] key) {
        int[] output = new int[key.length];
        for (int i = 0;i < key.length;i++) {
            output[i] = getHeader(key[i]);
        }
        return output;
    }

    public void loadData(String filename) throws Exception {
        ArrayList<String[]> output = new ArrayList<>();
        FileInputStream fin = new FileInputStream(filename);
        Scanner scan = new Scanner(fin);
        String readline;
        String[] data;
        readline = scan.nextLine();
        String[] prepare = stringSplit(readline);

        while (scan.hasNextLine()) {
            readline = scan.nextLine();
            data = stringSplit(readline);
            output.add(data);
        }
        String[] dataArray = output.get(1);
        int fIndex = -1;
        for (int i = 0;i < dataArray.length;i++) {
            if (utilOperation.findIn('/',2,dataArray[i])) {
                fIndex  = i;
                break;
            }
        }
        if (fIndex != -1) {
            String[] toHeader = utilOperation.expandStringArray(prepare,prepare.length + 3);
            toHeader[toHeader.length -3] = toHeader[fIndex] + " (Year)";
            toHeader[toHeader.length -2] = toHeader[fIndex] + " (Month)";
            toHeader[toHeader.length -1] = toHeader[fIndex] + " (Day)";
            header = toHeader;
        } else {
            header= prepare;
        }
        for (int i = 0;i < header.length;i++ ) {
            headerIndex.put(header[i],i);
        }
        isMeasure = new boolean[header.length];
        int i = 0;
        for (String datas: dataArray) {
            isMeasure[i] = utilOperation.isNumeric(datas);
            //System.out.println(isMeasure[i]);
            i++;
        }
        scan.close();
        fin.close();
        String[][] outArray = new String[output.size()][header.length];
        int x = 0;
        for (String[] a:output) {
            if (fIndex != -1) {
                outArray[x] = utilOperation.expandStringArray(a,header.length);
                String[] date = a[fIndex].split("/");
                outArray[x][outArray[x].length - 3] = date[2];
                outArray[x][outArray[x].length - 2] = date[1];
                outArray[x][outArray[x].length - 1] = date[0];
            } else {
                outArray[x] = a;
            }
            System.out.println(Arrays.toString(outArray[x]));
            x++;
        }
        /*
        String[][] strings = new String[output.size()][];
        this.data = output.toArray(strings);

         */
        this.data = outArray;
        if (loadFileListener != null) {
            loadFileListener.actionPerformed(null);
        }
    }

    public static String[] stringSplit(String input) {
        boolean en = true;
        ArrayList<String> output = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i < input.length();i++) {
            char c = input.charAt(i);
            if (c == '"') {
                en = !en;
            } else if (c == ',') {
                if (en) {
                    output.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
            } else {
                stringBuilder.append(c);
            }
        }
        output.add(stringBuilder.toString());
        stringBuilder.setLength(0);

        return output.toArray(new String[0]);
    }


    public static void main(String[] args) throws Exception {
        new TableFileReader("Superstore.csv");
    }
}