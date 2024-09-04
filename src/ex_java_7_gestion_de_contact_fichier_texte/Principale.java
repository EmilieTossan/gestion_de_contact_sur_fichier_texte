package ex_java_7_gestion_de_contact_fichier_texte;

import java.io.*;
import java.util.*;

public class Principale {

	// nouveau commentaire
	
	private static final String FICHIER = "contacts.txt";
	private static Scanner entree = new Scanner(System.in);
	
	private static String saisie;
	private static int option;
	
	public static void main(String[] args) throws IOException {
		
		// on crée un nouvel objet, un nouveau fichier txt qu'on nomme contacts
		
		File fichier = new File(FICHIER);
		
		// si le fichier contacts.txt n'est pas créé, on le crée
		
		if (!fichier.exists()) {
			
			boolean fichier_cree = fichier.createNewFile();
			
			if (fichier_cree) System.out.println("Le fichier vient d'être créé.");
		}
		
		// afin de proposer une solution à chaque étape, nous créons une boucle à l'infini
		
		while (true) {
			
			// on propose quatre options
			
			quatreOptions();
			
			// on demande une réponse à l'utilisateur
			
			saisie = saisir();
			
			// la méthode isTrueOption() vérifie si la saisie est bien un chiffre entre 1 et 4
			
			option = choixOption(saisie, "énoncé_de_début");
			
			// proposition des options selon le chiffre choisi par l'utilisateur
			
			System.out.println();
			
			if (option == 1) {
				
				try (
						
				    // FileReader ouvre le fichier "contacts.txt" pour la lecture
				    FileReader filereader = new FileReader(FICHIER);
					
				    // BufferedReader enveloppe FileReader pour une lecture plus efficace des données
					BufferedReader bufferedReader = new BufferedReader(filereader);
				) {
					String line; 
					
					// première lecture pour vérifier s'il y a des contacts
					
					if ((line = bufferedReader.readLine()) != null) {
						
						System.out.println("Liste des contacts enregistrés :");
					    System.out.println();
					    
					    // affichage de chaque ligne avant de lire les autres lignes
					    
					    do {
			                System.out.println(line);
			            } while ((line = bufferedReader.readLine()) != null);
						
					} else {
						
					    System.out.println("Il n'y a pas de contact enregistré.");
						System.out.println();
					}
				    
				} catch (IOException e) {
					
				    System.err.println(e.getMessage());
				}
				
			}
			
			// on crée cet objet pour obtenir la liste des contacts à partir du fichier texte
			
			ContactManager manager = new ContactManager(FICHIER);
			
			if (option == 2) {
				
				// nous créons une boucle infinie tant que l'utilisateur veut ajouter un contact
				
				while (true) {
					
					System.out.println("Entrez un nom de famille :");
					String nom = saisir();
					
					System.out.println("Entrez un prénom :");
					String prenom = saisir();
					
					System.out.println("Entrez un numéro de téléphone :");
					String telephone = saisir();
					
					System.out.println("Entrez une ville :");
					String ville = saisir();
					
					Contact contact = new Contact (nom, prenom, telephone, ville);
					
					// on ajoute le contact dans la liste des contacts existante
					
					manager.ajouterContact(contact);
					
					FileWriter file_writer = new FileWriter(FICHIER); // on ouvre le fichier texte
					PrintWriter print_writer = new PrintWriter(file_writer); // on active l'imprimante
					
					// on enregistre la liste entière des contacts en écrasant le contenu du fichier texte
					
					manager.enregistrer();
					
					print_writer.close(); // on désactive l'imprimante
					file_writer.close(); // on ferme le fichier texte
					
					// on demande à l'utilisateur s'il veut ajouter un nouveau contact
					
					System.out.println("Voulez-vous ajouter un autre contact [oui/non] ?");
					
					// l'utilisateur doit répondre "ok" pour continuer ou doit "entrer" pour passer 
					
					String reponse = saisir();
					
					// on vérifie la réponse donnée par l'utilsateur
					
					while (!reponse.equalsIgnoreCase("oui") && !reponse.equalsIgnoreCase("non")) {
						
						System.out.println();
						System.err.println("Tapez \"oui\" pour supprimer ou \"non\" pour partir.");
						reponse = saisir();
					}
					
					System.out.println();
					
					if (reponse.equalsIgnoreCase("oui")) continue;
					if (reponse.equalsIgnoreCase("non")) break;
					
				}
			}
			
			int index = 0;
			String aModifier = "";
			
			if (option == 3) {
				
				// on prévient s'il a des contacts enregistrés
				
				if (manager.nombreContacts() == 0 ) {
					
					System.out.println("Il n'y a pas de contact à modifier.");
					System.out.println("Choisissez l'option 2 pour ajouter un nouveau contact.");
					System.out.println();
					
				} else {
					
					// on affiche la liste des contacts
					
					manager.afficherTout();
					
					// s'il y a plus d'un contact
					
					if (manager.nombreContacts()  > 1) {
						
						// on demande quel contact l'utilisateur veut modifier
						
						System.out.println("Quel contact voulez-vous modifier ?");
						System.out.println();
						
						// on attend la réponse de l'utilisateur
						
						saisie = saisir();
						
						// on vérifie si la saisie est correcte
						
						int numero = valeurNumerique(saisie);
						
						// et on vérifie si le numéro est disponible
						
						while (!valeurCorrecte(numero, manager.nombreContacts())) {
							
							System.out.println("Choisissez un numéro entre 1 et " + manager.nombreContacts() + " :");
							
							saisie = saisir();
							
							numero = valeurNumerique(saisie);
						}
						
						// on enregistre l'index pour le back
						
						index = numero - 1;
					}
					
					while (true) {
						
						System.out.println();
						System.out.println("Que voulez-vous modifier ?");
						System.out.println();
						
						// liste des coordonnées à modifier
						
						miseAJour();
						
						// l'utilisateur fait un choix
						
						saisie = saisir();
						
						// on vérifie le choix de l'option
						
						option = choixOption(saisie, "étape_de_modification");
						
						System.out.println();
						
						// selon la réponse de l'utilisateur on détermine ce qui doit être modifié
						
						if (option == 1) {
							
							System.out.println("Nouveau nom de famille :");
							aModifier = "nom";
						}
						if (option == 2) {
							
							System.out.println("Nouveau prénom :");
							aModifier = "prenom";
						}
						if (option == 3) {
		
							System.out.println("Nouveau numéro de téléphone :");
							aModifier = "telephone";
						}
						if (option == 4) {
							
							System.out.println("Nouvelle ville :");
							aModifier = "ville";
						}
						
						System.out.println();
						
						// l'utilisateur écrit la nouvelle valeur
						
						String valeur = saisir();
						
						System.out.println();
						
						manager.modifier(index, aModifier, valeur);
						manager.enregistrer();
						
						System.out.println();
						
						System.out.println("Voulez-vous modifier autre chose [oui/non] ?");
						
						// on attend la réponse de l'utilisateur
						
						String reponse = saisir();
						
						// on vérifie sa réponse
						
						while (!reponse.equalsIgnoreCase("oui") && !reponse.equalsIgnoreCase("non")) {
							
							System.err.println("Tapez \"oui\" pour continuer ou \"non\" pour partir.");
							reponse = saisir();
						}
						
						if (reponse.equalsIgnoreCase("oui")) continue;
						
						if (reponse.equalsIgnoreCase("non")) {
							
							System.out.println();
							System.out.println("Ce contact a été modifié :");
							System.out.println();
							
							manager.afficher(index);
							
							System.out.println();
							
							break;
						}
					}
				}
			}

			if (option == 4) {
				
				// on prévient s'il a des contacts enregistrés
				
				if (manager.nombreContacts() == 0 ) {
					
					System.out.println();
					
					System.out.println("Il n'y a pas de contact à supprimer.");
					System.out.println("Choisissez une autre option.");
					
					System.out.println();
					
				} else {
					
					System.out.println();
					
					// on affiche la liste des contacts
					
					manager.afficherTout();
					
					System.out.println();
					
					if (manager.nombreContacts() == 1) {
						
						System.out.println("Voulez-vous supprimer ce contact [oui/non] ?");
						
						// on attend la réponse de l'utilisateur
						
						String reponse = saisir();
						
						// on vérifie sa réponse
						
						while (!reponse.equalsIgnoreCase("oui") && !reponse.equalsIgnoreCase("non")) {
							
							System.err.println("Tapez \"oui\" pour supprimer ou \"non\" pour partir.");
							reponse = saisir();
						}
						
						if (reponse.equalsIgnoreCase("oui")) {
							
							manager.supprimer(index);
							manager.enregistrer();
						}
						
						if (reponse.equalsIgnoreCase("non")) {
							
							System.out.println();
							continue;
						}
						
						
					} else {
						
						System.out.println("Quel contact voulez-vous supprimer ?");	
					
						// on attend le choix de l'utilisateur
						
						saisie = saisir();
						
						// on vérifie si la saisie est correcte
						
						int numero = valeurNumerique(saisie);
						
						// et on vérifie si le numéro est disponible
						
						while (!valeurCorrecte(numero, manager.nombreContacts())) {
							
							System.err.println("Ce contact n'existe pas.");
							
							System.out.println("Choisissez un numéro entre 1 et " + manager.nombreContacts() + " :");
							
							saisie = saisir();
							
							numero = valeurNumerique(saisie);
						}
						
						index = numero - 1;
						
						manager.supprimer(index);
						manager.enregistrer();
					
					}
				}
			}
		}
	}

