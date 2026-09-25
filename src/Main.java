import Camping.*;
import java.util.*;
import java.sql.*;

public  Scanner sc =new Scanner(System.in);
private List<Animateur> animateursMain=new ArrayList<>();
private List<Animation> animationMain=new ArrayList<>();

public String URL = "jdbc:mysql://172.17.0.7:3306/apCamping";
public String LOGIN = "Himation";
public String PASSWORD = "Camping@Anee_2027";
public boolean bddError=true;

void main() {
    if(bddError){
        System.out.println("Connexion: ");
        try {
            Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Connexion réussie");
            bddError=false;
        }
        catch (SQLException exc) {
            System.out.print("erreur: ");
            System.out.println(exc.getMessage());
        }
    }
    System.out.println("\n1: Gestion Animateur | 2: Gestion Animation | 3: Planification");
    System.out.print("Saisissez un chiffre:");
    int index=sc.nextInt();
    switch (index){
        case 1:gestionAnimateur();
        case 2:gestionAnimation();
        default:
            main();
    }
}

//ANIMATEUR

public void gestionAnimateur(){
    System.out.println("\n1: Ajouter un Animateur | 2: Liste d'animateurs | 3: Suprimer un Animateur | 4: Retour");
    int index = sc.nextInt();
    switch (index){
        case 1: InterfaceAddAnimateur(); break;
        case 2: getAnimateurListe(); break;
        case 3: InterfaceDelAnimateur(); break;
        case 4: main(); break;
        default:
            System.out.println("Saisissez un chiffre correct");
            gestionAnimateur();
    }
}

public void InterfaceAddAnimateur(){
    System.out.print("\nAjout d'Animateur:\nNom: ");
    String nom = sc.next();
    System.out.print("Prénom: ");
    String prenom = sc.next();
    String[] coordonee = new String[5];
    System.out.print("Mail: ");
    coordonee[0] = sc.next();
    System.out.print("Telephone: ");
    coordonee[4] = sc.next();
    System.out.print("Code Postale: ");
    coordonee[3] = sc.next();
    System.out.print("Ville: ");
    coordonee[2] = sc.next();
    System.out.print("Adresse: ");
    coordonee[1] = sc.next();

    Animateur newA = new Animateur(animateursMain.size(), nom, prenom, coordonee);
    createAnimateur(newA);
    animateursMain.add(newA);
    afficherAnimateur(newA);
    gestionAnimateur();
}
public void InterfaceDelAnimateur(){
    System.out.print("\nSupression d'Animateur:\nId: ");
    int id = sc.nextInt();
    System.out.print("Nom: ");
    String nom = sc.next();

    System.out.println("\nEst ce Id: "+id+"\nNom: "+nom+"?\nOUI: 1 | NON: 2");
    int choix = sc.nextInt();

    if(choix == 1){
        suprAnimateur(id);
        animateursMain.removeIf(a -> a.getId() == id);
        System.out.println("Animateur supprimé avec succès.");
    } else {
        System.out.println("Retour au menu.");
    }
    gestionAnimateur();
}

public void getAnimateurListe(){
    try {
        animateursMain.clear();
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        Statement stmt = connexion.createStatement();
        String requete = "SELECT * FROM animateur";
        ResultSet res = stmt.executeQuery(requete);

        System.out.println("\n-------| Liste d'animateurs |-------");
        while (res.next()) {
            Animateur animateur = new Animateur(
                    res.getInt("IdAnim"),
                    res.getString("Nom"),
                    res.getString("Prenom"),
                    new String[]{
                            res.getString("Mail"),
                            res.getString("Adresse"),
                            res.getString("Ville"),
                            res.getString("CodePostal"),
                            res.getString("Tel")
                    }
            );
            animateursMain.add(animateur);
            afficherAnimateur(animateur);
        }

        res.close();
        stmt.close();
        connexion.close();
        gestionAnimateur();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
        gestionAnimateur();
    }
}

public void createAnimateur(Animateur animateur){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "INSERT INTO animateur (Nom, Prenom, Mail, Adresse, Ville, CodePostal, Tel) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setString(1, animateur.getNom());
        pstmt.setString(2, animateur.getPrenom());
        pstmt.setString(3, animateur.getCoordonees()[0]);
        pstmt.setString(4, animateur.getCoordonees()[1]);
        pstmt.setString(5, animateur.getCoordonees()[2]);
        pstmt.setString(6, animateur.getCoordonees()[3]);
        pstmt.setString(7, animateur.getCoordonees()[4]);
        pstmt.executeUpdate();

        pstmt.close();
        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}
