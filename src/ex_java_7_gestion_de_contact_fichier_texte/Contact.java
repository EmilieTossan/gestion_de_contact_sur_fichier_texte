package ex_java_7_gestion_de_contact_fichier_texte;

import java.util.List;

public class Contact {

	private String nom;
	private String prenom;
	private String telephone;
	private String ville;
	
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getVille() {
		return ville;
	}

	public Contact (
		String nom,
		String prenom,
		String telephone,
		String ville
	) {
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.ville = ville;
	}
	
	public static Contact fromString(List<String> details) {
		
		if (details.size() != 4) {
			
			throw new IllegalArgumentException("Vérifiez le contenu de votre fichier de contacts");
		}
		
		return new Contact(
			details.get(0),
			details.get(1),
			details.get(2),
			details.get(3)
		);
	}
}