package villagegaulois;

import java.util.Iterator;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;
	
	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			
			for(int i = 0; i < nombreEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if(indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
			else {
				System.out.println("L'indice de l'étal est invalide.");
			}
		}
		
		public int trouverEtalLibre() {
			for(int i = 0; i < etals.length; i++) {
				if(!(etals[i].isEtalOccupe()))
						return i;
			}
			
			return -1;
		}
		
		public Etal[] trouverEtal(String produit) {
			Etal[] etalsAProduit = new Etal[etals.length];
			
			int countEtals = 0;
			int countEtalsAProduit = 0;
			
			while(countEtals < etals.length) {
				if(etals[countEtals].contientProduit(produit)) {
					etalsAProduit[countEtalsAProduit] = etals[countEtals];
					countEtalsAProduit++;
				}
				countEtals++;
			}
			
			return etalsAProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				 if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			
			return null;
		}
		
		public void afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalsVides = 0;
			
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}
				else {
					nbEtalsVides++;
				}
			}
			
			if (nbEtalsVides > 0) {
				chaine.append("Il reste " + nbEtalsVides + " étals non utilisés dans le marché.\n");
			}
			
			System.out.println(chaine.toString());
		}
	}
	

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder(
				"Le vendeur " + vendeur.getNom() + " quitte son Ã©tal, ");
		int produitVendu = quantiteDebutMarche - quantite;
		if (produitVendu > 0) {
			chaine.append(
					"il a vendu " + produitVendu + " parmi " + produit + ".\n");
		} else {
			chaine.append("il n'a malheureusement rien vendu.\n");
		}
		return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'Ã©tal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'Ã©tal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		if (etalOccupe) {
			StringBuilder chaine = new StringBuilder();
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
					+ " " + produit + " Ã  " + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", "
						+ acheteur.getNom() + " vide l'Ã©tal de "
						+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom()
						+ ", est ravi de tout trouver sur l'Ã©tal de "
						+ vendeur.getNom() + "\n");
			}
			return chaine.toString();
		}
		return null;
	}

	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}

}
