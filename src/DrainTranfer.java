import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

class DrainTranfer extends TransferHandler {
    public boolean canImport(TransferHandler.TransferSupport supp) {
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
        if (ImportTransferHandler.modelFrom != null) {
            ImportTransferHandler.modelFrom.removeElementAt(ImportTransferHandler.listFrom.getSelectedIndex());
        }
        return true;
    }
}
