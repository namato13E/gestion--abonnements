package Menu;

import Camping.Animateur;
import Camping.Animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Variables {
    private final List<Animateur> animateursMain=new ArrayList<>();
    private List<Animation> animationMain=new ArrayList<>();
    private HashMap<Integer, String> typeAnimation=new HashMap<>();

    private String URL = "jdbc:mysql://172.17.0.18:3306/apCamping";
    private String LOGIN = "Himation";
    private String PASSWORD = "Camping@Anee_2027";
    private String  connected=null;

    public String getURL() {
        return URL;
    }
    public String getLOGIN() {
        return LOGIN;
    }
    public String getPASSWORD() {
        return PASSWORD;
    }

    public void disconnect() {
        this.connected=null;
    }
    public void connect(String identifiant) {
        this.connected=identifiant;
    }
    public String Connected() {
        return connected;
    }

    public void addAnimateur(Animateur a){
        animateursMain.add(a);
    }
    public List<Animateur> getAnimateursMain() {
        return animateursMain;
    }
    public void removeOfAnimateursMain(int id) {
        animateursMain.removeIf(a -> a.getId() == id);
    }
    public void clearAnimateursMain(){
        animateursMain.clear();
    }

    public void addAnimation(Animation a){
        animationMain.add(a);
    }
    public List<Animation> getAnimationMain() {
        return animationMain;
    }
    public void removeOfAnimationMain(int id) {
        animationMain.removeIf(a -> a.getId() == id);
    }
    public void clearAnimationMain(){
        animationMain.clear();
    }


    public void addTypeAnimation(int id, String nom){
        typeAnimation.put(id, nom);
    }
    public HashMap<Integer, String> getTypeAnimation() {
        return typeAnimation;
    }

}
