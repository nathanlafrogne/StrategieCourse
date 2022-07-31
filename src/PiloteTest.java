import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;  
    
public class PiloteTest {
        
    @Test
    public void testConstructeur() {
        Pilote pilote = new Pilote("Toto");
        assertEquals("Toto", pilote.getNom());
        assertTrue(pilote.pasDeTemps());
        assertTrue(pilote.tempsReference.isZero());

        Pilote pilote2 = new Pilote("Toto", Duration.ofSeconds(90));
        assertEquals("Toto", pilote2.getNom());
        assertTrue(pilote2.pasDeTemps());
        assertEquals(0, pilote2.tempsReference.toHours());
        assertEquals(1, pilote2.tempsReference.toMinutes());
        assertEquals(90, pilote2.tempsReference.toSeconds());
        assertEquals(90000, pilote2.tempsReference.toMillis());
    }

    @Test
    public void testAddTour() {
        Pilote pilote = new Pilote("Toto");
        Tour tour = new Tour(Duration.ofMillis(90000), pilote); // 1:30:000
        pilote.addTour(tour);
        assertEquals(1, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));

        Tour tour2 = new Tour(Duration.ofSeconds(80), pilote);
        pilote.addTour(tour2);
        assertEquals(2, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));
        assertEquals(tour2, pilote.getTour(1));
    }


    @Test
    public void testGetTour() {
        Pilote pilote = new Pilote("Toto");
        Tour tour = new Tour(Duration.ofSeconds(85), pilote);
        pilote.addTour(tour);
        assertEquals(1, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));

        Tour tour2 = new Tour(Duration.ofSeconds(40), pilote);
        pilote.addTour(tour2);
        assertEquals(2, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));
        assertEquals(tour2, pilote.getTour(1));
    }
}
    