public void suprAnimateur(int idAnimateur){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "DELETE FROM `apCamping`.`animateur` WHERE  `IdAnim`=?;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setInt(1, idAnimateur);
        pstmt.executeUpdate();

        pstmt.close();
        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}

public void afficherAnimateur(Animateur animateur){
    String[] co = animateur.getCoordonees();
    System.out.print("\nId: " + animateur.getId() + "\nNom: " + animateur.getNom() + "\nPrenom: " + animateur.getPrenom() + "\nCoordonée: " + co[0]);
    for (int i = 1; i < co.length; i++) {
        System.out.print(", " + co[i]);
    }
    System.out.println();
}



// ANIMATION

public void gestionAnimation(){
    System.out.println("\n1: Ajouter une Animation | 2: Liste d'animations | 3: Suprimer une Animation | 4: Retour");
    int index = sc.nextInt();
    switch (index){
        case 1: InterfaceAddAnimation(); break;
        case 2: getAnimationListe(); break;
        case 3: InterfaceDelAnimation(); break;
        case 4: main(); break;
        default:
            System.out.println("Saisissez un chiffre correct");
            gestionAnimateur();
    }
}

public void InterfaceAddAnimation(){
    System.out.print("\nAjout d'Animation:\nNom: ");
    String nom = sc.next();
    System.out.print("Heure de debut: ");
    int heureFin = sc.nextInt();
    System.out.print("Heure de fin: ");
    int heureDebut = sc.nextInt();
    System.out.print("Id anim: ");
    int idAnim = sc.nextInt();
    System.out.print("Id type anim: ");
    int idTypeAnim = sc.nextInt();


    Animation newA = new Animation(animationMain.size(), nom,  heureDebut, heureFin,idAnim,idTypeAnim);
    createAnimation(newA);
    animationMain.add(newA);
    newA.infosAnimation();
    gestionAnimation();
}
public void InterfaceDelAnimation(){
    System.out.print("\nSupression d'Animation:\nId: ");
    int id = sc.nextInt();

    System.out.println("\nEst ce Id: "+id+"?\nOUI: 1 | NON: 2");
    int choix = sc.nextInt();

    if(choix == 1){
        suprAnimation(id);
        animationMain.removeIf(a -> a.getId() == id);
        System.out.println("Animation supprimé avec succès.");
    } else {
        System.out.println("Retour au menu.");
    }
    gestionAnimateur();
}

public void getAnimationListe(){
    try {
        animationMain.clear();
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        Statement stmt = connexion.createStatement();
        String requete = "SELECT * FROM animation";
        ResultSet res = stmt.executeQuery(requete);

        System.out.println("\n-------| Liste d'animations |-------");
        while (res.next()) {
            Animation animation = new Animation(
                    res.getInt("IdAnimation"),
                    res.getString("NomAnim"),
                    res.getInt("HeureDebutAnim"),
                    res.getInt("HeureFinAnim"),
                    res.getInt("IdAnim"),
                    res.getInt("idTypeAnim")
            );
            animationMain.add(animation);
            afficherAnimateur(animation);
        }

        res.close();
        stmt.close();
        connexion.close();
        gestionAnimateur();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
        gestionAnimateur();
    }
}

public void createAnimation(Animation animation){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "INSERT INTO animateur (NomAnim, HeureDebutAnim, HeureFinAnim, IdAnim, IdTypeAnim) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setString(1, animation.getNom());
        pstmt.setInt(2, animation.getHeureDebut());
        pstmt.setInt(3, animation.getHeureFin());
        pstmt.setInt(4, animation.getIdAnim());
        pstmt.setInt(5, animation.getIdTypeAnim());
        pstmt.executeUpdate();

        pstmt.close();
        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}
public void suprAnimation(int idAnimateur){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "DELETE FROM `apCamping`.`animateur` WHERE  `IdAnim`=?;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setInt(1, idAnimateur);
        pstmt.executeUpdate();

        pstmt.close();
        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}








