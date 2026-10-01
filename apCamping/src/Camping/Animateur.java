package Camping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class Animateur {
    public String URL = "jdbc:mysql://172.17.0.7:3306/apCamping";
    public String LOGIN = "Himation";
    public String PASSWORD = "votre_mot_de_passe"; // Remplacez par votre vrai mot de passe

    private int id;
    private String nom;
    private String prenom;
    private String[] coordonees;

    public Animateur(){}

    public Animateur(int id, String nom, String prenom, String[] coordonees){
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
    public String getPrenom() {
        return prenom;
    }
    public String[] getCoordonees() {
        return coordonees;
    }
    public String afficheInfo(){
        return "Nom: "+this.nom+", Prenom: "+this.prenom;
    }

}