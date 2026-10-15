package Camping;

public class Animateur {
    private int id;
    private String nom;
    private String prenom;
    private String[] coordonees;
    public String identifiant;

    public Animateur(){}

    public Animateur(int id, String nom, String prenom, String[] coordonees){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.coordonees=coordonees;
        this.identifiant= prenom.charAt(0)+nom;
    }

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String[] getCoordonees() {
        return coordonees;
    }
    public String afficheInfo(){
        return "Nom: "+this.nom+", Prenom: "+this.prenom;
    }
    public String getIdentifiant() {
        return identifiant;
    }

}