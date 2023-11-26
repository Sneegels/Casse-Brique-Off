import java.io.*;

public class Partie {
    // Autres membres et méthodes de la classe Partie

    // Fonction de sauvegarde
    public void sauvegarderPartie(String nomFichier) {
        try (ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            sortie.writeObject(this);  // Sauvegarde de l'objet Partie
            System.out.println("Partie sauvegardée avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction de chargement
    public static Partie chargerPartie(String nomFichier) {
        try (ObjectInputStream entree = new ObjectInputStream(new FileInputStream(nomFichier))) {
            Partie partieChargee = (Partie) entree.readObject();  // Chargement de l'objet Partie
            System.out.println("Partie chargée avec succès !");
            return partieChargee;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}