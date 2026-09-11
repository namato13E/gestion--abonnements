import Camping.Animateur;
import java.util.Scanner;

public  Scanner sc =new Scanner(System.in);
private List<Animateur> animateursMain=new ArrayList<>();
void main() {
    System.out.println("\n1: Gestion Animateur | 2: Gestion Animation | 3: Planification");
    System.out.print("Saisissez un chiffre:");
    int index=sc.nextInt();
    switch (index){
        case 1:gestionAnimateur();
        default:
            System.out.println("Saisissez un chiffe correcte");
            main();
    }
}

public void gestionAnimateur(){
    System.out.println("\n1: Ajouter un Animateur | 2: Modifier un Animateur | 3: Suprimer un Animateur | 4: Retour");
    System.out.print("Saisissez un chiffre:");
    int index=sc.nextInt();
    switch (index){
        case 1:addAnimateur();break;
        case 2:manageAnimateur();break;
        case 3:delAnimateur();break;
        case 4:main();break;
        default:
            System.out.println("Saisissez un chiffe correcte");
            gestionAnimateur();
    }
}

public void addAnimateur(){
    String[] coordonee=new String[5];
    System.out.print("\nAjout d'Animateur:\nMail: ");
    coordonee[0]=sc.next();
    System.out.print("Code Postale: ");
    coordonee[3]=sc.next();
    System.out.print("Ville: ");
    coordonee[2]=sc.next();
    System.out.print("Adresse: ");
    coordonee[1]=sc.next();
    System.out.print("Telephone: ");
    coordonee[4]=sc.next();
    System.out.print("Nom: ");
    String  nom=sc.next();

    int id= animateursMain.size();
    Animateur newA=new Animateur(id, nom, coordonee);
    animateursMain.add(newA);
    String[] co=newA.getCoordonees();
    System.out.print("\nId: "+newA.getId()+"\nNom: "+newA.getNom()+"\nCoordonée: "+co[0]);
    for (int i = 1; i < newA.getCoordonees().length; i++) {
        System.out.print(", "+co[i]);
    }
    System.out.println();
    gestionAnimateur();
}
public void delAnimateur(){
    System.out.print("\nSupression d'Animateur:\nId: ");
    int Id=sc.nextInt();
    System.out.print("Nom: ");
    String Nom=sc.next();

    System.out.println("\nEst ce Id: "+Id+"\nNom: "+Nom+"?\n1: OUI | 2: NON");
    int index=sc.nextInt();

    if(index==1){
        animateursMain.remove(animateursMain.get(Id));
    }else System.out.println("Retour au menu.");
    gestionAnimateur();
}

public void manageAnimateur(){
    System.out.println("\nGerer les Animateurs:");
    for (Animateur animateur:animateursMain){
        System.out.println(animateur.afficheInfo());
    }
    gestionAnimateur();
}