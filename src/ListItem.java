import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListItem extends JLabel {
    Color textHover = Color.black;
    Color backgroundHover = Color.white;

    Color textNormal = Color.white;
    Color backgroundNormal = Color.black;

    Color textSelect = Color.black;
    Color backgroundSelect = Color.gray;

    boolean isSelect = false;
    ActionListener actionListener;
    Item item;
    ListItem(Item item) {
        this.item = item;
        this.setText(item.text);
        this.setOpaque(true);
        this.setBackground(backgroundNormal);
        this.setForeground(textNormal);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (actionListener != null) {
                    actionListener.actionPerformed(null);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(backgroundHover);
                setForeground(textHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isSelect) {
                    setBackground(backgroundSelect);
                    setForeground(textSelect);
                } else {
                    setBackground(backgroundNormal);
                    setForeground(textNormal);
                }
            }
        });
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void select(boolean isSelect) {
        if (isSelect) {
            this.isSelect = true;
            setBackground(backgroundSelect);
            setForeground(textSelect);
        } else {
            this.isSelect = false;
            setBackground(backgroundNormal);
            setBackground(textNormal);
        }
    }
}
