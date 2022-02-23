import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Linked {
    String data;
    String name;
    int operation = 0;
    Range range;
    String[] exception;
    JPopupMenu pop = new JPopupMenu();
    ActionListener update;
    Linked(String setname,String setdata) {
        this.data = setdata;
        this.name = setname;
        JMenuItem i1 = new JMenuItem("SUM");
        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOperation(0);
            }
        });
        JMenuItem i2 = new JMenuItem("Average");
        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOperation(1);
            }
        });
        JMenuItem i3 = new JMenuItem("Medium");
        i3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOperation(2);
            }
        });
        JMenuItem i4 = new JMenuItem("Max");
        i4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOperation(3);
            }
        });
        JMenuItem i5 = new JMenuItem("Min");
        i5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOperation(4);
            }
        });
        JMenuItem i6 = new JMenuItem("Count");
        i6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOperation(5);
            }
        });
        pop.add(i1);
        pop.add(i2);
        pop.add(i3);
        pop.add(i4);
        pop.add(i5);
        pop.add(i6);
    }
    public void setOperation(int operation) {
        this.operation = operation;

        switch (operation) {
            case 0:
                name = "SUM(" + data + ")";
                break;
            case 1:
                name = "AVG(" + data + ")";
                break;
            case 2:
                name = "MED(" + data + ")";
                break;
            case 3:
                name = "MAX(" + data + ")";
                break;
            case 4:
                name = "MIN(" + data + ")";
                break;
            case 5:
                name = "COUNT(" + data + ")";
                break;
        }
        if (update != null) {
            update.actionPerformed(null);
        }
    }
    public void setActionListener(ActionListener e) {
        this.update = e;
    }
    Linked(String name,String data,int operation) {
        this(name,data);
        this.operation = operation;
    }
    public String getData() {
        return data;
    }
    public String toString() {
        return name;
    }
    public void showMeasureMenu(Component c, int x, int y) {
        pop.show(c,x,y);
    }
}
