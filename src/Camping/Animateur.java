package Camping;

public class Animateur {
    private int id;
    private String nom;
    private String prenom;
    private String[] coordonees;

    public Animateur(){}


    public Animateur( int id, String nom, String prenom, String[] coordonees){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.coordonees=coordonees;
    }

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String[] getCoordonees() {
        return coordonees;
    }

    public String getPrenom() {
        return prenom;
    }
}
