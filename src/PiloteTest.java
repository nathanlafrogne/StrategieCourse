import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;  
    
public class PiloteTest {
        
    @Test
    public void testConstructeur() {
        Pilote pilote = new Pilote("Toto");
        assertEquals("Toto", pilote.getNom());
        assertTrue(pilote.pasDeTemps());
        assertTrue(pilote.tempsReference.estNull());

        Pilote pilote2 = new Pilote("Toto", new Temps(1,2,3,4));
        assertEquals("Toto", pilote2.getNom());
        assertTrue(pilote2.pasDeTemps());
        assertEquals(1, pilote2.tempsReference.heure);
        assertEquals(2, pilote2.tempsReference.minutes);
        assertEquals(3, pilote2.tempsReference.secondes);
        assertEquals(4, pilote2.tempsReference.milliemes);
    }

    @Test
    public void testAddTour() {
        Pilote pilote = new Pilote("Toto");
        Tour tour = new Tour(new Temps(1,2,3,4), pilote);
        pilote.addTour(tour);
        assertEquals(1, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));

        Tour tour2 = new Tour(new Temps(1,2,3,4), pilote);
        pilote.addTour(tour2);
        assertEquals(2, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));
        assertEquals(tour2, pilote.getTour(1));
    }

    @Test
    public void testRemoveTour() {
        Pilote pilote = new Pilote("Toto");
        Tour tour = new Tour(new Temps(1,2,3,4), pilote);
        pilote.addTour(tour);
        assertEquals(1, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));

        Tour tour2 = new Tour(new Temps(1,2,3,4), pilote);
        pilote.addTour(tour2);
        assertEquals(2, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));
        assertEquals(tour2, pilote.getTour(1));
        
        pilote.removeTour(tour);
        assertEquals(1, pilote.getTours().size());
        assertEquals(tour2, pilote.getTour(0));
        
    }


    @Test
    public void testGetTour() {
        Pilote pilote = new Pilote("Toto");
        Tour tour = new Tour(new Temps(1,2,3,4), pilote);
        pilote.addTour(tour);
        assertEquals(1, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));

        Tour tour2 = new Tour(new Temps(1,2,3,4), pilote);
        pilote.addTour(tour2);
        assertEquals(2, pilote.getTours().size());
        assertEquals(tour, pilote.getTour(0));
        assertEquals(tour2, pilote.getTour(1));
    }
}
    