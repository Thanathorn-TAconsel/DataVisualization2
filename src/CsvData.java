import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CsvData {
    String[][] data; //[row][col]
    String[] header;
    HashMap<String,Integer> headerIndex = new HashMap<>();
    boolean[] isMeasure;
    String fileName;

    CsvData(String filename) throws Exception {
        this.fileName = filename;
        loadData(filename);
    }

    public void loadData(String filename) throws Exception {
        ArrayList<String[]> output = new ArrayList<>();
        FileInputStream fin = new FileInputStream(filename);
        Scanner scan = new Scanner(fin);
        String readline;
        String[] data;
        readline = scan.nextLine();
        header = TableFileReader.stringSplit(readline);
        for (int i = 0;i < header.length;i++ ) {
            headerIndex.put(header[i],i);
        }
        while (scan.hasNextLine()) {
            readline = scan.nextLine();
            data = TableFileReader.stringSplit(readline);
            output.add(data);
        }
        String[] dataArray = output.get(1);
        isMeasure = new boolean[dataArray.length];
        int i = 0;
        for (String datas: dataArray) {
            isMeasure[i] = utilOperation.isNumeric(datas);
            //System.out.println(isMeasure[i]);
            i++;
        }
        scan.close();
        fin.close();
        String[][] strings = new String[output.size()][];
        this.data = output.toArray(strings);
    }

    public static boolean isAdded(ArrayList data1, ArrayList<ArrayList> data2){
        //System.out.println("data 1 : " + data1);
        for (ArrayList element : data2){
            if (element.equals(data1)){
                return true;
            }
            else {
                //System.out.println("data 2 : " + element);
                //System.out.println("not found");
                continue;
            }
        }
        return false;
    }

    public static boolean checkHeader(CsvData file1,CsvData file2){
        if (file1.header.length == file2.header.length) {
            for (int i = 0; i < file1.header.length; i++) {
                if (!file1.header[i].equals(file2.header[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkHeaderV2(ArrayList<CsvData> CsvDataList) {
        for (int i = 0; i < CsvDataList.size(); i++) {
            try {
                System.out.print("check header for " + CsvDataList.get(i).fileName + " and " + CsvDataList.get(i+1).fileName);
                if (CsvDataList.get(i).header.length == CsvDataList.get(i+1).header.length) {   //check for the amount of header
                    for (int j = 0; j < CsvDataList.get(i).header.length; j++) {
                        if (!CsvDataList.get(i).header[j].equals(CsvDataList.get(i+1).header[j])){ //check for each header if they're the same or not
                            System.out.println("not valid");
                            return false;
                        }

                        else {
                            continue;
                        }
                    }
                }
                else return false;
                System.out.println(" : valid!");
            }
            catch (IndexOutOfBoundsException e) {
                //System.out.println(e);
            }
        }
        return true;
    }

    public static void unionFileV2(String[] fileList) throws Exception {
        ArrayList<CsvData> CsvDataList = new ArrayList<>(); //Create arrayList for multiple CsvData
        for (String element : fileList) {
            CsvDataList.add(new CsvData(element));
        }

        if (checkHeaderV2(CsvDataList)){ //Check if the file is valid by the header
            System.out.println("All files are validated!");
            try (PrintWriter writer = new PrintWriter("unionV2.csv")){
                StringBuilder sb = new StringBuilder();
                ArrayList<ArrayList> newData = new ArrayList<>();

                //write header to our files
                for (int i = 0;i < CsvDataList.get(0).header.length;i++) {
                    //System.out.println(CsvDataList.get(0).header[i]);
                    sb.append(CsvDataList.get(0).header[i]);
                    if (i != CsvDataList.get(0).header.length - 1) {
                        sb.append(',');
                    }
                    else break;
                }
                sb.append('\n');

                //pack data in to line
                for (CsvData file : CsvDataList) {
                    //System.out.println("file : " + file.fileName);
                    for (int i = 0; i < file.data.length; i++) {
                        ArrayList<String> lineTemp = new ArrayList<>();
                        for (int j = 0; j < file.header.length; j++) {
                            lineTemp.add(file.data[i][j]);
                        }
                        //System.out.println(lineTemp);
                        if (!newData.isEmpty()) {   //pack our data to new data line by line so we can check if the line is duplicated
                            if (isAdded(lineTemp, newData)) { //if the line is already in new data skip
                                continue;                     //else add the line to new data
                            }
                            else newData.add(lineTemp);
                        }
                        else newData.add(lineTemp);
                    }
                }

                for (int i = 0; i < newData.size(); i++) {
                    for (int j = 0; j < CsvDataList.get(0).header.length; j++) {
                        sb.append(newData.get(i).get(j));
                        if (j != CsvDataList.get(0).header.length - 1) {
                            sb.append(',');
                        }
                        else break;
                    }
                    sb.append('\n');
                }
                writer.write(sb.toString());
            }
            catch (FileNotFoundException e) {

            }
        }
        else {
            System.out.println("not valid");
        }
    }

    public static void unionFile(CsvData file1,CsvData file2,String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            StringBuilder sb = new StringBuilder();
            ArrayList<ArrayList> data1 = new ArrayList<>();
            ArrayList<ArrayList> data2 = new ArrayList<>();
            ArrayList<ArrayList> newData = new ArrayList<>();

            //check the header
            //if the file header is the same then the file is the same type
            if (!checkHeader(file1,file2)){
                System.out.println("Invalid File");
            }
            else {
                System.out.println("Valid File");

                //write header to output file
                for (int i = 0; i < file1.header.length; i++) {
                    sb.append(file1.header[i]);
                    if (i == file1.header.length - 1) break;    //check for eol, we will not add ',' to our eol
                    else sb.append(',');
                }
                sb.append('\n');

                //pack data in to line
                for (int i = 0; i < file1.data.length;i++) {
                    ArrayList Temp = new ArrayList<>();
                    for (int j = 0; j < file1.header.length; j++) {
                        Temp.add(file1.data[i][j]);
                    }
                    data1.add(Temp);
                }
                for (int i = 0; i < file2.data.length;i++) {
                    ArrayList Temp = new ArrayList<>();
                    for (int j = 0; j < file2.header.length; j++) {
                        Temp.add(file2.data[i][j]);
                    }
                    data2.add(Temp);
                }

                //added all element in data1 to output file
                for (ArrayList element : data1){
                    newData.add(element);
                }

                /*System.out.println("data1\n");
                for (ArrayList element : newData) {
                    System.out.println(element);
                }*/

                //System.out.println("\ndata2\n");

                //check if there is data2 in the output if yes do nothing if no add the element to output
                for (ArrayList element : data2){
                    if (isAdded(element,newData)) {
                        continue;
                    }
                    else {
                        //System.out.println(element);
                        newData.add(element);
                    }
                }

                //write new data to output file
                for (int i = 0; i < newData.size(); i++) {
                    for (int j = 0; j < newData.get(i).size(); j++) {
                        sb.append(newData.get(i).get(j));
                        if (j == newData.get(i).size() - 1) break;  //check for eol, we will not add ',' to our eol
                        else sb.append(',');
                    }
                    sb.append('\n');
                }
                System.out.println("Union File Done!");
                System.out.println("Filename : " + filename);
            }
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
