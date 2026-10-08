package Menu;

import Camping.Animateur;
import Camping.Animation;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Fonctions {

    private Affichage getAffichage(){
        Affichage affichage=new Affichage();
        return affichage;
    }
    private Variables variables=new Variables();

    public Fonctions(){}

    public void getAnimateurListe(boolean afficher, String lanceur){
        try {
            variables.clearAnimateursMain();
            Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
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
                variables.addAnimateur(animateur);
                if(afficher) getAffichage().afficherAnimateur(animateur);
            }
            res.close();
            stmt.close();
            connexion.close();
            if(afficher){
                switch (lanceur){
                    case "gestionAnimateur": getAffichage().gestionAnimateur();
                    default:;
                }
            }
        } catch (SQLException exc) {
            System.out.println("ERREUR");
            exc.printStackTrace();
            getAffichage().gestionAnimateur();
        }
    }
    public void createAnimateur(Animateur animateur){
        try {
            Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
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
            Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
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

    public void getTypeAnimation(){
        try {
            variables.clearAnimationMain();
            Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
            Statement stmt = connexion.createStatement();
            String requete = "SELECT * FROM TypeAnimation";
            ResultSet res = stmt.executeQuery(requete);

            while (res.next()) {
                int IdTypeAnim = res.getInt("IdTypeAnim");
                String nom = res.getString("LibelleTypeAnim");

                variables.addTypeAnimation(IdTypeAnim, nom);
            }

            res.close();
            stmt.close();
            connexion.close();
            getAffichage().afficherTypeAnimation();
        } catch (SQLException exc) {
            System.out.println("ERREUR lors de la récolte des types");
            exc.printStackTrace();
        }
    }
    public void getAnimationListe(boolean afficher){
        try {
            variables.clearAnimationMain();
            getAnimateurListe(false, "");
            Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
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
                    for (Animateur animateur : variables.getAnimateursMain()) {
                        if (animateur.getId() == idAnimateur) {
                            animateurs.add(animateur);
                        }
                    }
                }

                Animation animation = new Animation(idAnimation, nom, date, heureDebut, heureFin, idTypeAnim, animateurs);
                variables.addAnimation(animation);
                if(afficher) getAffichage().afficherAnimation(animation);
            }

            res.close();
            stmt.close();
            connexion.close();
            if(afficher) getAffichage().gestionAnimation();
        } catch (SQLException exc) {
            System.out.println("ERREUR");
            exc.printStackTrace();
            getAffichage().gestionAnimation();
        }
    }
    public void createAnimation(Animation animation, List<Animateur> animateurs){
        try {
            Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
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
            Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
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

    public void ExporterPlanning() {
        try {
            Map<LocalDate, List<Animation>> planning = getPlanning();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("planning.pdf"));
            document.open();

            TreeMap<LocalDate, List<Animation>> sortedMap = new TreeMap<>(planning);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Map<LocalDate, List<Animation>> getPlanning() {
        getAnimateurListe(false, "");
        Map<LocalDate, List<Animation>> planning = new HashMap<>();
        try (Connection connexion = DriverManager.getConnection(variables.getURL(), variables.getLOGIN(), variables.getPASSWORD());
             Statement stmt = connexion.createStatement();
             ResultSet res = stmt.executeQuery("SELECT * FROM Animation WHERE `Date` >= CURDATE() AND `Date` < CURDATE() + INTERVAL 7 DAY")) {

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
                            for (Animateur a : variables.getAnimateursMain()) {
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
        return planning;
    }
}