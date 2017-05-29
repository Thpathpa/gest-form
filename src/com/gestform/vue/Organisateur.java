package com.gestform.vue;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.resources.MyDBConnect;

public class Organisateur {
// INSERT INTO `session`(`idSession`, `dateDebut`, `dateFin`, `ORGANISATEUR_idOrganisateur`, `FORMATION_idFormation`) VALUES (5,'2017-04-14 16:00:00','2017-04-14 18:00:00',1,1)
	private JFrame frmOrganisateur;
	private JTextField heureDebutJTextField;
	private JTextField heureFinJTextField;
	private JTable formationsJTable;
	
	public static String idOrganisateur;
	
	MyDBConnect mdbc;
	Statement stmt; 
	ResultSet rs; 

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Organisateur window = new Organisateur();
					// On lui donne une taille à notre fenêtre
					window.frmOrganisateur.setSize(1000, 800);
					window.frmOrganisateur.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Organisateur() {
        mdbc=new MyDBConnect();
        mdbc.init();
        Connection conn=mdbc.getMyConnection();
        stmt= conn.createStatement(); 

        initialize();
        remplir_jCBFormations();
	}

    public ResultSet getResultFromDISPO() {
        ResultSet rs=null;
        
        try{
        rs=stmt.executeQuery(
        "SELECT `FORMATION`.`Motif`,`DateDebut`,`FORMATION`.`DUREE`,`NBMAX`,`NBMIN`,`VALID`,`ORGANISATEUR`.`Nom` FROM veyre_ppe2.SESSION, veyre_ppe2.ORGANISATEUR, veyre_ppe2.FORMATION WHERE `SESSION`.`ORGANISATEUR_idOrganisateur`=`ORGANISATEUR`.`idOrganisateur` and `SESSION`.`FORMATION_idFormation`=`FORMATION`.`idFORMATION` and `SESSION`.`ORGANISATEUR_idOrganisateur`="+GestForm.id+" ORDER BY  `DateDebut`"
        );
        //System.out.println(rs);
        
        }
        catch(SQLException e){
        	e.printStackTrace();
			System.out.println("Problème de requête des formations");
        }
        
        return rs;    
        }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOrganisateur = new JFrame();
		frmOrganisateur.setTitle("Organisateur");
		frmOrganisateur.setBounds(100, 100, 450, 300);
		frmOrganisateur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel sessionJLabel = new JLabel("Crée une session :");
		
		JLabel formationsJLabel = new JLabel("Formations :");
		
		JComboBox formationsJComboBox = new JComboBox();
		
		JLabel dateDebutJLabel = new JLabel("Date de début");
		
		JLabel jourDebutJLabel = new JLabel("Jour :");
		
		JComboBox jourDebutJComboBox = new JComboBox();
		
		JLabel moisDebutJLabel = new JLabel("Mois :");
		
		JComboBox moisDebutJComboBox = new JComboBox();
		
		JLabel anneeDebutJLabel = new JLabel("Année :");
		
		JComboBox anneeDebutJComboBox = new JComboBox();
		
		JLabel heureDebutJLabel = new JLabel("Heure de début :");
		
		heureDebutJTextField = new JTextField();
		heureDebutJTextField.setHorizontalAlignment(SwingConstants.CENTER);
		heureDebutJTextField.setText("08:00");
		heureDebutJTextField.setColumns(10);
		
		JLabel dateFinJLabel = new JLabel("Date de fin :");
		
		JLabel jourFinJLabel = new JLabel("Jour :");
		
		JComboBox jourFinJComboBox = new JComboBox();
		
		JLabel moisFinJLabel = new JLabel("Mois :");
		
		JComboBox moisFinJComboBox = new JComboBox();
		
		JLabel anneeFinJLabel = new JLabel("Année :");
		
		JComboBox anneeFinJComboBox = new JComboBox();
		anneeFinJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2017", "2018", "2019" }));
		
		JLabel heureFinJLabel = new JLabel("Heure de fin :");
		
		heureFinJTextField = new JTextField();
		heureFinJTextField.setHorizontalAlignment(SwingConstants.CENTER);
		heureFinJTextField.setText("16:00");
		heureFinJTextField.setColumns(10);
		
		JLabel vosSessionsJLabel = new JLabel("Vos sessions :");
		
		JSeparator JSeparator = new JSeparator();
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frmOrganisateur.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(sessionJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(formationsJComboBox, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(jourDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(moisDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(anneeDebutJComboBox, 0, 59, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(dateDebutJLabel)
									.addGap(31))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(30)
							.addComponent(formationsJLabel)
							.addGap(39)
							.addComponent(jourDebutJLabel)
							.addGap(18)
							.addComponent(moisDebutJLabel)
							.addGap(18)
							.addComponent(anneeDebutJLabel)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addComponent(dateFinJLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(jourFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(moisFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(anneeFinJComboBox, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(jourFinJLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(moisFinJLabel)
									.addGap(18)
									.addComponent(anneeFinJLabel)))))
					.addGap(18))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(vosSessionsJLabel)
					.addContainerGap(365, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(JSeparator, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(60)
							.addComponent(heureDebutJLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(heureDebutJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(44)
							.addComponent(heureFinJLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(heureFinJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sessionJLabel)
						.addComponent(dateDebutJLabel)
						.addComponent(dateFinJLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(formationsJLabel)
						.addComponent(jourDebutJLabel)
						.addComponent(moisDebutJLabel)
						.addComponent(anneeDebutJLabel)
						.addComponent(jourFinJLabel)
						.addComponent(moisFinJLabel)
						.addComponent(anneeFinJLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(formationsJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jourDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(moisDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(anneeDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jourFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(moisFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(anneeFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(heureDebutJLabel)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(heureDebutJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(heureFinJLabel)
							.addComponent(heureFinJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(JSeparator, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(vosSessionsJLabel)
					.addGap(8)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		formationsJTable = new JTable();
		formationsJTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date de d\u00E9but", "Formation", "Formation"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setColumnHeaderView(formationsJTable);
		frmOrganisateur.getContentPane().setLayout(groupLayout);
	}
}
