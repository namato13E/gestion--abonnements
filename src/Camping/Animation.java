package Camping;

import java.util.ArrayList;
import java.util.List;

public class Animation{
    private String nom;
    private int id;
    private String heureDebut;
    private String  heureFin;
    private int idTypeAnim;



    public Animation(){}

    public Animation(int id, String nom, String  heureDebut, String  heureFin, int idTypeAnim){
        this.nom=nom;
        this.id=id;
        this.heureDebut=heureDebut;
        this.heureFin=heureFin;
        this.idTypeAnim=idTypeAnim;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String  getHeureDebut() {
        return heureDebut;
    }

    public String  getHeureFin() {
        return heureFin;
    }

    public int getIdTypeAnim() {
        return idTypeAnim;
    }

    public String infosAnimation(){
        return "Id: "+id+", nom: "+nom+", dateDebut: "+heureDebut+", dateFin: "+heureFin+", idTypeAnim: "+idTypeAnim;
    }
}