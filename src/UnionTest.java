import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UnionTest {
    UnionTest() throws Exception {
        String[] fileList = {"testunion1.csv","testunion2.csv","testunion3.csv"};
        CsvData.unionFileV2(fileList);
    }

    public static void main(String[] args) throws Exception {
        new UnionTest();
    }
}