	private static void quatreOptions() {
		
		System.out.println("[Option 1] Liste des contacts");
		System.out.println("[Option 2] Créer un nouveau contact");
		System.out.println("[Option 3] Modifier un contact");
		System.out.println("[Option 4] Supprimer un contact");
		
		System.out.println();
	}
	
	private static void miseAJour() {
		
		System.out.println("[Option 1] Le nom de famille");
		System.out.println("[Option 2] Le prénom");
		System.out.println("[Option 3] Le numéro de téléphone");
		System.out.println("[Option 4] La ville");
		
		System.out.println();
	}
	
	private static int choixOption(String chaine, String option) {
		
		// on vérifie d'abord si la saisie a une valeur numérique
		
		int valeur = valeurNumerique(chaine);
		
		// tant que la valeur n'est pas comprise entre 1 et 4
		
		while (!valeurCorrecte(valeur, 4)) {
			
			System.out.println();
			System.err.println("L'option " + valeur + " n'existe pas.");
			System.out.println();
			System.out.println("Choissisez une des options suivantes :");
			System.out.println();
			
			// on énonce à nouveau l'énoncé selon l'étape à laquelle se trouve l'utilisateur
			
			if (option == "énoncé_de_début") quatreOptions();
				
			else if (option == "étape_de_modification") miseAJour();
			
			// on attend la réponse de l'utilisateur
		
			chaine = saisir();
			
			// on vérifie si c'est une valeur numérique et on la récupère
			
			valeur = valeurNumerique(chaine);
			
		}
		
		return valeur;
	}
	
