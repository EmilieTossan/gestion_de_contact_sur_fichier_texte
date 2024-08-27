package ex_java_7_gestion_de_contact_fichier_texte;

import java.io.*;
import java.util.*;

public class ContactManager {

	// on prépare une liste de contacts
	
	private List<Contact> contacts;
	private static String FICHIER;
	
	public ContactManager(String fichier) {
		
		// on crée la liste sous la forme d'un tableau
		
		contacts = new ArrayList<>();
		
		// on enregistre le nom du fichier comme ça on n'a pas à le redemander
		
		ContactManager.FICHIER = fichier;
		
		// on remplit la liste de contacts avec le contenu du fichier texte
		
		charger();
	}
	
	public void ajouterContact(Contact contact) {
		
		// on ajoute un contact à la liste de contacts créée
		
		contacts.add(contact);
	}
	
	// ce getter va servir pour vérifier le nombre de contacts avant modification ou suppression
	
	public int nombreContacts() {
		
		return contacts.size();
	}
	
	// la méthode enregistrer() écrase les informations de fichier txt avec toute la liste de contacts
	
	public void enregistrer() {
		
		try (
			BufferedWriter writer = 
				new BufferedWriter(
					new FileWriter(FICHIER)
				)
		) {
			
			// on écrit les informations de chaque contact dans le fichier texte
			
			for (Contact contact : contacts) {
				
				writer.write(contact.getNom());
				writer.newLine();
				
				writer.write(contact.getPrenom());
				writer.newLine();
				
				writer.write(contact.getTelephone());
				writer.newLine();
				
				writer.write(contact.getVille());
				writer.newLine();
				
				writer.newLine();
			}
			
		} catch (IOException e) {
			
			System.err.println(e.getMessage());
		}
	}
	
	// la méthode charger() permet de charger tous les contacts à partir du fichier texte

	public void charger() {
		
		contacts.clear();
		
		try (
			BufferedReader reader = 
				new BufferedReader(
					new FileReader(FICHIER))
		) {
			
			// on crée une liste pour récupérer les informations de chaque contact
			
			List<String> details = new ArrayList<>();
			
			// tant que la ligne lu par BufferedReader n'est pas nulle
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				// si la ligne est vide 
				
				if (line.trim().isEmpty()) {
					
					// et que les coordonnées ne le sont pas 
					
					if (!details.isEmpty()) {
						
						// on enregistre les informations du contact dans la liste
						
						contacts.add(Contact.fromString(details));
						
						// on vide la liste pour la remplir avec les informations d'un nouveau contact
						
						details.clear();
					}
					
				// si la ligne n'est pas vide, on l'ajoute dans les coordonnées du contact
				
				} else {
					
					details.add(line);
					
				}
				
			}
			
			if (!details.isEmpty()) {
				
				contacts.add(Contact.fromString(details));
			}
			
		} catch (IOException e) {
			
			System.err.println(e.getMessage());
		}
		
	}
	
	// la méthode afficher() permet d'afficher un contact à la fois
	
	public void afficher(int i) {
		
		Contact contact = contacts.get(i);
		
		System.out.println("Contact [" + (i + 1) + "]");
		
		System.out.println();
		
		System.out.println("Nom de famille : " + contact.getNom());
		System.out.println("Prénom : " + contact.getPrenom());
		System.out.println("N° de téléphone : " + contact.getTelephone());
		System.out.println("Ville : " + contact.getVille());
		
	}
	
	// la méthode afficherTout() permet d'afficher la liste de contacts chargée avec la méthode load()
	
	public void afficherTout() {
			
		for (int i = 0; i < contacts.size(); i++) {
			
			Contact contact = contacts.get(i);
			
			System.out.println("Contact [" + (i + 1) + "]");
			
			System.out.println();
			
			System.out.println("Nom de famille : " + contact.getNom());
			System.out.println("Prénom : " + contact.getPrenom());
			System.out.println("N° de téléphone : " + contact.getTelephone());
			System.out.println("Ville : " + contact.getVille());
			
		}
		
	}

	public void modifier(
		int index,
		String detailAModifer,
		String nouvelleValeur
	) {
		
		if (index >= 0 && index < contacts.size()) {
			
			Contact contact = contacts.get(index);
			Contact contactMisAJour;
			
			switch (detailAModifer) {
			
			case "nom" :
				contactMisAJour = new Contact(
					nouvelleValeur,
					contact.getPrenom(),
					contact.getTelephone(),
					contact.getVille()
				);
				break;
				
			case "prenom" :
				contactMisAJour = new Contact(
					contact.getNom(),
					nouvelleValeur,
					contact.getTelephone(),
					contact.getVille()
				);
				break;
				
			case "telephone" :
				contactMisAJour = new Contact(
					contact.getNom(),
					contact.getPrenom(),
					nouvelleValeur,
					contact.getVille()
				);
				break;
				
			case "ville" :
				contactMisAJour = new Contact(
					contact.getNom(),
					contact.getPrenom(),
					contact.getTelephone(),
					nouvelleValeur
				);
				break;
				
			default:
				System.err.print("Ce changement n'est pas possible.");
				return;
			}
			
			contacts.set(index, contactMisAJour);
			
			System.out.println("Ce contact a été modifié.");
			
		} else {
			
			System.err.print("Ce contact n'existe pas.");
			
		}
	}
	
	public void supprimer(int index) {
		
		if (index >= 0 && index < contacts.size() ) {
			
			contacts.remove(index);
			
			System.out.println("Ce contact vient d'être supprimé.");
			System.out.println();
			
		} else {
			
			System.err.println("Ce contact n'existe pas.");
		}
	}
}
