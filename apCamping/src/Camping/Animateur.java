package Camping;

public class Animateur {
    private int id;
    private String nom;
    private String[] coordonees;

    public Animateur(){}
    public Animateur( int id, String nom, String[] coordonees){
        this.id=id;
        this.nom=nom;
        this.coordonees=coordonees;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String[] getCoordonees() {
        return coordonees;
    }
    public void setCoordonees(String[] coordonees) {
        this.coordonees = coordonees;
    }
    public String  afficheInfo(){
        return("Id: "+this.getId()+" Nom: "+this.getNom());
    }


}
