import Camping.*;

import java.sql.*;
import java.util.*;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.security.*;
import org.mindrot.jbcrypt.BCrypt;

public  Scanner sc =new Scanner(System.in);
public final List<Animateur> animateursMain=new ArrayList<>();
public List<Animation> animationMain=new ArrayList<>();
public HashMap<Integer, String> typeAnimation=new HashMap<>();
public HashMap<Animation, Animateur[]> planning=new HashMap<>();

public String URL = "jdbc:mysql://172.17.0.18:3306/apCamping";
public String LOGIN = "Himation";
public String PASSWORD = "Camping@Anee_2027";
private String  connected=null;

void main() {
    while (connected == null) connect();
    if("ahimation".equalsIgnoreCase(connected)){
        System.out.println("\n1: Gestion Animateur | 2: Gestion Animation | 3: Planification | 4: Déconnexion");
        System.out.print("Saisissez un chiffre:");
        int index=sc.nextInt();
        switch (index){
            case 1:gestionAnimateur();
            case 2:gestionAnimation();
            case 3:gestionPlaning();
            case 4:connected=null; main();
            default:
                main();
        }
    }
    else{
        System.out.println("\n1: Mes missions | 2: Démission | 3: Changer mdp | 4: Déconnexion");
        System.out.print("Saisissez un chiffre:");
        int index=sc.nextInt();
        switch (index){
            case 1:afficherPlanning();
            case 2:demission();
            case 3:changerMdp();
            case 4:connected=null; main();
        }
    }
}

public void gestionAnimateur(){
    System.out.println("\n1: Ajouter un Animateur | 2: Liste d'animateurs | 3: Supprimer un Animateur | 4: Modifier son mot de passe | 5: Retour");
    System.out.print("Saisissez un chiffre:");
    int index = sc.nextInt();
    switch (index){
        case 1: InterfaceAddAnimateur(); break;
        case 2: getAnimateurListe(true, "gestionAnimateur",true); break;
        case 3: InterfaceDelAnimateur(); break;
        case 4: changerMdp(); break;
        case 5: main(); break;
        default:
            System.out.println("Saisissez un chiffre correct");
            gestionAnimateur();
    }
}
public void gestionAnimation(){
    System.out.println("\n1: Ajouter une Animation | 2: Liste d'animations | 3: Supprimer une Animation | 4: Insérer un Animateur | 5: Retour");
    System.out.print("Saisissez un chiffre:");
    int index = sc.nextInt();
    switch (index){
        case 1: InterfaceAddAnimation(); break;
        case 2: getAnimationListe(true); break;
        case 3: InterfaceDelAnimation(); break;
        case 4: InterfaceInsertAnimateur(); break;
        case 5: main(); break;
        default:
            System.out.println("Saisissez un chiffre correct");
            gestionAnimateur();
    }
}
public void gestionPlaning(){
    System.out.println("\n1: Exporter le planning | 2: Afficher le planning | 3: Retour");
    System.out.print("Saisissez un chiffre:");
    int index = sc.nextInt();
    switch (index){
        case 1: ExporterPlanning(); break;
        case 2: afficherPlanning(); break;
        case 3: main(); break;
        default:
            System.out.println("Saisissez un chiffre correct");
            gestionAnimateur();
    }
}


