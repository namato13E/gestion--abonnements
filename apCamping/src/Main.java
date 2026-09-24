import Camping.Animateur;
import java.util.Scanner;

void main() {
    addAnimateur();
}

public void gestionAnimateur(){}
public void addAnimateur(){
    Scanner sc =new Scanner(System.in);
    String[] coordonee=new String[5];
    System.out.print("Mail: ");
    coordonee[0]=sc.next();
    System.out.print("Code Postale: ");
    String a = "";
    while(a.length()<5 || a.length()>5){
        a=sc.next();
    }
    coordonee[3]=a;
    System.out.print("Ville: ");
    coordonee[2]=sc.next();
    System.out.print("Adresse: ");
    coordonee[1]=sc.next();
    System.out.print("Telephone: ");
    String b = "";
    while(b.length()<10 || b.length()>10){
        b=sc.next();
    }
    coordonee[4]=b;
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
