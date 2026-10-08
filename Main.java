import Camping.*;
import java.sql.*;
import java.util.*;
import java.security.*;
import org.mindrot.jbcrypt.BCrypt;

public  Scanner sc =new Scanner(System.in);
private final List<Animateur> animateursMain=new ArrayList<>();
private final List<Animation> animationMain=new ArrayList<>();
private final HashMap<Integer, String> typeAnimation=new HashMap<>();

public String URL = "jdbc:mysql://172.17.0.18:3306/apCamping";
public String LOGIN = "Himation";
public String PASSWORD = "Camping@Anee_2027";
void main() {

    connect();

    System.out.println("\n1: Gestion Animateur | 2: Gestion Animation | 3: Planification");
    System.out.print("Saisissez un chiffre:");
    int index=sc.nextInt();
    switch (index){
        case 1:gestionAnimateur();
        case 2:gestionAnimation();
        case 3:gestionPlaning();
        default:
            main();
    }
}

public void gestionAnimateur(){
    System.out.println("\n1: Ajouter un Animateur | 2: Liste d'animateurs | 3: Supprimer un Animateur | 4: Retour");
    int index = sc.nextInt();
    switch (index){
        case 1: InterfaceAddAnimateur(); break;
        case 2: getAnimateurListe(true); break;
        case 3: InterfaceDelAnimateur(); break;
        case 4: main(); break;
        default:
            System.out.println("Saisissez un chiffre correct");
            gestionAnimateur();
    }
}
public void gestionAnimation(){
    System.out.println("\n1: Ajouter une Animation | 2: Liste d'animations | 3: Supprimer une Animation | 4: Retour");
    int index = sc.nextInt();
    switch (index){
        case 1: InterfaceAddAnimation(); break;
        case 2: getAnimationListe(true); break;
        case 3: InterfaceDelAnimation(); break;
        case 4: main(); break;
        default:
            System.out.println("Saisissez un chiffre correct");
            gestionAnimateur();
    }
}
public void gestionPlaning(){}


