package Camping;

import java.util.List;

public class Animation{
    private String nom;
    private int id;
    private String date;
    private String heureDebut;
    private String  heureFin;
    private int idTypeAnim;
    private List<Animateur> animateurs;

    public Animation(){}

    public Animation(int id, String nom, String date, String  heureDebut, String  heureFin, int idTypeAnim, List<Animateur> animateurs){
        this.nom=nom;
        this.id=id;
        this.date=date;
        this.heureDebut=heureDebut;
        this.heureFin=heureFin;
        this.idTypeAnim=idTypeAnim;
        this.animateurs=animateurs;
    }

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getDate() {
        return date;
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

    public String  getAnimateurs() {
        String  anim="Animateurs: [ ";
        for (int i = 0; i < animateurs.size(); i++) {
            if (i==0) anim+=animateurs.get(i).getNom()+" "+animateurs.get(i).getPrenom();
            else anim+=", "+animateurs.get(i).getNom()+" "+animateurs.get(i).getPrenom();
        }
        if (animateurs.size()==0)anim+="vide";
        anim+=" ]";
        return anim;
    }

    public String infosAnimation(){
        String  anim="Animateurs: [ ";
        for (int i = 0; i < animateurs.size(); i++) {
            if (i==0) anim+=animateurs.get(i).getNom()+" "+animateurs.get(i).getPrenom();
            else anim+=", "+animateurs.get(i).getNom()+" "+animateurs.get(i).getPrenom();
        }
        if (animateurs.size()==0)anim+="vide";
        return "Id: "+id+", nom: "+nom+", date: "+date+", heureDebut: "+heureDebut+", heureFin: "+heureFin+", idTypeAnim: "+idTypeAnim+", "+anim+" ]";
    }

    public String infoMinimum(){
        String  anim="Animateurs: [ ";
        for (int i = 0; i < animateurs.size(); i++) {
            if (i==0) anim+=animateurs.get(i).getNom()+" "+animateurs.get(i).getPrenom();
            else anim+=", "+animateurs.get(i).getNom()+" "+animateurs.get(i).getPrenom();
        }
        if (animateurs.size()==0)anim+="vide";
        return "Id: "+id+", nom: "+nom+", date: "+date+", idTypeAnim: "+idTypeAnim+", "+anim+" ]";
    }
}