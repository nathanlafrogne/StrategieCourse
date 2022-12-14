import org.junit.Test;
import static org.junit.Assert.*;   
    
public class TempsTest {
        
    @Test
    public void testConstructeursAccesseurs() {
        Temps temps = new Temps();
        assertEquals(0, temps.getHeure());
        assertEquals(0, temps.getMinutes());
        assertEquals(0, temps.getSecondes());
        assertEquals(0, temps.getMillisecondes());
        
        Temps temps2 = new Temps(1,2,3,4);
        assertEquals(1, temps2.getHeure());
        assertEquals(2, temps2.getMinutes());
        assertEquals(3, temps2.getSecondes());
        assertEquals(4, temps2.getMillisecondes());
        
        Temps temps3 = new Temps("1:2:3");
        assertEquals(1, temps3.getMinutes());
        assertEquals(2, temps3.getSecondes());
        assertEquals(3, temps3.getMillisecondes());
    }

    @Test
    public void testAddSecondeMilli(){
        Temps temps = new Temps(1,1,1,1);
        temps.addMili(100);
        assertEquals(1, temps.getMinutes());
        assertEquals(1, temps.getSecondes());
        assertEquals(101, temps.getMillisecondes());

        temps.addMili(1000);
        assertEquals(1, temps.getMinutes());
        assertEquals(2, temps.getSecondes());
        assertEquals(101, temps.getMillisecondes());

        temps.addMili(1100);
        assertEquals(1, temps.getMinutes());
        assertEquals(3, temps.getSecondes());
        assertEquals(201, temps.getMillisecondes());

        temps.addMili(2500);
        assertEquals(1, temps.getMinutes());
        assertEquals(5, temps.getSecondes());
        assertEquals(701, temps.getMillisecondes());

        temps.addSecondes(54);
        assertEquals(1, temps.getMinutes());
        assertEquals(59, temps.getSecondes());
        assertEquals(701, temps.getMillisecondes());

        temps.addSecondesReel(1.125);
        assertEquals(2, temps.getMinutes());
        assertEquals(0, temps.getSecondes());
        assertEquals(826, temps.getMillisecondes());

        temps.addSecondesReel(1.1751254);
        assertEquals(2, temps.getMinutes());
        assertEquals(2, temps.getSecondes());
        assertEquals(1, temps.getMillisecondes());

        temps.addMili(300);
        assertEquals(2, temps.getMinutes());
        assertEquals(2, temps.getSecondes());
        assertEquals(301, temps.getMillisecondes());
    }

    @Test
    public void testAddMinutes(){
        Temps t = new Temps(1,1,1,1);
        
        t.addMinutes(2);
        assertEquals(1, t.getHeure());
        assertEquals(3, t.getMinutes());
        assertEquals(1,t.getSecondes());
        assertEquals(1, t.getMillisecondes());

        t.addMinutes(0);
        assertEquals(1, t.getHeure());
        assertEquals(3, t.getMinutes());
        assertEquals(1,t.getSecondes());
        assertEquals(1, t.getMillisecondes());

        t.addHeure(0);
        assertEquals(1, t.getHeure());
        assertEquals(3, t.getMinutes());
        assertEquals(1,t.getSecondes());
        assertEquals(1, t.getMillisecondes());

        t.addHeure(2);
        assertEquals(3, t.getHeure());
        assertEquals(3, t.getMinutes());
        assertEquals(1,t.getSecondes());
        assertEquals(1, t.getMillisecondes());

        t.addMinutes(57);
        assertEquals(4, t.getHeure());
        assertEquals(0, t.getMinutes());
        assertEquals(1,t.getSecondes());
        assertEquals(1, t.getMillisecondes());

        t.addMinutes(62);
        assertEquals(5, t.getHeure());
        assertEquals(2, t.getMinutes());
        assertEquals(1,t.getSecondes());
        assertEquals(1, t.getMillisecondes());

        // Faire le cas o?? on ajoute une heure en ms ou en s etc
    }

    @Test
    public void testAjouteTempsTrop(){
        Temps t = new Temps();
        t.addMili(3600000);
        assertEquals(1, t.getHeure());
        assertEquals(0, t.getMinutes());
        assertEquals(0,t.getSecondes());
        assertEquals(0, t.getMillisecondes());
    }

    @Test
    public void testConversion(){
        Temps temps = new Temps(1,1,1,1);
        assertEquals(1+1000+60*1000+60*60*1000, temps.enMilli(),0.01);

        Temps temps2 = new Temps(1,2,3,4);
        assertEquals(3723004, temps2.enMilli(),0.01);

        Temps temps3 = new Temps(0,1,2,3);
        assertEquals(62.003, temps3.enSeconde(),0.01);
    }

    @Test
    public void testDifference(){
        Temps temps1 = new Temps(0,1,52,300);
        Temps temps2 = new Temps(0,1,52,200);
        assertEquals(100, temps1.differenceAvecEnMilli(temps2),0.01);

        Temps temps3 = new Temps(0,1,52,400);
        assertEquals(-100, temps1.differenceAvecEnMilli(temps3),0.01);

        Temps temps4 = new Temps(0,1,51,900);
        assertEquals(400, temps1.differenceAvecEnMilli(temps4),0.01);

        Temps temps5 = new Temps(0,1,50,900);
        assertEquals(1400, temps1.differenceAvecEnMilli(temps5),0.01);
    }

    @Test
    public void testNull(){
        Temps temps = new Temps();
        assertTrue(temps.estNull());
    }
}
    