package testsUnitaires;

import Camping.Animateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimateurTest {

    @Test
    void constructeur() {
        String[] coord = {"0612345678", "jean.dupont@mail.fr"};
        Animateur a = new Animateur(1, "Dupont", "Jean", coord);

        assertEquals(1, a.getId());
        assertEquals("Dupont", a.getNom());
        assertEquals("Jean", a.getPrenom());
        assertArrayEquals(coord, a.getCoordonees());
    }

    @Test
    void afficheInfo() {
        Animateur a = new Animateur(1, "Dupont", "Jean", new String[]{});

        assertEquals("Nom: Dupont, Prenom: Jean", a.afficheInfo());
    }
}