	private static boolean valeurCorrecte(int valeur, int nombre) {
		
		if (valeur > 0 && valeur <= nombre) return true;
		
		else return false;
	}

	private static int valeurNumerique(String chaine) {
		
		while (true) {
			
			try {
			
				option = Integer.parseInt(chaine);
				
				break;
				
			} catch (NumberFormatException e) {
				
				boolean letter = false;
				boolean special = false;
				
				for (char c : chaine.toCharArray()) {
					
					if (Character.isLetter(c)) letter = true;
					if (!Character.isLetterOrDigit(c)) special = true;
					
				}
				
				if (letter && special) {
					
					System.err.println("Vous ne pouvez pas utiliser de lettres ou de caractères spéciaux.");
					
				} else if (letter) {
					
					System.err.println("Vous ne pouvez pas utiliser de lettres.");
					
				} else if (special) {
					
					System.err.println("Vous ne pouvez pas utiliser de caractères spéciaux.");
				}
				
				System.out.println();
				System.out.println("Choisissez une option de 1 à 4 :");
				System.out.println();
				
				chaine = saisir();
			}
		}
		
		return option;
	}

	private static String saisir() {
		
		String reponse = "";
		
		while(reponse.isEmpty()) {
			
			reponse = entree.nextLine();
		}
		
		return reponse;
	}

}