//=========| Gestion Affichage Animateur |=========\\
public void InterfaceAddAnimateur(){
    System.out.print("\nAjout d'Animateur :\nNom : ");
    String nom = sc.next();
    System.out.print("Prénom : ");
    String prenom = sc.next();
    String[] coordonee = new String[5];
    System.out.print("Mail : ");
    coordonee[0] = sc.next();
    System.out.print("Téléphone : ");
    coordonee[4] = sc.next();
    System.out.print("Code Postale : ");
    coordonee[3] = sc.next();
    System.out.print("Ville : ");
    coordonee[2] = sc.next();
    System.out.print("Adresse : ");
    coordonee[1] = sc.next();

    Animateur newA = new Animateur(animateursMain.size(), nom, prenom, coordonee);
    createAnimateur(newA);
    animateursMain.add(newA);
    System.out.println("\nCréation de l'animateur réalisé avec succès");
    gestionAnimateur();
}
public void InterfaceDelAnimateur(){
    getAnimateurListe(false);
    System.out.print("\nSuppression d'Animateur:\nId: ");
    int id = sc.nextInt();

    boolean trouve = false;
    int indexATrouver = -1;

    for(Animateur animateur:animateursMain){
        if (animateur.getId()==id){
            indexATrouver=animateursMain.indexOf(animateur);
            trouve=!trouve;
        }
    }

    if(trouve){
        String nom = animateursMain.get(indexATrouver).afficheInfo();
        System.out.println("\nCes informations sont elles correctes ?\nId: "+id+"\n"+nom+"?\nOUI: 1 | NON: 2");
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
    else {
        System.out.println("Erreur lors de la saisie de l'animateur.");
        gestionAnimateur();
    }
}
public void afficherAnimateur(Animateur animateur){
    String[] co = animateur.getCoordonees();
    System.out.print("\nId: " + animateur.getId() +"\n"+ animateur.afficheInfo() + "\nCoordonées : " + co[0]);
    for (int i = 1; i < co.length; i++) {
        System.out.print(", " + co[i]);
    }
    System.out.println();
}

public void getAnimateurListe(boolean afficher){
    try {
        animateursMain.clear();
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        Statement stmt = connexion.createStatement();
        String requete = "SELECT * FROM animateur";
        ResultSet res = stmt.executeQuery(requete);

        if(afficher) System.out.println("\n-------| Liste d'animateurs |-------");
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
            if(afficher) afficherAnimateur(animateur);
        }

        res.close();
        stmt.close();
        connexion.close();
        if(afficher) gestionAnimateur();
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


//=========| Gestion Affichage Animation |=========\\
public void InterfaceAddAnimation(){
    System.out.print("\nAjout d'Animation:\nNom: ");
    String nom = sc.next();
    System.out.print("jour: ");
    String  date = "-"+sc.nextInt()+"-";
    System.out.print("mois: ");
    date = date+sc.nextInt()+" ";
    System.out.println("année: ");
    date = sc.nextInt()+date;
    System.out.println("Heure de debut: [format: hh:mm]");
    String heureDebut = date+sc.next()+":00";
    System.out.println("Heure de fin:     [format: hh:mm]");
    String  heureFin = date+sc.next()+":00";
    getTypeAnimation();
    System.out.print("\nId type animation: ");
    int idTypeAnim = sc.nextInt();

    Animation newA = new Animation(animationMain.size(), nom,  heureDebut, heureFin, idTypeAnim);
    createAnimation(newA);
    animationMain.add(newA);
    newA.infosAnimation();
    gestionAnimation();
}
public void InterfaceDelAnimation(){
    getAnimationListe(false);
    System.out.print("\nSuppression d'Animation:\nId: ");
    int id = sc.nextInt();

    boolean trouve = false;
    int indexATrouver = -1;

    for(Animation animation:animationMain){
        if (animation.getId()==id){
            indexATrouver=animationMain.indexOf(animation);
            trouve=!trouve;
        }
    }

    if(trouve){
        String nom = animationMain.get(indexATrouver).getNom();
        System.out.println("\nCes informations sont elles correctes ?\nID : "+id+"\nNom : "+nom+"?\nOUI: 1 | NON: 2");
        int choix = sc.nextInt();

        if(choix == 1){
            suprAnimation(id);
            animationMain.removeIf(a -> a.getId() == id);
            System.out.println("Animation supprimée avec succès.");
        } else {
            System.out.println("Retour au menu.");
        }
        gestionAnimation();
    }
    else {
        System.out.println("Erreur lors de la saisie de l'animation.");
        gestionAnimation();
    }
}
public void afficherAnimation(Animation animation){
    System.out.print("\n"+ animation.infosAnimation());
    System.out.println();
}
public void afficherTypeAnimation(){
    for(Map.Entry<Integer, String > entry: typeAnimation.entrySet())
        System.out.print("\n"+ entry.getKey()+": "+entry.getValue());
}

public void getTypeAnimation(){
    try {
        animationMain.clear();
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        Statement stmt = connexion.createStatement();
        String requete = "SELECT * FROM TypeAnimation";
        ResultSet res = stmt.executeQuery(requete);

        while (res.next()) {
            int IdTypeAnim = res.getInt("IdTypeAnim");
            String nom = res.getString("LibelleTypeAnim");

            typeAnimation.put(IdTypeAnim, nom);
        }

        res.close();
        stmt.close();
        connexion.close();
        afficherTypeAnimation();
    } catch (SQLException exc) {
        System.out.println("ERREUR lors de la récolte des types");
        exc.printStackTrace();
    }
}
public void getAnimationListe(boolean afficher){
    try {
        animationMain.clear();
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        Statement stmt = connexion.createStatement();
        String requete = "SELECT * FROM Animation";
        ResultSet res = stmt.executeQuery(requete);

        if(afficher) System.out.println("\n-------| Liste d'animations |-------");
        while (res.next()) {
            int idAnimation = res.getInt("IdAnimation");
            String nom = res.getString("NomAnim");
            String heureDebut = res.getString("HeureDebutAnim");
            String heureFin = res.getString("HeureFinAnim");
            int idTypeAnim = res.getInt("IdTypeAnim");

            Animation animation = new Animation(idAnimation, nom, heureDebut, heureFin, idTypeAnim);
            animationMain.add(animation);
            if(afficher) afficherAnimation(animation);
        }

        res.close();
        stmt.close();
        connexion.close();
        if(afficher) gestionAnimation();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
        gestionAnimation();
    }
}
public void createAnimation(Animation animation){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "INSERT INTO Animation (NomAnim, HeureDebutAnim, HeureFinAnim, IdTypeAnim) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, animation.getNom());
        pstmt.setString(2, animation.getHeureDebut());
        pstmt.setString(3, animation.getHeureFin());
        pstmt.setInt(4, animation.getIdTypeAnim());
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        int idAnimation = 0;
        if (rs.next()) {
            idAnimation = rs.getInt(1);
        }

        pstmt.close();
        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}
public void suprAnimation(int idAnimation){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "DELETE FROM `Animation` WHERE  `IdAnimation`=?;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setInt(1, idAnimation);
        pstmt.executeUpdate();

        pstmt.close();
        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}
public boolean connect() {
    try {

        System.out.println("Saisissez votre matricule");
        String ident = sc.next();
        System.out.println("Saisissez votre mot de passe");
        String mdp = sc.next();

        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "SELECT * FROM animateur WHERE Identifiant=?;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);


        pstmt.setString(1, ident);

        ResultSet res = pstmt.executeQuery();

        if (res.next()) {
            if(BCrypt.checkpw(mdp, res.getString("Mdp"))){
                System.out.println("Connexion réussie");
                return true;
            }
            else {
                System.out.println("erreur d'identifiant");
                connect();
                return false;
            }
        } else {
            System.out.println("Aucun utilisateur ou mot de passe trouvé dans la base de données.");
        }

    }

    catch (SQLException exc) {
        System.out.println("Erreur de connexion");
        exc.printStackTrace();
        return false;
    }
    return false;
}

public static String hashPassword(String password) {
    // Define a cost factor (work factor). Default is 10.
    int logRounds = 12;  // Increasing this value makes it more secure, but slower

    // Generate the salt
    String salt = BCrypt.gensalt(logRounds);

    // Hash the password with the salt
    return BCrypt.hashpw(password, salt);
}

public String hash(String mdp) {

    return hashPassword(mdp);

}