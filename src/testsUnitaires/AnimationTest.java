package testsUnitaires;

import Camping.Animateur;
import Camping.Animation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimationTest {
    @Test
    void constructeur(){
        Animation a = new Animation(1, "Yoga", "2026-07-01", "10:00", "11:00", 2, new ArrayList<>());

        assertEquals(1, a.getId());
        assertEquals("Yoga", a.getNom());
        assertEquals("2026-07-01", a.getDate());
        assertEquals("10:00", a.getHeureDebut());
        assertEquals("11:00", a.getHeureFin());
        assertEquals(2, a.getIdTypeAnim());
    }

    @Test
    void getAnimateurs() {
        String[] coordonnees = new String[0];
        Animateur anim = new Animateur(1,"Dupont","Jean",coordonnees);
        List<Animateur> liste = new ArrayList<>();
        liste.add(anim);
        Animation a = new Animation(1, "Yoga", "2026-07-01", "10:00", "11:00", 2,liste);

        assertEquals("Animateurs: [ Dupont Jean ]", a.getAnimateurs());
    }

    @Test
    void infosAnimation_retourneToutesLesInfos() {
        String[] coordonnees = new String[0];
        Animateur anim = new Animateur(1,"Dupont","Jean",coordonnees);
        List<Animateur> liste = new ArrayList<>();
        liste.add(anim);
        Animation a = new Animation(1, "Yoga", "2026-07-01", "10:00", "11:00", 2, liste);

        assertEquals(
                "Id: 1, nom: Yoga, date: 2026-07-01, heureDebut: 10:00, heureFin: 11:00, idTypeAnim: 2, Animateurs: [ Dupont Jean ]",
                a.infosAnimation());
    }
}