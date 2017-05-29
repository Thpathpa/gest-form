package main.resources;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableauModel extends AbstractTableModel {
    private int colnum=4;
    private int rownum;
    private String[] colNames={
        "Formation","Organisateur","Date de debut","Date de fin"
    };
        private  ArrayList<String[]> ResultSets;
    /** Création de l'instance de TableauModel */
    public TableauModel(ResultSet rs) {
      
      ResultSets=new ArrayList<String[]>();  
    
      try{
        while(rs.next()){
      
              String[] row={
                rs.getString(1),rs.getString(2), rs.getString(3),rs.getString(4)         
            };
            ResultSets.add(row);
      
         }   
      }
      catch(Exception e){
          System.out.println("Exception dans TableauModel");
            }
        
    }
    
    @Override
    public Object getValueAt(int rowindex, int columnindex) {
        
       String[] row=ResultSets.get(rowindex);
       return row[columnindex];
        
    }
    @Override
    public int getRowCount() {
        return ResultSets.size();
    }
    @Override
    public int getColumnCount() {
        return colnum;
    }
    
    public String getColumnName(int param) {

       return colNames[param];
    }

}
