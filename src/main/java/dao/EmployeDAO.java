package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Employe;
import models.Entreprise;
import utils.DatabaseConnection;

public class EmployeDAO {

	public static boolean addEmploye(Employe employe) {
		String query = "INSERT INTO employe (matricule, nomEmp, prenomEmp, fonctionEmp, serviceEmp, dateEmbauche, sexeEmp, salaireBase, id_entreprise) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, employe.getMatricule());
			stmt.setString(2, employe.getNom());
			stmt.setString(3, employe.getPrenom());
			stmt.setString(4, employe.getFonction());
			stmt.setString(5, employe.getService());
			stmt.setDate(6, new java.sql.Date(employe.getDateEmbauche().getTime()));
			stmt.setString(7, employe.getSexe());
			stmt.setDouble(8, employe.getSalaireBase());

			if (employe.getEntreprise() != null) {
				stmt.setInt(9, employe.getEntreprise().getId());
			} else {
				stmt.setNull(9, java.sql.Types.INTEGER);
			}

			int affectedRows = stmt.executeUpdate();

			if (affectedRows > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					employe.setId(rs.getInt(1));
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			System.err.println("Erreur lors de l'ajout de l'employé : " + e.getMessage());
			return false;
		}
	}

	public static List<Employe> getAllEmployes() {
	    List<Employe> employes = new ArrayList<>();
	    String query = "SELECT e.id_emp, e.matricule, e.nomEmp, e.prenomEmp, e.fonctionEmp, e.serviceEmp, " +
	                   "e.dateEmbauche, e.sexeEmp, e.salaireBase, e.id_entreprise, " +
	                   "ent.nomEnt AS nom_entreprise, ent.adresseEnt, ent.chiffreAffaire, ent.dateCreation " +
	                   "FROM employe e " +
	                   "LEFT JOIN entreprise ent ON e.id_entreprise = ent.id_entreprise " +
	                   "ORDER BY e.id_emp DESC";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            // Création de l'objet Employe
	            Employe employe = new Employe(
	                rs.getInt("id_emp"),
	                rs.getString("matricule"),
	                rs.getString("nomEmp"),
	                rs.getString("prenomEmp"),
	                rs.getString("fonctionEmp"),
	                rs.getString("serviceEmp"),
	                rs.getDate("dateEmbauche"),
	                rs.getString("sexeEmp"),
	                rs.getDouble("salaireBase"),
	                null
	            );

	            // Si l'employé a une entreprise associée, on l'ajoute
	            if (rs.getObject("id_entreprise") != null) {
	                Entreprise entreprise = new Entreprise(
	                    rs.getInt("id_entreprise"),
	                    rs.getString("nom_entreprise"),
	                    rs.getString("adresseEnt"),
	                    rs.getDouble("chiffreAffaire"),
	                    rs.getDate("dateCreation")
	                );
	                employe.setEntreprise(entreprise);
	            }

	            // Ajouter l'employé à la liste
	            employes.add(employe);
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de la récupération des employés : " + e.getMessage());
	    }

	    return employes;
	}


	public static Employe getEmployeById(int id) {
        System.out.println("Tentative de récupération de l'employé avec l'ID : " + id);
        String query = "SELECT e.*, ent.id_entreprise, ent.nomEnt as nom_entreprise, " +
                      "ent.adresseEnt, ent.chiffreAffaire, ent.dateCreation " +
                      "FROM employe e " +
                      "LEFT JOIN entreprise ent ON e.id_entreprise = ent.id_entreprise " +
                      "WHERE e.id_emp = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            System.out.println("Exécution de la requête : " + query.replace("?", String.valueOf(id)));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Employé trouvé dans la base de données");
                    Employe employe = new Employe(
                        rs.getInt("id_emp"),
                        rs.getString("matricule"),
                        rs.getString("nomEmp"),
                        rs.getString("prenomEmp"),
                        rs.getString("fonctionEmp"),
                        rs.getString("serviceEmp"),
                        rs.getDate("dateEmbauche"),
                        rs.getString("sexeEmp"),
                        rs.getDouble("salaireBase"),
                        null
                    );

                    if (rs.getObject("id_entreprise") != null) {
                        Entreprise entreprise = new Entreprise(
                            rs.getInt("id_entreprise"),
                            rs.getString("nom_entreprise"),
                            rs.getString("adresseEnt"),
                            rs.getDouble("chiffreAffaire"),
                            rs.getDate("dateCreation")
                        );
                        employe.setEntreprise(entreprise);
                    }

                    return employe;
                } else {
                    System.out.println("Aucun employé trouvé avec l'ID : " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'employé : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

	public static boolean updateEmploye(Employe employe) {
		String sql = "UPDATE employe SET nomEmp = ?, prenomEmp = ?, fonctionEmp = ?, serviceEmp = ?, " +
				"dateEmbauche = ?, sexeEmp = ?, salaireBase = ?, id_entreprise = ? WHERE id_emp = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, employe.getNom());
			pstmt.setString(2, employe.getPrenom());
			pstmt.setString(3, employe.getFonction());
			pstmt.setString(4, employe.getService());
			pstmt.setDate(5, new java.sql.Date(employe.getDateEmbauche().getTime()));
			pstmt.setString(6, employe.getSexe());
			pstmt.setDouble(7, employe.getSalaireBase());

			if (employe.getEntreprise() != null) {
				pstmt.setInt(8, employe.getEntreprise().getId());
			} else {
				pstmt.setNull(8, java.sql.Types.INTEGER);
			}

			pstmt.setInt(9, employe.getId());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Erreur lors de la mise à jour de l'employé : " + e.getMessage());
			return false;
		}
	}

	public static List<Employe> getEmployesByEntreprise(int entrepriseId) {
		List<Employe> employes = new ArrayList<>();
		String query = "SELECT e.*, ent.id_entreprise, ent.nomEnt as nom_entreprise " +
				"FROM employe e " +
				"LEFT JOIN entreprise ent ON e.id_entreprise = ent.id_entreprise " +
				"WHERE e.id_entreprise = ? " +
				"ORDER BY e.id_emp DESC";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, entrepriseId);
			
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Employe employe = new Employe(
						rs.getInt("id_emp"),
						rs.getString("matricule"),
						rs.getString("nomEmp"),
						rs.getString("prenomEmp"),
						rs.getString("fonctionEmp"),
						rs.getString("serviceEmp"),
						rs.getDate("dateEmbauche"),
						rs.getString("sexeEmp"),
						rs.getDouble("salaireBase"),
						null
					);
					
					if (rs.getObject("id_entreprise") != null) {
						Entreprise entreprise = new Entreprise(
							rs.getInt("id_entreprise"),
							rs.getString("nom_entreprise"),
							"", // On n'a pas besoin de tous les détails ici
							0,
							null
						);
						employe.setEntreprise(entreprise);
					}
					
					employes.add(employe);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la récupération des employés par entreprise : " + e.getMessage());
			e.printStackTrace();
		}
		return employes;
	}

	public static String generateMatricule() {
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as count FROM employe")) {

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("count");
				return "EMP" + String.format("%04d", count + 1);
			}

		} catch (SQLException e) {
			System.err.println("Erreur lors de la génération du matricule : " + e.getMessage());
			return "EMP" + System.currentTimeMillis();
		}
		return "EMP" + System.currentTimeMillis();
	}

	public static boolean deleteEmploye(int id) {
		String sql = "DELETE FROM employe WHERE id_emp = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Erreur lors de la suppression de l'employé : " + e.getMessage());
			return false;
		}
	}
}
