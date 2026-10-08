package Camping;

/*import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Planning{
    public  Scanner sc =new Scanner(System.in);
    private HashMap<Integer, ArrayList> listeAnim  = new HashMap<Integer, ArrayList>();
    private ArrayList<Animateur> animateurs=new ArrayList<>();
    private String reponse;
    private int idAnimateur;

    public Planning(){}
    public Planning(HashMap<Integer, ArrayList> listeAnim, ArrayList<Animateur> animateurs, ){}

    public void creerPlanning(ArrayList<Animateur> animateur ) {
        System.out.println("Quelle animation voulez vous mettre : ");
        int idanim = sc.nextInt();
        do {
            System.out.println("Quel animateur voulez-vous mettre : ");
            idAnimateur = sc.nextInt();
            if(animateur.contains(idAnimateur)) {
                do {
                    System.out.println(" Veuiller réentrer un nouvel animateur : ");
                    idAnimateur = sc.nextInt();
                } while (animateur.contains(idAnimateur));
            }
            animateurs.add(animateur.get(idAnimateur));
            System.out.println("Voulez-vous ajouter un autre animateur ? (oui/non)");
            reponse = sc.next();
        } while (reponse.equalsIgnoreCase("oui"));
        listeAnim.put(idanim, animateurs);
    }
}*/

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Planning {
    private List<Animation> animations;

    public Planning() {
        this.animations = new ArrayList<>();
    }

    public void ajouterRendezVous(Animation rendezVous) {
        this.animations.add(rendezVous);
    }

    public List<Animation> getAnimations() {
        return animations;
    }

    public void afficherPlanning(LocalDate date) {
        for (Animation anim : animations) {
            if (anim.getDate().equals(date)) {
                System.out.println(anim.toString()+"\n");
            }
        }
    }
}
