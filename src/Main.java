import Camping.*;
import java.util.Scanner;

void main() {
    //addAnimateur();

    //Tentative de plaling: trier les dates par ordre croissant
    ArrayList<Animation> listAnimation = new ArrayList<>();
    listAnimation.add(new Animation("testAnimation",1,010101,10,2,new Animateur(1,"Paul")));
    Planing planing1= new Planing();
    for(Animation anim:listAnimation){
        System.out.println(anim.infosAnimation());
    }
}

public void gestionAnimateur(){}
public void addAnimateur(){
    Scanner sc =new Scanner(System.in);
    String[] coordonee=new String[5];
    System.out.print("Mail: ");
    coordonee[0]=sc.next();
    System.out.print("Code Postale: ");
    coordonee[3]=sc.next();
    System.out.print("Ville: ");
    coordonee[2]=sc.next();
    System.out.print("Adresse: ");
    coordonee[1]=sc.next();
    System.out.print("Telephone: ");
    coordonee[4]=sc.next();
    System.out.print("Id: ");
    int id=sc.nextInt();
    System.out.print("Nom: ");
    String  nom=sc.next();

    Animateur newA=new Animateur(id, nom, coordonee);
    String[] co=newA.getCoordonees();
    System.out.print("\nId: "+newA.getId()+"\nNom: "+newA.getNom()+"\nCoordonée: "+co[0]);
    for (int i = 1; i < newA.getCoordonees().length; i++) {
        System.out.print(", "+co[i]);
    }
}