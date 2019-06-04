package com.gestform.vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TableModelListener;

import java.awt.Window.Type;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import main.resources.MyDBConnect;
import main.resources.TableauModel;

import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Adherent {

	private JFrame frmAdherent;
	private JPanel getContentPane;
	private JTable inscriptionsjTable;
	private JTable toutesFormationsjtable;
	private JComboBox inscriptionJComboBox;
	private JComboBox desinscriptionJComboBox;
	
	public static String idLicence;
	
	static MyDBConnect mdbc;
    Statement stmt; 
    ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Adherent window = new Adherent();
					// On donne une taille à notre fenêtre
					window.frmAdherent.setSize(1000, 800);
					window.frmAdherent.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public Adherent() throws SQLException {
		
        mdbc=new MyDBConnect();
        mdbc.init();
        Connection conn=mdbc.getMyConnection();
        try {
			stmt=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize(); // On créé les composants de la fenêtre
		remplirFormationsJCombo();
		remplirFormationsInscriptionsJCombo();
	}
	// on récupère la liste complète des formations
	public ResultSet getToutesFormations() {
        String rSQL = "SELECT `formation`.`libelle`, `organisateur`.`nom`, `session`.`dateDebut`, `session`.`dateFin` \n" +
        		"FROM `session`, `organisateur`, `formation` \n" + 
        		"WHERE `session`.`ORGANISATEUR_idOrganisateur`=`organisateur`.`idOrganisateur` and `session`.`FORMATION_idFormation`=`formation`.`idFormation`";
        ResultSet rs=null;
        try {
            rs=stmt.executeQuery(rSQL);
        }
        catch(SQLException e){
        	System.out.println(e);
        	}
        
        return rs;
        
    }
	// on récupère la liste des formations où l'adherent est inscrit
    public ResultSet getInscriptionFormations() {
	    ResultSet rs=null; 
	    
	   String rSQL = "SELECT `formation`.`libelle`, `organisateur`.`nom`, `session`.`dateDebut`, `session`.`dateFin`  \n" +
	"FROM `session`, `adherent_has_session`, `formation`, `organisateur`\n" +
	"WHERE `session`.`ORGANISATEUR_idOrganisateur`=`organisateur`.`idOrganisateur` \n" +
	"AND `session`.`FORMATION_idFormation`=`formation`.`idFormation`\n" +
	"AND `session`.`idSession`=`adherent_has_session`.`session_idsession`\n" +
	"AND `adherent_has_session`.`adherent_idLicence`="+ idLicence;
	   
	   try {
	       rs=stmt.executeQuery(rSQL);
	   }
	   catch(SQLException e){
		   System.out.println(e);
	   }
   return rs;
   }   

	// On rempli toutes les sessions de la bases de données dans la jcombobox
	private void remplirFormationsJCombo() throws SQLException {
		
			inscriptionJComboBox.removeAllItems();
		
			mdbc=new MyDBConnect();
	        mdbc.init();
	        Connection conn=mdbc.getMyConnection();
	        stmt=conn.createStatement();
	        
	        
	   String req= "SELECT `formation`.`libelle`, `session`.`dateDebut`, `session`.`idSession` \n" +
		"FROM session,\n" +
		"formation\n" +
		"WHERE `session`.`FORMATION_idFormation`=`formation`.`idFormation`"; 
			
			try {
		        stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery(req);
				while(res.next()){
					inscriptionJComboBox.addItem((res.getString(1))+" "+(res.getString(2)+" #"+(res.getString(3))));
					// le nom du jComboBox est jComboName et  <indexe de la colonne > est l'index de la colonne dont vous voulez afficher dans le combobox ,elle peut prendre l'une des valeurs 1,2 . .  
					}
				res.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Problème de requête des formations");
			}	
			}
	// On rempli toutes les sessions de la bases de données dans la jcombobox
	private void remplirFormationsInscriptionsJCombo() throws SQLException {
			desinscriptionJComboBox.removeAllItems();
		
			mdbc=new MyDBConnect();
	        mdbc.init();
	        Connection conn=mdbc.getMyConnection();
	        stmt=conn.createStatement();
	        
	           
	   String req= "SELECT `formation`.`libelle`, `session`.`dateDebut`, `session`.`idSession` \n" +
		"FROM session,\n" +
		"formation,\n" +
		"adherent_has_session\n" +
	   "WHERE `session`.`FORMATION_idFormation`=`formation`.`idFormation`\n" +
				"AND `session`.`idSession`=`adherent_has_session`.`session_idsession`\n" +
				"AND `adherent_has_session`.`adherent_idLicence`="+ idLicence;
			try {
		        stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery(req);
				while(res.next()){
					desinscriptionJComboBox.addItem((res.getString(1))+" "+(res.getString(2)+" #"+(res.getString(3))));     
					// le nom du jComboBox est jComboName et  <indexe de la colonne > est l'index de la colonne dont vous voulez afficher dans le combobox ,elle peut prendre l'une des valeurs 1,2 . .  
					}
				res.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Problème de requête des inscriptions");	
			}
			}
	// on inscrit l'adherent à la session selectionnée
	private void inscriptionAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValidBActionPerformed
		
		String countInscrit="SELECT Count(adherent_IdLicence) as nb "
		+ "FROM adherent_has_session "
		+ "INNER JOIN session "
		+ "ON session.idSession = adherent_has_session.session_idsession " 
		+ "WHERE adherent_has_session.adherent_IdLicence = " + idLicence + " "
		+ "AND YEAR(session.dateDebut) = YEAR(NOW()) " 
		+ "AND inscription = 1 ;";
		try {
            ResultSet result=stmt.executeQuery(countInscrit); 
            result.next(); // Récupère le premier résultat
            int count = result.getInt("nb"); // Le count
            
            boolean isInscrit = count < 5;
            
            String[] lis;
            String liststring = (String)inscriptionJComboBox.getSelectedItem();
            lis=liststring.split("#");
            String insertStr="INSERT INTO `adherent_has_session`(`adherent_idLicence`, `session_idsession`, `inscription`, `dateInscription` ) VALUES (" + idLicence + ","+lis[1]+"," + isInscrit + ", NOW());";

            int done=stmt.executeUpdate(insertStr);           
        } catch (SQLException ex) {
            Logger.getLogger(Adherent.class.getName()).log(Level.SEVERE, null, ex);
        }

        //SwingUtilities.updateComponentTreeUI(frmAdherent);
        //getContentPane.removeAll();
		frmAdherent.dispose();
		//getInscriptionFormations();
		//frame.setVisible(false);
		//frmAdherent.setVisible(false);
		//frmAdherent.revalidate();
		//frmAdherent.repaint();
		initialize();
		frmAdherent.setVisible(true);
        
        try {
            remplirFormationsJCombo();
            remplirFormationsInscriptionsJCombo();
        	getInscriptionFormations();
        } catch (SQLException ex) {
            Logger.getLogger(Adherent.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
	
	// on désinscrit l'adherent à la session selectionnée
	private void desinscriptionAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValidBActionPerformed

        String[] lis;
        String liststring = (String)desinscriptionJComboBox.getSelectedItem();
        lis=liststring.split("#");
        // On recupère la donnée split dans un String pour la requête sql
        String lis1 = lis[1];
        //System.out.println(lis[1]);
        String insertStr="DELETE FROM `adherent_has_session` WHERE `adherent_idLicence`=" + idLicence + " AND `session_idsession`="+ lis1;
        try {
            ResultSet done=stmt.executeQuery(insertStr);
        } catch (SQLException ex) {
            Logger.getLogger(Adherent.class.getName()).log(Level.SEVERE, null, ex);
        }
        frmAdherent.dispose();
        //getInscriptionFormations();
        //frmAdherent.setVisible(false);
		initialize();
		frmAdherent.setVisible(true);
        //frmAdherent.removeAll();
        //frmAdherent.getContentPane().removeAll();
        //frmAdherent.removeAll();
        //SwingUtilities.updateComponentTreeUI(frmAdherent);
        
        try {
        	remplirFormationsJCombo();
        	remplirFormationsInscriptionsJCombo();
        	getInscriptionFormations();
        } catch (SQLException ex) {
            Logger.getLogger(Adherent.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdherent = new JFrame();
		frmAdherent.setTitle("Adherent");
		frmAdherent.setResizable(true);
		frmAdherent.setBounds(100, 100, 731, 629);
		frmAdherent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel formationIncriptionJLabel = new JLabel("Choisissez la formation à laquelle vous inscrire");
		
		inscriptionJComboBox = new JComboBox();
		
		JLabel mesFormationsJLabel = new JLabel("Les formations aux quelles vous êtes inscrit");
		
		JScrollPane inscritScrollPane = new JScrollPane();
		
		JButton desinscriptionButton = new JButton("Se désinscrire");
		desinscriptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desinscriptionAction(evt);
            }
        });
		
		JLabel formationsDisponibleLabel = new JLabel("Tous les formations disponibles");
		
		JScrollPane toutesFormationsScrollPane = new JScrollPane();
		
		JButton inscriptionButton = new JButton("S'inscrire");
		inscriptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inscriptionAction(evt);
            }
        });
		
		JLabel desinscriptionLabel = new JLabel("Choisissez la formation à retirer");
		
		desinscriptionJComboBox = new JComboBox();
		
		// NEW Deconnexion
		JButton btnDeconnexion = new JButton("Se déconnecter");
		btnDeconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// on ferme la frame courante
				
				frmAdherent.dispose();
                // On ouvre la classe Login
                //Login login = null;
                //login.loginInitialize();
                Login.main();
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmAdherent.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(formationsDisponibleLabel)
							.addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(toutesFormationsScrollPane, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
								.addContainerGap())
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(inscritScrollPane, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
								.addContainerGap())
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(formationIncriptionJLabel, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(mesFormationsJLabel, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
										.addGap(114)))
								.addGap(197))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(inscriptionJComboBox, 0, 421, Short.MAX_VALUE)
									.addComponent(desinscriptionLabel)
									.addComponent(desinscriptionJComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(inscriptionButton)
									.addComponent(desinscriptionButton))
								.addGap(119)))))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(490, Short.MAX_VALUE)
					.addComponent(btnDeconnexion)
					.addGap(112))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(17)
					.addComponent(btnDeconnexion)
					.addGap(8)
					.addComponent(formationIncriptionJLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(inscriptionJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(inscriptionButton))
					.addGap(7)
					.addComponent(desinscriptionLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(desinscriptionJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(desinscriptionButton))
					.addPreferredGap(ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
					.addComponent(mesFormationsJLabel)
					.addGap(1)
					.addComponent(inscritScrollPane, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(formationsDisponibleLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toutesFormationsScrollPane, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		toutesFormationsjtable = new JTable();
		toutesFormationsjtable.setModel(new TableauModel(getToutesFormations()));/*
				new Object[][] {
				},
				new String[] {
						"Formation", "Nom de l'Organisateur", "Date de fin", "Date de d\u00E9but" 
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});*/
		/*
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
		});*//*
		toutesFormationsjtable.getColumnModel().getColumn(1).setPreferredWidth(97);
		toutesFormationsScrollPane.setColumnHeaderView(toutesFormationsjtable);*/
		toutesFormationsScrollPane.setViewportView(toutesFormationsjtable);
		
		inscriptionsjTable = new JTable();
		inscriptionsjTable.setModel(new TableauModel(getInscriptionFormations()));
		/*inscriptionsjTable.setModel(new DefaultTableModel(
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
		*/
		inscritScrollPane.setViewportView(inscriptionsjTable);
		frmAdherent.getContentPane().setLayout(groupLayout);
	}
}
