/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mario
 */
public class ExportCSV {
    public static void exportTableToCSV(JTable table, JFrame parent){
        try{
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String defaultFileName = "poeple_data_" + date + ".csv";
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File(defaultFileName));
            int userSelection = fileChooser.showSaveDialog(parent);
            
            if(userSelection == JFileChooser.APPROVE_OPTION){
                File fileToSave = fileChooser.getSelectedFile();
                
                try(PrintWriter pw = new PrintWriter(fileToSave)){
                    TableModel model = table.getModel();
                    
                    for (int i = 0; i < model.getColumnCount(); i++){
                        pw.print(model.getColumnName(i));
                        if (i < model.getColumnCount() - 1) pw.print(",");
                    }
                    pw.println();
                    
                    
                    for (int row = 0; row < model.getRowCount();row++){
                         for (int col = 0; col < model.getColumnCount(); col++) {
                            Object value = model.getValueAt(row, col);
                            pw.print(value != null ? value.toString() : "");
                            if (col < model.getColumnCount() - 1) pw.print(",");
                    }
                         pw.println();
                }
                
                
                JOptionPane.showMessageDialog(parent,
                        "Data exported successfully as " + fileToSave.getName(),
                        "Export Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
                      
            
        }
    }catch (Exception ex){
    JOptionPane.showMessageDialog(parent,
            "error exporting data: " + ex.getMessage(),
            "Export Failed",
            JOptionPane.ERROR_MESSAGE);
}
}
}
    

