package com.gestform.vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Window.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.resources.MyDBConnect;

import javax.swing.ListSelectionModel;
import javax.swing.JButton;

public class Adherent {

	private JFrame frmAdherent;
	private JTable inscriptionsjTable;
	private JTable toutesFormationsjtable;
	
	public static String idLicence;
	
	static MyDBConnect mdbc;
    Statement stmt; 
    ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Adherent window = new Adherent();
					window.frmAdherent.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Adherent() {
		
        mdbc=new MyDBConnect();
        mdbc.init();
        Connection conn=mdbc.getMyConnection();
        try {
			stmt=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	// On rempli toutes les sessions de la bases de données dans la jcombobox
	private void remplir_formationsJcomb() throws SQLException {
	        
			mdbc=new MyDBConnect();
	        mdbc.init();
	        Connection conn=mdbc.getMyConnection();
	        stmt=conn.createStatement();
	        
	   String req= "SELECT `formation`.`libelle`,`session`.`dateDebut`,session.idSession \n" +
		"FROM session,\n" +
		"formation\n" +
		"WHERE `session`.`FORMATION_idFormation`=`formation`.`idFORMATION`"; 
			
			    try {

	        stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(req);
		while(res.next()){
			inscriptionComboBox.addItem((res.getString(1))+" "+(res.getString(2)+" #"+(res.getString(3))));     
	// le nom du jComboBox est jComboName et  <indexe de la colonne > est l'indexe de la colonne dont vous voulez afficher dans le combobox ,elle peut prendre l'une des valeurs 1,2 . .  
			}
			res.close();
		} catch (SQLException e) {
		e.printStackTrace();
			}
			System.out.println("Problème de requête");	
			}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdherent = new JFrame();
		frmAdherent.setTitle("Adherent");
		frmAdherent.setSize(452, 439);
		frmAdherent.setResizable(true);
		frmAdherent.setBounds(100, 100, 450, 300);
		frmAdherent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel formationIncriptionjLabel = new JLabel("Choisissez la formation à laquelle vous inscrire");
		
		JComboBox inscriptionComboBox = new JComboBox();
		
		JLabel mesFormationsjLabel = new JLabel("Les formations aux quelles vous êtes inscrit");
		
		JScrollPane inscritScrollPane = new JScrollPane();
		
		JButton desinscriptionButton = new JButton("Désinscrire");
		
		JLabel formationsDisponibleLabel = new JLabel("Tous les formations disponibles");
		
		JScrollPane toutesFormationsScrollPane_1 = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frmAdherent.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(toutesFormationsScrollPane_1, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(formationsDisponibleLabel)
							.addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(inscritScrollPane, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
								.addContainerGap())
							.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addComponent(formationIncriptionjLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(197))
							.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(mesFormationsjLabel, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(inscriptionComboBox, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
										.addGap(44)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(desinscriptionButton)
								.addGap(38)))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(formationIncriptionjLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(inscriptionComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(mesFormationsjLabel)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(desinscriptionButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addComponent(inscritScrollPane, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(formationsDisponibleLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toutesFormationsScrollPane_1, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		toutesFormationsjtable = new JTable();
		toutesFormationsjtable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date de fin", "Date de d\u00E9but", "Formation", "Nom de l'Organisateur"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		toutesFormationsjtable.getColumnModel().getColumn(1).setPreferredWidth(97);
		toutesFormationsScrollPane_1.setColumnHeaderView(toutesFormationsjtable);
		
		inscriptionsjTable = new JTable();
		inscriptionsjTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inscriptionsjTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nom de l'Organisateur", "Date de fin", "Date de d\u00E9but", "Formation"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		inscriptionsjTable.getColumnModel().getColumn(1).setPreferredWidth(118);
		inscriptionsjTable.getColumnModel().getColumn(2).setPreferredWidth(123);
		inscritScrollPane.setColumnHeaderView(inscriptionsjTable);
		frmAdherent.getContentPane().setLayout(groupLayout);
	}
}
