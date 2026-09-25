package Camping;

import java.util.ArrayList;

public class Animation extends Animateur{
    private String nom;
    private int id;
    private int heureDebut;
    private int heureFin;
    private int idAnim;
    private int idTypeAnim;
    private ArrayList<Animateur> animateur;


    public Animation(){}

    public Animation(int id, String nom, int heureDebut, int heureFin, int idAnim, int idTypeAnim){
        this.nom=nom;
        this.id=id;
        this.heureDebut=heureDebut;
        this.heureFin=heureFin;
        this.idAnim=idAnim;
        this.idTypeAnim=idTypeAnim;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public int getIdAnim() {
        return idAnim;
    }

    public int getIdTypeAnim() {
        return idTypeAnim;
    }

    public String infosAnimation(){
        return "Id: "+id+", nom: "+nom+", dateDebut: "+heureDebut+", dateFin: "+heureFin+", idAnim: "+idAnim+", idTypeAnim: "+idTypeAnim;
    }
}