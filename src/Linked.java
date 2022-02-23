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
                operation = 0;
                name = "SUM(" + data + ")";
                if (update != null) {
                    update.actionPerformed(null);
                }
            }
        });
        JMenuItem i2 = new JMenuItem("Average");
        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = 1;
                name = "AVG(" + data + ")";
                if (update != null) {
                    update.actionPerformed(null);
                }
            }
        });
        JMenuItem i3 = new JMenuItem("Medium");
        i3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = 2;
                name = "MED(" + data + ")";
                if (update != null) {
                    update.actionPerformed(null);
                }
            }
        });
        JMenuItem i4 = new JMenuItem("Max");
        i4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = 3;
                name = "MAX(" + data + ")";
                if (update != null) {
                    update.actionPerformed(null);
                }
            }
        });
        JMenuItem i5 = new JMenuItem("Min");
        i5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = 4;
                name = "MIN(" + data + ")";
                if (update != null) {
                    update.actionPerformed(null);
                }
            }
        });
        JMenuItem i6 = new JMenuItem("Count");
        i6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation = 5;
                name = "COUNT(" + data + ")";
                if (update != null) {
                    update.actionPerformed(null);
                }
            }
        });
        pop.add(i1);
        pop.add(i2);
        pop.add(i3);
        pop.add(i4);
        pop.add(i5);
        pop.add(i6);
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
