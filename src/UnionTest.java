import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UnionTest {
    UnionTest() throws Exception {
        /*csvData file1 = new csvData("testunion1.csv");
        csvData file2 = new csvData("testunion2.csv");
        csvData file3 = new csvData("testunion3.csv");
        csvData.unionFile(file1,file2, "union.csv");
        csvData file4 = new csvData("union.csv");
        csvData.unionFile(file4, file3, "union3.csv");*/
        csvData file1 = new csvData("testunion1.csv");
        csvData file2 = new csvData("testunion2.csv");
        String[] fileList = {"testunion1.csv","testunion2.csv","testunion3.csv"};
        csvData.unionFileV2(fileList);
    }

    public static void main(String[] args) throws Exception {
        new UnionTest();
    }
}


