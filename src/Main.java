import Camping.*;
import java.util.Scanner;
import java.sql.*;

void main() {
    // Url de la base de données
    String URL = "jdbc:mysql://172.17.0.7/apCamping";
    // login et mdp de la base
    String LOGIN = "Himation";
    String PASSWORD = "Camping@Anee_2027";


    ArrayList<Animateur> liste_Animateur = new ArrayList<Animateur>();
    ArrayList<Animation> liste_Animation = new ArrayList<Animation>();

    try
    {// ouverture d'une connexion
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        System.out.println("Connexion réussie");
        Statement stmt = connexion.createStatement();

        // Partie Animateur
        String requete_animateur = "SELECT * FROM animateur";
        ResultSet res_animateur = stmt.executeQuery(requete_animateur);

        if (res_animateur.next())
        {
            String[] coordonnees = new String[] {
                    res_animateur.getString("Mail"), res_animateur.getString("Tel"), res_animateur.getString("CodePostal"), res_animateur.getString("Ville"), res_animateur.getString("Addresse")
            };
            liste_Animateur.add(new Animateur(res_animateur.getInt("id"),res_animateur.getString("nom"),res_animateur.getString("prenom"),coordonnees));
        }

        else
        {
            System.out.println("Pas de résultat pour cette requête");
        }

        //Partie Animation
        String requete_animation = "SELECT * FROM animation";
        ResultSet res_animation = stmt.executeQuery(requete_animation);


        //Fermeture des connections et requetes
        res_animateur.close();
        stmt.close();
    }
    catch (SQLException exc)
    {
        System.out.println("ERREUR"+exc.getMessage());
    }




}

public void gestionAnimateur(){}
public void addAnimateur(){
    Scanner sc =new Scanner(System.in);
    String[] coordonee=new String[5];
    System.out.print("Mail: ");
    coordonee[0]=sc.next();
    System.out.print("Code Postale: ");
    coordonee[3]=sc.next();
    System.out.print("Ville: ");
    coordonee[2]=sc.next();
    System.out.print("Adresse: ");
    coordonee[1]=sc.next();
    System.out.print("Telephone: ");
    coordonee[4]=sc.next();
    System.out.print("Id: ");
    int id=sc.nextInt();
    System.out.print("Nom: ");
    String  nom=sc.next();
    System.out.print("Prenom: ");
    String  prenom=sc.next();

    Animateur newA=new Animateur(id, nom, prenom,coordonee);
    String[] co=newA.getCoordonees();
    System.out.print("\nId: "+newA.getId()+"\nNom: "+newA.getNom()+"\nCoordonée: "+co[0]);
    for (int i = 1; i < newA.getCoordonees().length; i++) {
        System.out.print(", "+co[i]);
    }
}