//=========| Gestion Animateur |=========\\
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
    animateursMain.add(newA);
    createAnimateur(newA);
    System.out.println("\nCréation de l'animateur réalisé avec succès\nSes identifiants sont: "+newA.getIdentifiant()+" et mot de passe : 0550002D (temporaire)");
    gestionAnimateur();
}
public void InterfaceDelAnimateur(){
    getAnimateurListe(false, "", false);
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
public void afficherAnimateur(Animateur animateur, boolean donne){
    System.out.print("\nId: " + animateur.getId() +"\n"+ animateur.afficheInfo() );
    if (donne){
        String[] co = animateur.getCoordonees();
        System.out.print("\nCoordonées : " + co[0]);
        for (int i = 1; i < co.length; i++) {
            System.out.print(", " + co[i]);
        }
    }
    System.out.println();
}
public void demission() {
    Animateur animateur = getAnimateur(connected);
    System.out.println("Voulez vous vraiment démissionner?  [OUI:1 | NON:2]");
    int choix=sc.nextInt();
    if(choix==1){
        try {
            Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            String requete = "UPDATE animateur SET Identifiant=?, Mdp=? WHERE Identifiant=?";
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, null);
            pstmt.setString(2, null);
            pstmt.setString(3, connected);
            pstmt.executeUpdate();
            pstmt.close();
            suprAnimateur(animateur.getId());
            main();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

public void getAnimateurListe(boolean afficher, String lanceur, boolean donne){
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
            if(afficher) afficherAnimateur(animateur,donne);
        }

        res.close();
        stmt.close();
        connexion.close();
        if(afficher){
            switch (lanceur){
                case "gestionAnimateur": gestionAnimateur();
                default:;
            }
        }
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
        gestionAnimateur();
    }
}
public Animateur getAnimateur(String identifiant){
    getAnimateurListe(false, "",false);
    for(Animateur animateur:animateursMain){
        if("ahimation".equalsIgnoreCase(connected)){
            animateur=animateursMain.getFirst();
            System.out.println(animateur.afficheInfo());
            return animateur;
        }
        if (animateur.getIdentifiant().equals(identifiant)){
            return animateur;
        }
    }
    return null;
}
public void createAnimateur(Animateur animateur){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "INSERT INTO animateur (Nom, Prenom, Mail, Adresse, Ville, CodePostal, Tel, Identifiant, Mdp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setString(1, animateur.getNom());
        pstmt.setString(2, animateur.getPrenom());
        pstmt.setString(3, animateur.getCoordonees()[0]);
        pstmt.setString(4, animateur.getCoordonees()[1]);
        pstmt.setString(5, animateur.getCoordonees()[2]);
        pstmt.setString(6, animateur.getCoordonees()[3]);
        pstmt.setString(7, animateur.getCoordonees()[4]);
        pstmt.setString(8, animateur.getIdentifiant());
        pstmt.setString(9, hashPassword("0550002D"));
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
        String requete = "SELECT COUNT(*) AS Anime FROM `animer` AS an INNER JOIN `Animation` AS a ON an.`IdAnimation`=a.`IdAnim` WHERE  `IdAnimateur`=? AND a.`Date` <= CURDATE() ORDER BY a.`Date` ASC;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setInt(1, idAnimateur);

        if (!pstmt.executeQuery().next()) {
            pstmt.close();

            requete = "DELETE an FROM `animer` AS an INNER JOIN `Animation` AS a ON an.`IdAnimation`=a.`IdAnim` WHERE  `IdAnimateur`=? AND a.`Date` >= CURDATE();";
            pstmt = connexion.prepareStatement(requete);
            pstmt.setInt(1, idAnimateur);
            pstmt.executeUpdate();
            pstmt.close();

            requete = "DELETE FROM `apCamping`.`animateur` WHERE  `IdAnim`=?;";
            pstmt = connexion.prepareStatement(requete);
            pstmt.setInt(1, idAnimateur);
            pstmt.executeUpdate();

            System.out.println("Animateur supprimé avec succès.");
        }else{
            System.out.println("L'animateur a déjà animer une activité, on ne peux donc pas le retirer des registres.");
        }
        pstmt.close();
        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}


//=========| Gestion Animation |=========\\
public void InterfaceAddAnimation(){
    boolean  temps=false;
    System.out.print("\nAjout d'Animation:\nNom: ");
    String nom = sc.next();
    String  date="";
    String  dateTemp="";
    while (temps==false){
        System.out.print("jour: ");
        dateTemp=""+sc.nextInt();
        if(dateTemp.length()==1)dateTemp="0"+dateTemp;
        date = "-"+dateTemp;
        System.out.print("mois: ");
        dateTemp=""+sc.nextInt();
        if(dateTemp.length()==1)dateTemp="0"+dateTemp;
        date = "-"+dateTemp+date;
        System.out.print("année: ");
        date = sc.nextInt()+date;
        if(date.length()==10){
            temps=true;
            break;
        }
        System.out.println('\n'+date);
        System.out.println("\nSaisie de la date invalide ! [format: dd:mm:aaaa]");
    }

    System.out.println("Heure de debut: [format: hh:mm]");
    String heureDebut = sc.next()+":00";
    System.out.println("Heure de fin:     [format: hh:mm]");
    String  heureFin = sc.next()+":00";
    getTypeAnimation();
    System.out.print("\nId type animation: ");
    int idTypeAnim = sc.nextInt();
    List<Animateur>animateurs=new ArrayList<>();
    while (temps){
        getAnimateurListe(true, "gestionAnimation", false);
        System.out.println("\nAnimateur: [id attendus]");
        int idAnimateur= sc.nextInt();

        for(Animateur animateur:animateursMain){
            if (animateur.getId()==idAnimateur){
                if (animateurs.contains(animateur)) System.out.println("Cette animateur est déjà présent dans cette animation !");
                else animateurs.add(animateur);
            }
        }
        System.out.println("Voulez vous mobiliser un autre animateur en plus? 1:OUI|2:NON");
        int choix=sc.nextInt();
        if(choix==2) temps=false;
    }

    Animation newA = new Animation(animationMain.size(), nom, date,  heureDebut, heureFin, idTypeAnim,animateurs);
    createAnimation(newA, animateurs);
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
public void InterfaceInsertAnimateur(){
    getAnimationListe(false);
    System.out.println("\nInsertion d'un animateur dans une animation\n");
    getAnimateurListe(true,"", false);
    System.out.print("Saisissez l'identifiant de l'animateur:");
    int idAnimateur=sc.nextInt();
    System.out.println();
    for(Animation a:animationMain){
        System.out.println(a.infoMinimum());
    }
    System.out.print("Saisissez l'identifiant de l'animation:");
    int idAnimation=sc.nextInt();
    ajoutAnimateurAnimation(idAnimateur,idAnimation);
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
        getAnimateurListe(false, "",false);
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        Statement stmt = connexion.createStatement();
        String requete = "SELECT * FROM Animation";
        ResultSet res = stmt.executeQuery(requete);

        if(afficher) System.out.println("\n-------| Liste d'animations |-------");
        while (res.next()) {
            int idAnimation = res.getInt("IdAnimation");
            String nom = res.getString("NomAnim");
            String date = res.getString("Date");
            String heureDebut = res.getString("HeureDebutAnim");
            String heureFin = res.getString("HeureFinAnim");
            int idTypeAnim = res.getInt("IdTypeAnim");

            // Récupération des animateurs associés à l'animation
            List<Animateur> animateurs = new ArrayList<>();
            String requeteAnimateurs = "SELECT * FROM animer WHERE IdAnimation = ?";
            PreparedStatement pstmtAnimateurs = connexion.prepareStatement(requeteAnimateurs);
            pstmtAnimateurs.setInt(1, idAnimation);
            ResultSet resAnimateurs = pstmtAnimateurs.executeQuery();
            while (resAnimateurs.next()) {
                int idAnimateur = resAnimateurs.getInt("IdAnimateur");
                for (Animateur animateur : animateursMain) {
                    if (animateur.getId() == idAnimateur) {
                        animateurs.add(animateur);
                    }
                }
            }

            Animation animation = new Animation(idAnimation, nom, date, heureDebut, heureFin, idTypeAnim, animateurs);
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
public void createAnimation(Animation animation, List<Animateur> animateurs){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "INSERT INTO Animation (NomAnim, Date, HeureDebutAnim, HeureFinAnim, IdTypeAnim) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, animation.getNom());
        pstmt.setString(2, animation.getDate());
        pstmt.setString(3, animation.getHeureDebut());
        pstmt.setString(4, animation.getHeureFin());
        pstmt.setInt(5, animation.getIdTypeAnim());
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        int idAnimation = 0;
        if (rs.next()) {
            idAnimation = rs.getInt(1);
        }
        pstmt.close();

        for (int i = 0; i < animateurs.size(); i++) {
            requete = "INSERT INTO animer (IdAnimateur, IdAnimation) VALUES (?, ?)";
            pstmt = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, animateurs.get(i).getId());
            pstmt.setInt(2, idAnimation);
            pstmt.executeUpdate();
            pstmt.close();
        }

        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}
public void suprAnimation(int idAnimation){
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "DELETE FROM `animer` WHERE  `IdAnimation`=?;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setInt(1, idAnimation);
        pstmt.executeUpdate();
        pstmt.close();

        requete = "DELETE FROM `Animation` WHERE  `IdAnimation`=?;";
        pstmt = connexion.prepareStatement(requete);
        pstmt.setInt(1, idAnimation);
        pstmt.executeUpdate();
        pstmt.close();

        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}
public void ajoutAnimateurAnimation(int idAnimateur, int idAnimation){
    getAnimationListe(false);
    getAnimateurListe(false,"",false);
    boolean AnimationExist=false;
    boolean AnimateurExist=false;

    Animateur animateurTrouve = null;
    for (Animateur animateur: animateursMain) {
        if (animateur.getId() == idAnimateur) {
            animateurTrouve = animateur;
            AnimateurExist=true;
            break;
        }
    }
    if (!AnimateurExist){
        System.out.println("Vous vous êtes tromper dans l identifiant de l'animateur");
        return;
    }

    Animation animationTrouve = null;
    for (Animation animation: animationMain) {
        if(animation.getId()==idAnimation){
            animationTrouve = animation;
            AnimationExist=true;
            break;
        }
    }
    if (!AnimationExist){
        System.out.println("Vous vous êtes tromper dans l identifiant de l'animation");
        return;
    }

    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "SELECT COUNT(*) AS Animer FROM `animer` WHERE  `IdAnimateur`=? AND `IdAnimation`=?;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setInt(1, idAnimateur);
        pstmt.setInt(2, idAnimation);

        if (!pstmt.executeQuery().next()) {
            pstmt.close();

            requete = "INSERT INTO animer (IdAnimateur, IdAnimation) VALUES (?, ?)";
            pstmt = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, idAnimateur);
            pstmt.setInt(2, idAnimation);
            pstmt.executeUpdate();
        }
        else{
            System.out.println("Cette animateur anime déjà cette animation!");

        }
        pstmt.close();
        connexion.close();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}


//=========| Gestion Planning |=========\\
public void afficherPlanning() {
    Map<LocalDate, List<Animation>> planning = getPlanning();
    TreeMap<LocalDate, List<Animation>>sortedMap = new TreeMap<>(planning);
    boolean hasContent = false;
    for (Map.Entry<LocalDate, List<Animation>> entry : sortedMap.entrySet()) {
        String date = entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        System.out.println("\nAnimations du " + date);

        for (Animation animation : entry.getValue()) {
            System.out.println("    -"+animation.getNom()+" : "+animation.getHeureDebut() + " - " + animation.getHeureFin()+" | "+animation.getAnimateurs());
            hasContent = true;
        }
    }

    if (!hasContent) {
        System.out.println("Aucune animation à afficher pour cette semaine.");
    }
    main();
}

public void ExporterPlanning() {
    try {
        Map<LocalDate, List<Animation>> planning = getPlanning();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("planning.pdf"));
        document.open();

        TreeMap<LocalDate, List<Animation>>sortedMap = new TreeMap<>(planning);
        boolean hasContent = false;
        for (Map.Entry<LocalDate, List<Animation>> entry : sortedMap.entrySet()) {
            String date = entry.getKey().format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
            document.add(new Paragraph("\nAnimations du " + date));

            for (Animation animation : entry.getValue()) {
                document.add(new Paragraph("    -"+animation.getNom()+" : "+animation.getHeureDebut() + " - " + animation.getHeureFin()+" | "+animation.getAnimateurs()+"\n"));
                hasContent = true;
            }
        }

        if (!hasContent) {
            document.add(new Paragraph("Aucune animation à afficher pour cette semaine."));
        }

        document.close();
        System.out.println("Fichier PDF créé avec succès !");
        main();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public Map<LocalDate, List<Animation>> getPlanning() {
    Map<LocalDate, List<Animation>> planning = new HashMap<>();
    Animateur anim = getAnimateur(connected);

    if("ahimation".equalsIgnoreCase(connected)){
        try (Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             Statement stmt = connexion.createStatement();
             ResultSet res = stmt.executeQuery("SELECT * FROM Animation As a WHERE a.`Date` >= CURDATE() AND a.`Date` < CURDATE() + INTERVAL 7 DAY ORDER BY a.`Date` ASC")) {

            while (res.next()) {
                int id = res.getInt("IdAnimation");
                String nom = res.getString("NomAnim");
                String dateStr = res.getString("Date");
                String heureDebutStr = res.getString("HeureDebutAnim");
                String heureFinStr = res.getString("HeureFinAnim");
                int idTypeAnim = res.getInt("IdTypeAnim");
                // Animateurs de l'animation
                List<Animateur> animateurs = new ArrayList<>();
                try (PreparedStatement pstmt = connexion.prepareStatement(
                        "SELECT * FROM animer WHERE IdAnimation = ?")) {
                    pstmt.setInt(1, id);
                    try (ResultSet rA = pstmt.executeQuery()) {
                        while (rA.next()) {
                            int idA = rA.getInt("IdAnimateur");
                            for (Animateur a : animateursMain) {
                                if (a.getId() == idA) {
                                    animateurs.add(a);
                                }
                            }
                        }
                    }
                }

                LocalDate date = LocalDate.parse(dateStr);
                Animation animation = new Animation(id, nom, dateStr, heureDebutStr, heureFinStr, idTypeAnim, animateurs);
                planning.computeIfAbsent(date, k -> new ArrayList<>()).add(animation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    if (anim == null) {
        System.out.println("Erreur : Aucun animateur trouvé pour l'identifiant : " + connected);
        return planning;
    }
    else {
        String query = "SELECT * FROM Animation As a " +
                "INNER JOIN animer As an ON a.IdAnimation = an.IdAnimation " +
                "WHERE an.IdAnimateur = ? AND a.`Date` >= CURDATE() " +
                "AND a.`Date` < CURDATE() + INTERVAL 7 DAY ORDER BY a.`Date` ASC";

        try (Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             PreparedStatement pstmt = connexion.prepareStatement(query)) {

            pstmt.setInt(1, anim.getId());

            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    int id = res.getInt("IdAnimation");
                    String nom = res.getString("NomAnim");
                    String dateStr = res.getString("Date");
                    String heureDebutStr = res.getString("HeureDebutAnim");
                    String heureFinStr = res.getString("HeureFinAnim");
                    int idTypeAnim = res.getInt("IdTypeAnim");

                    List<Animateur> animateurs = new ArrayList<>();
                    animateurs.add(anim);

                    LocalDate date = LocalDate.parse(dateStr);
                    Animation animation = new Animation(id, nom, dateStr, heureDebutStr, heureFinStr, idTypeAnim, animateurs);
                    planning.computeIfAbsent(date, k -> new ArrayList<>()).add(animation);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    return planning;
}

//=========| Gestion Connexion |=========\\
public void connect() {
    try {
        clearScreen();

        System.out.print("\nSaisissez votre matricule: ");
        String ident = sc.next();
        System.out.print("Saisissez votre mot de passe: ");
        String mdp = sc.next();

        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "SELECT * FROM animateur WHERE Identifiant=?;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setString(1, ident);
        ResultSet res = pstmt.executeQuery();

        if (res.next()) {
            if(BCrypt.checkpw(mdp, res.getString("Mdp"))){
                connected=ident;
                clearScreen();
                System.out.println("Bienvenus "+ident);
                if(BCrypt.checkpw("0550002D", res.getString("Mdp"))){
                    System.out.println("Veuillez changer de mot de passe");
                    changerMdp();
                }
            }
            else {
                System.out.println("Erreur d'identifiants");
                connect();
            }
        } else {
            System.out.println("Erreur d'identifiants");
        }
    } catch (SQLException exc) {
        System.out.println("Erreur de connexion");
        exc.printStackTrace();
    }
}
public void changerMdp(){
    System.out.println("Entrez votre nouveau mdp: ");
    String mdp = sc.next();
    try {
        Connection connexion = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        String requete = "UPDATE `animateur` SET  `Mdp`=? WHERE Identifiant=?;";
        PreparedStatement pstmt = connexion.prepareStatement(requete);
        pstmt.setString(1, hashPassword(mdp));
        pstmt.setString(2, connected);
        pstmt.executeUpdate();
        pstmt.close();
        connexion.close();
        clearScreen();
        main();
    } catch (SQLException exc) {
        System.out.println("ERREUR");
        exc.printStackTrace();
    }
}
public static String hashPassword(String password) {
    int tailleSel = 12;
    String salt = BCrypt.gensalt(tailleSel);

    return BCrypt.hashpw(password, salt);
}
public static void clearScreen() {
    for (int i = 0; i < 50; i++) {
        System.out.println();
    }
}