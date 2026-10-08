package Menu;

import Camping.*;
import Menu.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Affichage {
    private Fonctions getFonctions(){
        Fonctions fonctions=new Fonctions();
        return fonctions;
    }
    private Variables variables=new Variables();

    Scanner sc =new Scanner(System.in);
    List<Animateur>animateursMain=variables.getAnimateursMain();

    public Affichage(){}

    public void start(){
        System.out.println("\n1: Gestion Animateur | 2: Gestion Animation | 3: Planification | 4: Déconnexion");
        System.out.print("Saisissez un chiffre:");
        int index=sc.nextInt();
        switch (index){
            case 1:gestionAnimateur();
            case 2:gestionAnimation();
            case 3:gestionPlaning();
            case 4:variables.disconnect(); start();
            default:
                start();
        }
    }
    public void gestionAnimateur(){
        System.out.println("\n1: Ajouter un Animateur | 2: Liste d'animateurs | 3: Supprimer un Animateur | 4: Retour");
        System.out.print("Saisissez un chiffre:");
        int index = sc.nextInt();
        switch (index){
            case 1: InterfaceAddAnimateur(); break;
            case 2: getFonctions().getAnimateurListe(true, "gestionAnimateur"); break;
            case 3: InterfaceDelAnimateur(); break;
            case 4: start(); break;
            default:
                System.out.println("Saisissez un chiffre correct");
                gestionAnimateur();
        }
    }
    public void gestionAnimation(){
        System.out.println("\n1: Ajouter une Animation | 2: Liste d'animations | 3: Supprimer une Animation | 4: Retour");
        System.out.print("Saisissez un chiffre:");
        int index = sc.nextInt();
        switch (index){
            case 1: InterfaceAddAnimation(); break;
            case 2: getFonctions().getAnimationListe(true); break;
            case 3: InterfaceDelAnimation(); break;
            case 4: start(); break;
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
            case 1: getFonctions().ExporterPlanning(); break;
            case 2: afficherPlanning(); break;
            case 3: start(); break;
            default:
                System.out.println("Saisissez un chiffre correct");
                gestionAnimateur();
        }
    }

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

        Animateur newA = new Animateur(variables.getAnimateursMain().size(), nom, prenom, coordonee);
        getFonctions().createAnimateur(newA);
        variables.addAnimateur(newA);
        System.out.println("\nCréation de l'animateur réalisé avec succès");
        gestionAnimateur();
    }
    public void InterfaceDelAnimateur(){
        getFonctions().getAnimateurListe(false, "");
        System.out.print("\nSuppression d'Animateur:\nId: ");
        int id = sc.nextInt();

        boolean trouve = false;
        int indexATrouver = -1;

        for(Animateur animateur:variables.getAnimateursMain()){
            if (animateur.getId()==id){
                indexATrouver=variables.getAnimateursMain().indexOf(animateur);
                trouve=!trouve;
            }
        }

        if(trouve){
            String nom = variables.getAnimateursMain().get(indexATrouver).afficheInfo();
            System.out.println("\nCes informations sont elles correctes ?\nId: "+id+"\n"+nom+"?\nOUI: 1 | NON: 2");
            int choix = sc.nextInt();

            if(choix == 1){
                getFonctions().suprAnimateur(id);
                variables.removeOfAnimateursMain(id);
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
        getFonctions().getTypeAnimation();
        System.out.print("\nId type animation: ");
        int idTypeAnim = sc.nextInt();
        List<Animateur>animateurs=new ArrayList<>();
        while (temps){
            getFonctions().getAnimateurListe(true, "gestionAnimation");
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

        Animation newA = new Animation(variables.getAnimationMain().size(), nom, date,  heureDebut, heureFin, idTypeAnim,animateurs);
        getFonctions().createAnimation(newA, animateurs);
        variables.addAnimation(newA);
        newA.infosAnimation();
        gestionAnimation();
    }
    public void InterfaceDelAnimation(){
        getFonctions().getAnimationListe(false);
        System.out.print("\nSuppression d'Animation:\nId: ");
        int id = sc.nextInt();

        boolean trouve = false;
        int indexATrouver = -1;

        for(Animation animation:variables.getAnimationMain()){
            if (animation.getId()==id){
                indexATrouver=variables.getAnimationMain().indexOf(animation);
                trouve=!trouve;
            }
        }

        if(trouve){
            String nom = variables.getAnimationMain().get(indexATrouver).getNom();
            System.out.println("\nCes informations sont elles correctes ?\nID : "+id+"\nNom : "+nom+"?\nOUI: 1 | NON: 2");
            int choix = sc.nextInt();

            if(choix == 1){
                getFonctions().suprAnimation(id);
                variables.removeOfAnimationMain(id);
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
        for(Map.Entry<Integer, String > entry: variables.getTypeAnimation().entrySet())
            System.out.print("\n"+ entry.getKey()+": "+entry.getValue());
    }

    public void afficherPlanning() {
        Map<LocalDate, List<Animation>> planning = getFonctions().getPlanning();
        TreeMap<LocalDate, List<Animation>> sortedMap = new TreeMap<>(planning);
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
    }

    public void connect() {
        try {
            clearScreen();
            System.out.print("\nSaisissez votre matricule: ");
            String ident = sc.next();
            System.out.print("Saisissez votre mot de passe: ");
            String mdp = sc.next();

            Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
            String requete = "SELECT * FROM animateur WHERE Identifiant=?;";
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, ident);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                if(BCrypt.checkpw(mdp, res.getString("Mdp"))){
                    System.out.println("Connexion réussie");
                    variables.connect(ident);
                    clearScreen();
                    System.out.println("Bienvenus "+ident);
                    start();
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

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
