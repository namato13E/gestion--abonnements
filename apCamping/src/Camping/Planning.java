package Camping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Planning{
    public  Scanner sc =new Scanner(System.in);
    HashMap<Integer, ArrayList> listeAnim  = new HashMap<Integer, ArrayList>();
    ArrayList<Animateur> animateurs=new ArrayList<>();
    String reponse;
    int id;

    public void creerPlanning(ArrayList<Animateur> animateur ) {
        System.out.println("Quelle animation voulez vous mettre : ");
        int idanim = sc.nextInt();
        do {
            System.out.println("Quel animateur voulez-vous mettre : ");
            id = sc.nextInt();
            if(animateur.contains(id)) {
                do {
                    System.out.println(" Veuiller réentrer un nouvel animateur : ");
                    id = sc.nextInt();
                } while (animateur.contains(id));
            }
            animateurs.add(animateur.get(id));
            System.out.println("Voulez-vous ajouter un autre animateur ? (oui/non)");
            reponse = sc.next();
        } while (reponse.equalsIgnoreCase("oui"));
        listeAnim.put(idanim, animateurs);
    }
}
