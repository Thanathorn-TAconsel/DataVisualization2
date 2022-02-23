import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.List;

import static javax.swing.SpringLayout.*;


public class Main {
    JFrame frame = new JFrame();
    Container container;
    SpringLayout layout = new SpringLayout();
    Vector<String> test = new Vector<>(),test1 = new Vector<>();
    Spring pw,ph;
    DefaultListModel<Linked> row = new DefaultListModel<>();
    DefaultListModel<Linked> column = new DefaultListModel<>();
    DefaultListModel filter = new DefaultListModel<>();
    DefaultListModel<String> filelist = new DefaultListModel<>();
    JTable table = new JTable();
    TableFileReader tableReader = new TableFileReader();
    DimensionFilter dimensionFilter = new DimensionFilter();
    MeasureFilter measureFilter = new MeasureFilter();
    Chart chart = new Chart();
    Main() throws Exception {
        frame.setSize(1200,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = frame.getContentPane();
        container.setLayout(layout);
        pw = layout.getConstraint(WIDTH,container);
        ph = layout.getConstraint(HEIGHT,container);

        JLabel label = new JLabel("Dimension");
        container.add(label);
        layout.putConstraint(WEST,label,10,WEST,container);
        layout.putConstraint(NORTH,label,10,NORTH,container);



        JList list = new JList();
        JScrollPane dimensionPane = new JScrollPane(list);
        list.setListData(tableReader.getDimensionList());
        list.setDragEnabled(true);
        list.setTransferHandler(new ExportTransferHandler(list));
        container.add(dimensionPane);
        layout.putConstraint(WEST,dimensionPane,0,WEST,label);
        layout.putConstraint(NORTH,dimensionPane,10,SOUTH,label);
        layout.putConstraint(EAST,dimensionPane,200,WEST,dimensionPane);
        layout.getConstraints(dimensionPane).setHeight(Spring.scale(ph,0.4f));

        JLabel label1 = new JLabel("Measure");
        container.add(label1);
        layout.putConstraint(WEST,label1,10,WEST,container);
        layout.putConstraint(NORTH,label1,10,SOUTH,dimensionPane);

        JList list1 = new JList();
        JScrollPane measurePane = new JScrollPane(list1);
        list1.setListData(tableReader.getMeasureList());
        list1.setDragEnabled(true);
        list1.setTransferHandler(new ExportTransferHandler(list1));
        container.add(measurePane);
        layout.putConstraint(WEST,measurePane,0,WEST,label1);
        layout.putConstraint(NORTH,measurePane,10,SOUTH,label1);
        layout.putConstraint(EAST,measurePane,0,EAST,dimensionPane);
        layout.putConstraint(SOUTH,measurePane,-10,SOUTH,container);

        JLabel label2 = new JLabel("Row");
        container.add(label2);
        layout.putConstraint(WEST,label2,10,EAST,dimensionPane);
        layout.putConstraint(NORTH,label2,0,NORTH,label);

        JList rowList = new JList();
        JScrollPane rowPane = new JScrollPane(rowList);
        rowList.setModel(row);
        rowList.setDropMode(DropMode.ON_OR_INSERT);
        rowList.setDragEnabled(true);
        rowList.setTransferHandler(new ImportTransferHandler(rowList,row,true));
        container.add(rowPane);
        layout.putConstraint(WEST,rowPane,10,EAST,dimensionPane);
        layout.putConstraint(EAST,rowPane,200,WEST,rowPane);
        layout.putConstraint(NORTH,rowPane,0,NORTH,dimensionPane);
        layout.putConstraint(SOUTH,rowPane,0,SOUTH,dimensionPane);

        JLabel label3 = new JLabel("Column");
        container.add(label3);
        layout.putConstraint(WEST,label3,10,EAST,measurePane);
        layout.putConstraint(NORTH,label3,0,NORTH,label1);

        JList columnList = new JList();
        JScrollPane columnPane = new JScrollPane(columnList);
        columnList.setModel(column);
        columnList.setDropMode(DropMode.ON_OR_INSERT);
        columnList.setDragEnabled(true);
        columnList.setTransferHandler(new ImportTransferHandler(columnList,column,true));
        container.add(columnPane);
        layout.putConstraint(WEST,columnPane,10,EAST,measurePane);
        layout.putConstraint(EAST,columnPane,200,WEST,columnPane);
        layout.putConstraint(NORTH,columnPane,0,NORTH,measurePane);
        layout.putConstraint(SOUTH,columnPane,0,SOUTH,measurePane);

        JLabel label4 = new JLabel("Filter");
        container.add(label4);
        layout.putConstraint(WEST,label4,10,EAST,rowPane);
        layout.putConstraint(NORTH,label4,0,NORTH,label2);

        JList filterList = new JList();
        filterList.setModel(filter);
        filterList.setDropMode(DropMode.ON_OR_INSERT);
        filterList.setDragEnabled(true);
        filterList.setTransferHandler(new ImportTransferHandler(filterList,filter,false));
        JScrollPane filterPane = new JScrollPane(filterList);
        container.add(filterPane);
        layout.putConstraint(WEST,filterPane,0,WEST,label4);
        layout.putConstraint(EAST,filterPane,200,WEST,filterPane);
        layout.putConstraint(SOUTH,filterPane,0,SOUTH,rowPane);
        layout.putConstraint(NORTH,filterPane,0,NORTH,rowPane);

        JLabel label5 = new JLabel("File");
        container.add(label5);
        layout.putConstraint(WEST,label5,0,WEST,filterPane);
        layout.putConstraint(NORTH,label5,10,SOUTH,filterPane);

        JList fileJlist = new JList();
        fileJlist.setModel(filelist);
        fileJlist.setDropMode(DropMode.ON_OR_INSERT);
        fileJlist.setDragEnabled(true);
        JScrollPane fileJlistPane = new JScrollPane(fileJlist);
        container.add(fileJlistPane);
        layout.putConstraint(WEST,fileJlistPane,0,WEST,label5);
        layout.putConstraint(EAST,fileJlistPane,200,WEST,fileJlistPane);
        layout.putConstraint(SOUTH,fileJlistPane,-50,SOUTH,container);
        layout.putConstraint(NORTH,fileJlistPane,10,SOUTH,label5);
        fileJlist.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        filelist.add(0,file.getAbsoluteFile().toString());
                    }
                    if (filelist.size() > 1) {
                        CsvData.unionFile(new CsvData(filelist.get(0)),new CsvData(filelist.get(1)),"temp");
                        for (int i = 2;i < filelist.size();i++) {
                            CsvData.unionFile(new CsvData("temp"),new CsvData(filelist.get(i)),"temp");
                        }
                        row.clear();
                        column.clear();
                        filter.clear();
                        tableReader.loadData("temp");
                        list.setListData(tableReader.getDimensionList());
                        list1.setListData(tableReader.getMeasureList());
                    }
                    if (filelist.size() == 1) {
                        row.clear();
                        column.clear();
                        filter.clear();
                        tableReader.loadData(filelist.get(0));
                        list.setListData(tableReader.getDimensionList());
                        list1.setListData(tableReader.getMeasureList());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JScrollPane tablePane = new JScrollPane(table);
        container.add(tablePane);
        layout.putConstraint(WEST,tablePane,10,EAST,filterPane);
        layout.putConstraint(NORTH,tablePane,0,NORTH,filterPane);
        layout.putConstraint(EAST,tablePane,-10,EAST,container);
        layout.putConstraint(SOUTH,tablePane,-10,SOUTH,container);
        frame.setVisible(true);
        frame.setTransferHandler(new DrainTranfer());
        row.addListDataListener(new DataChangeListener());
        column.addListDataListener(new DataChangeListener());



        //dimensionFilter.addException(tableReader.getHeader("Category"),"Technology");
        //measureFilter.addException(tableReader.getHeader("Profit"),0,new Range(0,Double.MAX_VALUE));
        rowList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {
                    if (rowList.getSelectedIndex() != -1){
                        listSelect(rowList,row,rowList.getSelectedValue(),e);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        columnList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {
                    if (columnList.getSelectedIndex() != -1){
                        listSelect(columnList,column,columnList.getSelectedValue(),e);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        filterList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {
                    if (filterList.getSelectedIndex() != -1){
                        filterSelect((Linked) filterList.getSelectedValue());
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        itemChange();
    }

    private void filterSelect(Linked selected) {
        System.out.println(selected.getData());
        if (tableReader.isMeasure[tableReader.getHeader(selected.getData())]) {
            if (selected.range == null) {
                String[] link = {selected.getData()};
                Integer[] f = {3};
                Integer[] f2 = {4};
                HashMap maxMap = tableReader.buildMap(new String[0],link, new DimensionFilter(),new MeasureFilter(),f,true);
                HashMap minMap = tableReader.buildMap(new String[0],link, new DimensionFilter(),new MeasureFilter(),f2,true);
                double max = ((double[]) maxMap.get(selected.getData()))[0];
                double min = ((double[]) minMap.get(selected.getData()))[0];
                Range range = getRange(max,min,selected);
                if (range != null){
                    selected.range = range;
                }
            } else {
                Range range = getRange(selected.range.max,selected.range.min,selected);
                if (range != null){
                    selected.range = range;
                }
            }
        } else {
            String[] link = {selected.getData()};
            HashMap data = tableReader.buildMap(link,new String[0], new DimensionFilter(),new MeasureFilter(),new Integer[0],true);
            String[] list= (String[]) data.keySet().toArray(new String[data.size()]);
            ArrayList<String> b = new ArrayList<>();
            if (selected.exception == null) {
                selected.exception = Excep(new String[0],list);
            } else {
                for (int i = 0;i < list.length;i++) {
                    boolean bre = true;
                    for (int x = 0;x < selected.exception.length;x++) {
                        if (selected.exception[x].equals(list[i])) {
                            bre = false;
                            break;
                        }
                    }
                    if (bre)
                    b.add(list[i]);
                }
                selected.exception = Excep(selected.exception,b.toArray(new String[b.size()]));
            }

        }
        itemChange();
    }
    private String[] Excep(String[] exc,String[] ava) {
        DefaultListModel model = new DefaultListModel();
        for (int i = 0;i < exc.length;i++) {
            model.add(0,exc[i]);
        }
        JList notInclude = new JList(exc);
        notInclude.setModel(model);
        notInclude.setDropMode(DropMode.ON_OR_INSERT);
        notInclude.setDragEnabled(true);
        notInclude.setTransferHandler(new ImportTransferHandler(notInclude,model,true));
        JScrollPane p1 = new JScrollPane(notInclude);
        p1.setPreferredSize(new Dimension(200,200));

        DefaultListModel model2 = new DefaultListModel();
        for (int i = 0;i < ava.length;i++) {
            model2.add(0,ava[i]);
        }
        JList include = new JList(ava);
        include.setModel(model2);
        include.setDropMode(DropMode.ON_OR_INSERT);
        include.setDragEnabled(true);
        include.setTransferHandler(new ImportTransferHandler(include,model2,true));
        JScrollPane p2 = new JScrollPane(include);
        p2.setPreferredSize(new Dimension(200,200));
        final JComponent[] RangeValue = new JComponent[]{
                new JLabel("Exception"),
                p1,
                new JLabel("Include"),
                p2
        };
        Object[] option = { "Apply", "Cancel"};
        int result = JOptionPane.showOptionDialog(null, RangeValue, "Filter Measurement", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, option, null);
        if (result == 0) {
            try {
                String[] e = utilOperation.toStringArray(model);
                System.out.println(Arrays.toString(e));
                return e;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println(result);
        }
        return null;
    }

    private Range getRange(double maxV,double minV,Linked l) {
        JTextField max = new JTextField(maxV + "");
        JTextField min = new JTextField(minV + "");
        JRadioButton r0 = new JRadioButton("SUM");
        JRadioButton r1 = new JRadioButton("AVG");
        JRadioButton r2 = new JRadioButton("MED");
        JRadioButton r3 = new JRadioButton("MIN");
        JRadioButton r4 = new JRadioButton("MAX");
        JRadioButton r5 = new JRadioButton("Count");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(r0);
        buttonGroup.add(r1);
        buttonGroup.add(r2);
        buttonGroup.add(r3);
        buttonGroup.add(r4);
        buttonGroup.add(r5);
        final JComponent[] RangeValue = new JComponent[]{
                new JLabel("Maximun"),
                max,
                new JLabel("Minimum"),
                min
                ,r0,r1,r2,r3,r4,r5
        };
        Object[] option = { "Apply", "Cancel"};
        int result = JOptionPane.showOptionDialog(null, RangeValue, "Filter Measurement", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, option, null);
        if (result == 0) {
            try {
                if (r0.isSelected()) {
                    l.operation = 0;
                } else if (r1.isSelected()) {
                    l.operation = 1;
                } else if (r2.isSelected()) {
                    l.operation = 2;
                } else if (r3.isSelected()) {
                    l.operation = 3;
                } else if (r4.isSelected()) {
                    l.operation = 4;
                } else if (r5.isSelected()) {
                    l.operation = 5;
                }
                return new Range(Double.parseDouble(min.getText()),Double.parseDouble(max.getText()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println(result);
        }
        return null;
    }
    private void listSelect (JList c,DefaultListModel model,Object selected,MouseEvent e) {
        Linked sel = (Linked) selected;
        System.out.println(sel.getData());
        sel.showMeasureMenu(c,e.getX(),e.getY());
        sel.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.setModel(model);
                itemChange();
            }
        });
    }

    HashMap map;
    private void itemChange() {
        try {
            dimensionFilter.clear();
            measureFilter.clear();
            for (int i = 0;i < filter.size();i++) {
                Linked l = (Linked) filter.get(i);
                if (tableReader.isMeasure[tableReader.getHeader(l.getData())]) {
                    measureFilter.addException(tableReader.getHeader(l.getData()),l.operation,l.range);
                } else {
                    for (int x = 0;x < l.exception.length;x++) {
                        dimensionFilter.addException(tableReader.getHeader(l.getData()),l.exception[x]);
                    }
                    Linked[] rows = utilOperation.toLinkedArray(row);
                    }
                }
            Linked[] columns = utilOperation.toLinkedArray(column);
            Linked[] rows = utilOperation.toLinkedArray(row);
            Object[][] all = tableReader.splitDM(utilOperation.concat(rows,columns));
            Integer[] operation = (Integer[]) all[2];
            String[] dimension = (String[]) all[0];
            String[] measure = (String[]) all[1];
            System.out.println("Dimension " + Arrays.toString(dimension));
            System.out.println("Measure " + Arrays.toString(measure));
            System.out.println("Operation " + Arrays.toString(operation));
            map = tableReader.buildMap(dimension,measure, dimensionFilter,measureFilter,operation,true);
            utilOperation.displayMap(map,0);
            displayTable(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
	    new Main();
    }
    ArrayList<ArrayList<String>> st = new ArrayList<>();
    String[][] data;
    int colHeight = 0;
    int colWidth = 0;
    int rowWidth = 0;
    HashMap<String,Integer> ma;
    HashMap<String,Double> maxMa;
    HashMap<String,Double> minMa;
    public void displayTable(HashMap m) {
        ma = new HashMap();
        int width = 0,height = 0;
        colHeight = 0;
        colWidth = 0;
        rowWidth = 0;
        int mode = 0;
        Object[][] calcColumn = null;
        Object[][] calcRow = null;
        HashMap rowMap = null,columnMap = null;
        if (row.size() > 0){
            calcRow = tableReader.splitDM(utilOperation.toLinkedArray(row));
            if (calcRow[1].length > 0) {
                mode = 1;
            }
            rowMap = tableReader.buildMap((String[]) calcRow[0], (String[]) calcRow[1], dimensionFilter,measureFilter, (Integer[]) calcRow[2],false);
            height += clc(rowMap);
            rowWidth += calcRow[0].length;
            if (calcRow[1].length > 0) {
                for (int i = 0;i < calcRow[1].length;i++) {
                    ma.put(calcRow[1][i].toString(),ma.size());
                }
                rowWidth++;
            }
            width += rowWidth;
        }
        System.out.println(width + "," + height);

        if (column.size() > 0) {
            calcColumn = tableReader.splitDM(utilOperation.toLinkedArray(column));
            if (calcColumn[1].length > 0) {
                mode = 2;
            }
            columnMap = tableReader.buildMap((String[]) calcColumn[0], (String[]) calcColumn[1], dimensionFilter,measureFilter, (Integer[]) calcColumn[2],false);
            colWidth = clc(columnMap);
            width += colWidth;
            colHeight += calcColumn[0].length;
            if (calcColumn[1].length > 0) {
                for (int i = 0;i < calcColumn[1].length;i++) {
                    ma.put(calcColumn[1][i].toString(),ma.size());
                }
                colHeight++;
            }
            height += colHeight;
        }
        data = new String[height][width];
        System.out.println(width + "," + height);
        s = 0;
        if (rowMap != null) {
            roM((String[]) calcRow[1],colHeight);
            ro(rowMap,0,colHeight,0);
        }
        if (columnMap != null) {
            colM((String[]) calcColumn[1],rowWidth);
            col(columnMap,0,rowWidth,0);

        }
        chart.reset();
        if (mode == 2) {
            if (colHeight > 1) {
                HashMap get = getLast(columnMap);
                Object[] keylist = get.keySet().toArray();
                for (Object key:keylist) {
                    DefaultCategoryDataset dat = new DefaultCategoryDataset();
                    showNumber(rowWidth,colHeight,dat,key.toString(),false);
                    chart.addData(dat,key.toString(),false);
                }
            } else {
                DefaultCategoryDataset dat = new DefaultCategoryDataset();
                showNumber(rowWidth,colHeight,dat,null,false);
                chart.addData(dat,"BarChart",false);
            }
        } else if (mode == 1) {
            if (rowWidth > 1) {
                HashMap get = getLast(rowMap);
                Object[] keylist = get.keySet().toArray();
                for (Object key:keylist) {
                    DefaultCategoryDataset dat = new DefaultCategoryDataset();
                    showNumber(rowWidth,colHeight,dat,key.toString(),true);
                    chart.addData(dat,key.toString(),true);
                }
            } else {
                DefaultCategoryDataset dat = new DefaultCategoryDataset();
                showNumber(rowWidth,colHeight,dat,null,false);
                chart.addData(dat,"BarChart",true);
            }
            /*
            DefaultCategoryDataset dat = new DefaultCategoryDataset();
            showNumber(rowWidth,colHeight,dat,null,false);
            chart.addData(dat,"BarChart",true);

             */
        } else {
            DefaultCategoryDataset dat = new DefaultCategoryDataset();
            showNumber(rowWidth,colHeight,dat,null,false);
            chart.addData(dat,"BarChart",true);
        }

        DefaultTableModel tab = new DefaultTableModel(data,new String[width]);
        table.setModel(tab);
        System.out.println("MODE:" + mode);
    }

    public HashMap getLast(HashMap root) {
        for (Object key:root.keySet()) {
            Object value = root.get(key);
            if (value instanceof HashMap) {
                return getLast((HashMap) value);
            } else {
                return root;
            }
        }
        return null;
    }
    int s = 0;
    public int clc(HashMap root) {
        s = 0;
        ccc(root,0);
        return s;
    }
    public void showNumber(int startX,int startY,DefaultCategoryDataset dat,String filter,boolean row) {
        for (int y = startY;y < data.length;y++) {
            for (int x = startX;x < data[y].length;x++) {

                String[] sA = recAccess(0,startX,y);
                String[] sB = colAccess(0,startY,x);
                String[] sAm = recAccessM(0,startX,y);
                String[] sBm = colAccessM(0,startY,x);
                String[] sum = utilOperation.concat(utilOperation.concat(sA,sB),utilOperation.concat(sAm,sBm));
                System.out.println(Arrays.toString(sum));
                data[y][x] = get(sum,0,map);
                if (data[y][x] != "") {
                    if (filter == null) {
                        dat.addValue(Double.parseDouble(data[y][x]),sum[sum.length - 1],b(sum,sum.length - 1));
                    } else {
                        if (row) {
                            if (sA.length -1 >= 0)
                                if (sA[sA.length -1].equals(filter)) {
                                    dat.addValue(Double.parseDouble(data[y][x]),sum[sum.length - 1],b(sum,sum.length - 1));
                                }
                        } else {
                            if (sB.length -1 >= 0){
                                if (sB[sB.length - 1].equals(filter)) {
                                    dat.addValue(Double.parseDouble(data[y][x]),sum[sum.length - 1],b(sum,sum.length - 1));
                                }
                            } else{
                                System.out.println("DEBUG> " + Arrays.toString(sB));
                            }

                        }
                    }


                }

            }
        }
    }
    public String b(String[] in,int len) {
        StringBuilder out = new StringBuilder();
        for (int i = 0;i < len;i++) {
            out.append(in[i] + "\t");
        }
        return out.toString();
    }
    public String get(String[] e,int index,Object root) {
        if (index < e.length) {
            if (ma.containsKey(e[index])) {
                if (root instanceof double[]) {
                    double[] ro = (double[]) root;
                    return ( (int)(ro[ma.get(e[index])]*100)/100.0) + "";
                }
            } else {
                if (root instanceof HashMap) {
                    return get(e, index + 1, ((HashMap) root).get(e[index]));
                }
            }
        }

        return "";
    }
    String[] a = new String[100];
    public String[] recAccess(int startX,int endX,int Y) {
        ArrayList<String> out = new ArrayList<>();
        for (int i = startX;i < endX;i++) {
            String lev;
            if (data[Y][i] == null) {
                lev = a[i];
            } else {
                lev = data[Y][i];
                a[i] = lev;
            }
            if (ma.containsKey(lev)) {

            } else {
                out.add(lev);
            }
        }
        return out.toArray(new String[out.size()]);
    }
    String[] b = new String[100];
    public String[] colAccess(int startX,int endY,int X) {
        ArrayList<String> out = new ArrayList<>();
        for (int i = startX;i < endY;i++) {
            String lev;
            if (data[i][X] == null) {
                lev = b[i];
            } else {
                lev = data[i][X];
                b[i] = lev;
            }
            if (ma.containsKey(lev)) {

            } else {
                out.add(lev);
            }
        }
        return out.toArray(new String[out.size()]);
    }
    public String[] recAccessM(int startX,int endX,int Y) {
        ArrayList<String> out = new ArrayList<>();
        for (int i = startX;i < endX;i++) {
            String lev;
            if (data[Y][i] == null) {
                lev = a[i];
            } else {
                lev = data[Y][i];
                a[i] = lev;
            }
            if (ma.containsKey(lev)) {
                out.add(lev);
            } else {

            }
        }
        return out.toArray(new String[out.size()]);
    }
    public String[] colAccessM(int startX,int endY,int X) {
        ArrayList<String> out = new ArrayList<>();
        for (int i = startX;i < endY;i++) {
            String lev;
            if (data[i][X] == null) {
                lev = b[i];
            } else {
                lev = data[i][X];
                b[i] = lev;
            }
            if (ma.containsKey(lev)) {
                out.add(lev);
            } else {

            }
        }
        return out.toArray(new String[out.size()]);
    }
    public int ro(HashMap root,int index,int offY,int indexY) {
        for (Object key:root.keySet()) {
            Object value = root.get(key);
            if (value instanceof HashMap) {
                System.out.println(index + " > " + indexY);
                data[indexY+offY][index] = key.toString();
                ro((HashMap) value,index+1,offY,indexY);
                indexY += clc((HashMap) value);
            } else if (value instanceof String[]){
                System.out.println(index + " > " + indexY);
                data[indexY+offY][index] = key.toString();
                String[] sel = (String[]) value;
                for (int i = 0;i < sel.length;i++) {
                    data[indexY+i+offY][index+1] = sel[i];
                }
                if (((String[]) value).length == 0) {
                    indexY++;
                } else {
                    indexY += ((String[]) value).length;
                }

            }
        }
        return indexY;
    }
    public void colM(String[] mea,int offX) {
        for (int i = 0;i < mea.length;i++) {
            data[0][offX+i] = mea[i];
        }
    }
    public void roM(String[] mea,int offX) {
        for (int i = 0;i < mea.length;i++) {
            data[offX+i][0] = mea[i];
        }
    }
    public int col(HashMap root,int index,int offX,int indexX) {
        for (Object key:root.keySet()) {
            Object value = root.get(key);
            if (value instanceof HashMap) {
                System.out.println(index + " > " + indexX);
                data[index][indexX+offX] = key.toString();
                col((HashMap) value,index+1,offX,indexX);
                indexX += clc((HashMap) value);
            } else if (value instanceof String[]){
                System.out.println(index + " > " + indexX);
                data[index][indexX+offX] = key.toString();
                String[] sel = (String[]) value;
                for (int i = 0;i < sel.length;i++) {
                    data[index+1][indexX+i+offX] = sel[i];
                }
                if (((String[]) value).length == 0) {
                    indexX++;
                } else {
                    indexX += ((String[]) value).length;
                }

            }
        }
        return indexX;
    }
    public void cl(HashMap root,int index) {
        for (Object key:root.keySet()) {
            Object value = root.get(key);
            data[colHeight + s][index] = key.toString();
            if (value instanceof HashMap) {
                cl((HashMap) value,index+1);
            } else if (value instanceof double[]){
                double[] sel = (double[]) value;
                for (int i = 0;i < sel.length;i++) {
                    data[colHeight+s][index + i + 1] = sel[i] + "";
                }
                s++;
            }
        }
    }
    public void ccc(HashMap root,int index) {

        for (Object key:root.keySet()) {
            Object value = root.get(key);
            if (value instanceof HashMap) {
                ccc((HashMap) value,index+1);
            } else if (value instanceof double[]){
                s++;
            } else if (value instanceof String[]){
                if (((String[]) value).length == 0) {
                    s+= 1;
                } else {
                    s+= ((String[]) value).length;
                }


            }
        }
    }

    private class DataChangeListener implements ListDataListener {

        @Override
        public void intervalAdded(ListDataEvent e) {
            itemChange();
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            itemChange();
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            itemChange();
        }
    }
}


