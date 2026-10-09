package Camping;

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
