import javax.swing.*;
import java.awt.*;
import java.util.Vector;

class ListItems extends JPanel {
    Vector<ListItem> listItems = new Vector<>();
    JScrollPane sc;
    JPanel panel = new JPanel();
    ListItems() {
        this.setLayout(new CardLayout());
        sc = new JScrollPane(panel);
        this.add(sc);
        this.panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setSize(500,500);
        panel.setBackground(Color.red);
    }
    public void render() {
        panel.removeAll();
        for (ListItem sel:listItems) {
            panel.add(sel);
        }
    }
    public void setListData(Vector<String> input) {
        listItems.clear();
        for (String str: input) {
            ListItem it = new ListItem(new Item(str,null));
            it.setPreferredSize(new Dimension(100,100));
            listItems.add(it);
        }
        render();
    }
}
