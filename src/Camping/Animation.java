package Camping;

import java.util.ArrayList;

public class Animation extends Animateur{
    private String nom;
    private int id;
    private int date;
    private int heure;
    private int duree;
    private Animateur animateur;


    public Animation(){}

    public Animation(String nom, int id, int date, int heure, int duree, Animateur animateur){
        this.nom=nom;
        this.id=id;
        this.date=date;
        this.heure=heure;
        this.duree=duree;
        this.animateur=animateur;
    }

    public String infosAnimation(){
        return "Id: "+id+", nom: "+nom+", date: "+date+", heure: "+heure+", duree: "+duree+", animateur: "+animateur;
    }
}



