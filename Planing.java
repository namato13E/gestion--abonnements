package Camping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Planing extends Animation{
    public  Scanner sc =new Scanner(System.in);
    private ArrayList<Animation> animations;
    public String URL = "jdbc:mysql://172.17.0.7:3306/apCamping";
    public String LOGIN = "Himation";
    HashMap<Integer, ArrayList> listeAnim  = new HashMap<Integer, ArrayList>();
    boolean manyAnim = false;
    int idanimateur;
    int idanimateur2;
    ArrayList<Animateur> animateurs=new ArrayList<>();

    public void creerPlanning(ArrayList<Animateur> animateur ) {

        //for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println("Quelle animation vouler vous mettre : ");
                int idanim = sc.nextInt();
                System.out.println("Quelle animateur vouler vous mettre : ");
                idanimateur = sc.nextInt();
                System.out.println("vouler vous mettre un deuxème animateur ?");
                String bool = sc.next();
                if (bool.equals("oui")) {
                    System.out.println("Quelle animateur vouler vous mettre : ");
                    idanimateur2 = sc.nextInt();
                    manyAnim = true;
                }
                if (!manyAnim) {
                    animateurs.add(animateur.get(idanimateur));
                } else {
                    animateurs.add(animateur.get(idanimateur));
                    animateurs.add(animateur.get(idanimateur2));
                }
                listeAnim.put(idanim, animateurs);
            }
        }
    //}
}
