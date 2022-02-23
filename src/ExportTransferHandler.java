import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

class ExportTransferHandler extends TransferHandler {
    JList list;
    ExportTransferHandler(JList list) {
        this.list = list;
    }
    public int getSourceActions(JComponent c){
        return TransferHandler.COPY_OR_MOVE;
    }

    public Transferable createTransferable(JComponent c) {
        return new StringSelection(list.getSelectedValue().toString());
    }
}

