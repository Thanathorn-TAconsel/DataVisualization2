import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

class ImportTransferHandler extends TransferHandler {
    DefaultListModel model;
    JList list;
    static DefaultListModel modelFrom;
    static JList listFrom;
    boolean removeOnFinish;
    public int getSourceActions(JComponent c){
        return TransferHandler.MOVE;
    }

    public Transferable createTransferable(JComponent c) {
        modelFrom = model;
        listFrom = list;
        return new StringSelection(list.getSelectedValue().toString());
    }
    ImportTransferHandler(JList list,DefaultListModel model,boolean removeOnFinish) {
        this.model = model;
        this.list = list;
        this.removeOnFinish = removeOnFinish;
    }
    public boolean canImport(TransferHandler.TransferSupport supp) {
        if (!supp.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        return true;
    }

    public boolean importData(TransferHandler.TransferSupport supp) {
        // Fetch the Transferable and its data
        Transferable t = supp.getTransferable();
        String data = "";
        try {
            data = (String)t.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        // Fetch the drop location
        JList.DropLocation loc = list.getDropLocation();
        int row = loc.getIndex();

        model.add(row, new Linked(data,data));
        list.validate();
        if (removeOnFinish) {
            if (modelFrom != null) {
                modelFrom.removeElementAt(listFrom.getSelectedIndex());
            }
            //model.removeElementAt(list.getSelectedIndex());
        }
        return true;
    }
}