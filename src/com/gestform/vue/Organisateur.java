package com.gestform.vue;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import main.resources.TableauModel;
import javax.swing.JButton;

public class Organisateur {
	private JFrame frmOrganisateur;
	private JTextField heureDebutJTextField;
	private JTextField heureFinJTextField;
	private JTable formationsJTable;
	private JComboBox formationsJComboBox;
	private JComboBox jourDebutJComboBox;
	private JComboBox moisDebutJComboBox;
	private JComboBox anneeDebutJComboBox;
	private JComboBox jourFinJComboBox;
	private JComboBox moisFinJComboBox;
	private JComboBox anneeFinJComboBox;
	
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
					// On donne une taille à notre fenêtre
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
	 * @throws SQLException 
	 */
	public Organisateur() throws SQLException {
        mdbc=new MyDBConnect();
        mdbc.init();
        Connection conn=mdbc.getMyConnection();
        try {
			stmt= conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

        initialize();
        remplirFormationsJCombo();
	}

    public ResultSet recupererVosSessions() {
        ResultSet rs=null;
        // ICI
        try{
        rs=stmt.executeQuery("SELECT `formation`.`libelle`, `session`.`dateDebut`, `session`.`dateFin`, `organisateur`.`nom` FROM session, organisateur, formation WHERE `session`.`ORGANISATEUR_idOrganisateur`=`organisateur`.`idOrganisateur` and `session`.`FORMATION_idFormation`=`formation`.`idFormation` and `session`.`ORGANISATEUR_idOrganisateur`="+idOrganisateur+" ORDER BY `dateDebut`");
        //System.out.println(rs);
        }
        catch(SQLException e){
        	e.printStackTrace();
			System.out.println("Problème de requête des sessions");
        }
        
        return rs;    
        }
    // On récupère les idFormations et les libellés
    private void remplirFormationsJCombo() throws SQLException {
        mdbc=new MyDBConnect();
        mdbc.init();
        Connection conn=mdbc.getMyConnection();
        stmt=conn.createStatement();
        
        String req= "SELECT `idFormation`, `libelle` FROM `formation`";
		
		try {
			stmt = conn.createStatement();
        	ResultSet res = stmt.executeQuery(req);
			while(res.next()){
				formationsJComboBox.addItem((res.getString(2))+"  #"+(res.getString(1))); 
				// le nom du jComboBox est jComboName et  <indexe de la colonne > est l'index de la colonne dont vous voulez afficher dans le combobox ,elle peut prendre l'une des valeurs 1,2 . .  
			}
			res.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Problème de requête des formations");
		}
	}
    
    private void ajouterSession() {
        // TODO add your handling code here:
        mdbc=new MyDBConnect();
        mdbc.init();
        Connection conn=mdbc.getMyConnection();
        String jourDebut=this.jourDebutJComboBox.getSelectedItem().toString(),
               moisDebut=this.moisDebutJComboBox.getSelectedItem().toString(),
               anneeDebut=this.anneeDebutJComboBox.getSelectedItem().toString(),
               heureDebut=this.heureDebutJTextField.getText(),
               jourFin=this.jourFinJComboBox.getSelectedItem().toString(),
               moisFin=this.moisFinJComboBox.getSelectedItem().toString(),
               anneeFin=this.anneeFinJComboBox.getSelectedItem().toString(),
               heureFin=this.heureFinJTextField.getText(),
               dateTimeDebut=anneeDebut+"-"+moisDebut+"-"+jourDebut+" "+heureDebut,
               dateTimeFin=anneeFin+"-"+moisFin+"-"+jourFin+" "+heureFin,
       listform=(String)this.formationsJComboBox.getSelectedItem();
       String[] lssi=listform.split("#");
        
        //System.out.println(dateTimeDebut);
       //System.out.println(dateTimeFin);
        //System.out.println(lssi[1]);
       String idMax= null;
       int idMaxSession = 0;
       
       String maxSession="SELECT MAX(`idSession`) FROM `session`";
              try {
            	  stmt=conn.createStatement();
            	  ResultSet resS=stmt.executeQuery(maxSession);
            	  while(resS.next()){
            		  idMax=resS.getString(1);
            	  }
            	  resS.close();
            	  idMaxSession = Integer.parseInt(idMax)+1;
              }
              catch (SQLException ex) {
            	  Logger.getLogger(Organisateur.class.getName()).log(Level.SEVERE, null, ex);
            	  System.out.println("Problème de récupération de l'idSession MAXIMUM de la table session dans la base de données");
              }
              // INSERT INTO `session`(`idSession`, `dateDebut`, `dateFin`, `ORGANISATEUR_idOrganisateur`, `FORMATION_idFormation`) VALUES (5,'2017-04-14 16:00:00','2017-04-14 18:00:00',1,1)
               String req="INSERT INTO `session` " +
					"(`idSession`, `dateDebut`, `dateFin`, `ORGANISATEUR_idOrganisateur`, `FORMATION_idFormation`) " +
					"VALUES ("+idMaxSession+", '"+dateTimeDebut+"', '"+dateTimeFin+"', "+idOrganisateur+", "+lssi[1]+")";     
              try {
              stmt = conn.createStatement();
              int done=stmt.executeUpdate(req);
              //res.close();
              } catch (SQLException e) {
            	  e.printStackTrace();
            	  System.out.println("Problème d'insertion de la session dans la base de données");
              }
              //getContentPane().removeAll();
              frmOrganisateur.dispose();
              //getInscriptionFormations();
              //frmOrganisateur.setVisible(false);
              //frmOrganisateur.setVisible(false);
              //frmOrganisateur.revalidate();
              //frmOrganisateur.repaint();
              initialize();
              frmOrganisateur.setVisible(true);
        try {
            this.remplirFormationsJCombo();
        }
        catch (SQLException ex) {
            Logger.getLogger(Organisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
        
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOrganisateur = new JFrame();
		frmOrganisateur.setTitle("Organisateur");
		frmOrganisateur.setBounds(100, 100, 768, 576);
		frmOrganisateur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel sessionJLabel = new JLabel("Crée une session :");
		
		JLabel formationsJLabel = new JLabel("Formations :");
		
		formationsJComboBox = new JComboBox();
		
		JLabel dateDebutJLabel = new JLabel("Date de début");
		
		JLabel jourDebutJLabel = new JLabel("Jour :");
		
		jourDebutJComboBox = new JComboBox();
		jourDebutJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
		
		JLabel moisDebutJLabel = new JLabel("Mois :");
		
		moisDebutJComboBox = new JComboBox();
		moisDebutJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		
		JLabel anneeDebutJLabel = new JLabel("Année :");
		
		anneeDebutJComboBox = new JComboBox();
		anneeDebutJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2017", "2018", "2019" }));
		
		JLabel heureDebutJLabel = new JLabel("Heure de début :");
		
		heureDebutJTextField = new JTextField();
		heureDebutJTextField.setHorizontalAlignment(SwingConstants.CENTER);
		heureDebutJTextField.setText("08:00");
		heureDebutJTextField.setColumns(10);
		
		JLabel dateFinJLabel = new JLabel("Date de fin :");
		
		JLabel jourFinJLabel = new JLabel("Jour :");
		
		jourFinJComboBox = new JComboBox();
		jourFinJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
		
		JLabel moisFinJLabel = new JLabel("Mois :");
		
		moisFinJComboBox = new JComboBox();
		moisFinJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
		
		JLabel anneeFinJLabel = new JLabel("Année :");
		
		anneeFinJComboBox = new JComboBox();
		anneeFinJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2017", "2018", "2019" }));
		
		JLabel heureFinJLabel = new JLabel("Heure de fin :");
		
		heureFinJTextField = new JTextField();
		heureFinJTextField.setHorizontalAlignment(SwingConstants.CENTER);
		heureFinJTextField.setText("16:00");
		heureFinJTextField.setColumns(10);
		
		JLabel vosSessionsJLabel = new JLabel("Vos sessions :");
		
		JSeparator JSeparator = new JSeparator();
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton ajouterSessionButton = new JButton("Ajouter une session");
		ajouterSessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterSession();
            }
        });
		GroupLayout groupLayout = new GroupLayout(frmOrganisateur.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(sessionJLabel)
										.addComponent(formationsJComboBox, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(53)
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(jourDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(jourDebutJLabel))
											.addGap(18)
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(moisDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(moisDebutJLabel))
											.addGap(10)
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(anneeDebutJComboBox, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
												.addComponent(anneeDebutJLabel)))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(100)
											.addComponent(dateDebutJLabel))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(30)
									.addComponent(formationsJLabel)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(25)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(jourFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(moisFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(anneeFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(jourFinJLabel)
											.addGap(18)
											.addComponent(moisFinJLabel)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(anneeFinJLabel))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(66)
									.addComponent(dateFinJLabel))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(vosSessionsJLabel))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(256)
								.addComponent(ajouterSessionButton)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(JSeparator, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(60)
								.addComponent(heureDebutJLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(heureDebutJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(44)
								.addComponent(heureFinJLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(heureFinJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 926, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sessionJLabel)
						.addComponent(dateFinJLabel)
						.addComponent(dateDebutJLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(formationsJLabel)
						.addComponent(jourDebutJLabel)
						.addComponent(anneeDebutJLabel)
						.addComponent(jourFinJLabel)
						.addComponent(moisFinJLabel)
						.addComponent(anneeFinJLabel)
						.addComponent(moisDebutJLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(formationsJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(anneeDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jourDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jourFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(moisFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(anneeFinJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(moisDebutJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(heureDebutJLabel)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(heureDebutJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(heureFinJLabel)
							.addComponent(heureFinJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(JSeparator, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(vosSessionsJLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ajouterSessionButton)))
					.addGap(8)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		formationsJTable = new JTable();
		ResultSet rs=recupererVosSessions();
		formationsJTable.setModel(new TableauModel(rs));
		mdbc.close(rs);
		scrollPane.setViewportView(formationsJTable);
		frmOrganisateur.getContentPane().setLayout(groupLayout);
	}
}
