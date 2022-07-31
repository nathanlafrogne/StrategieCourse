import org.junit.Test;  
import static org.junit.Assert.*;

import java.time.Duration; 
    
public class EquipeTest {
        
    @Test
    public void testConstructeur() {
        Pilote pilote1 = new Pilote("Toto");
        Pilote pilote2 = new Pilote("Titi");
        Equipe equipe = new Equipe("Equipe");
        equipe.addPilote(pilote1);
        equipe.addPilote(pilote2);
        assertEquals("Equipe", equipe.getNom());
        assertEquals(2, equipe.getPilotes().size());
        assertEquals(pilote1, equipe.getPilote(0));
        assertEquals(pilote2, equipe.getPilote(1));
    }

    @Test
    public void testAddPilote() {
        Pilote pilote1 = new Pilote("Toto");
        Pilote pilote2 = new Pilote("Titi");
        Equipe equipe = new Equipe("Equipe");
        equipe.addPilote(pilote1);
        assertEquals(1, equipe.getPilotes().size());
        assertEquals(pilote1, equipe.getPilote(0));
        equipe.addPilote(pilote2);
        assertEquals(2, equipe.getPilotes().size());
        assertEquals(pilote1, equipe.getPilote(0));
        assertEquals(pilote2, equipe.getPilote(1));
    }

    @Test
    public void testCoef(){
        Pilote pilote1 = new Pilote("Toto");
        Pilote pilote2 = new Pilote("Titi");
        Equipe equipe = new Equipe("Equipe");
        equipe.addPilote(pilote1);
        equipe.addPilote(pilote2);
        assertEquals(-1, (int) equipe.getValeurInitialePneu());
        assertEquals(0, (int) equipe.getValeurInitialeFuel());
        
        Duration tempsPlein = Duration.ofSeconds(85); // 1:25:000
        Duration tempsMoitie = Duration.ofMillis(84500); // 1:24:500
        equipe.calculCoefGainFuel(tempsPlein, tempsMoitie);
        assertEquals(-0.5/10 , equipe.getCoefGainFuel(),0.001);

        Duration tempsNeuf = Duration.ofMillis(84200); // 1:24:200
        Duration tempsUses = Duration.ofSeconds(85);
        equipe.calculCoefPertePneu(tempsNeuf, tempsUses);
        assertEquals(17 , equipe.getCoefPertePneu(),0.02);
    }

    @Test
    public void testPrevoitTemps(){
        Equipe equipe = new Equipe("Equipe");
        Pilote pilote = new Pilote("Toto", Duration.ofSeconds(85));
        equipe.addPilote(pilote);
        Duration check = equipe.prevoitTourX(0, pilote);
        assertEquals(1,check.toMinutes());
        assertEquals(85,check.toSeconds());
        assertEquals(85000,check.toMillis());
        Duration tempsPlein = Duration.ofSeconds(85); // 1:25:000
        Duration tempsMoitie = Duration.ofMillis(84500); // 1:24:500
        equipe.calculCoefGainFuel(tempsPlein, tempsMoitie);
        Duration tempsNeuf = Duration.ofMillis(84200); // 1:24:200
        Duration tempsUses = Duration.ofSeconds(85); // 1:25:000
        equipe.calculCoefPertePneu(tempsNeuf, tempsUses);

        // Tour 1
        check = equipe.prevoitTourX(1, pilote);
        assertEquals(1,check.toMinutes());
        assertEquals(85,check.toSeconds());
        assertEquals(85010,check.toMillis());

        // Tour 2
        check = equipe.prevoitTourX(2, pilote);
        assertEquals(1,check.toMinutes());
        assertEquals(85,check.toSeconds());
        assertEquals(85024,check.toMillis());

        // Tour 3
        check = equipe.prevoitTourX(3, pilote);
        assertEquals(1,check.toMinutes());
        assertEquals(85,check.toSeconds());
        assertEquals(85042,check.toMillis());

        // Tour 4
        check = equipe.prevoitTourX(4, pilote);
        assertEquals(1,check.toMinutes());
        assertEquals(85,check.toSeconds());
        assertEquals(85065,check.toMillis());

        // Tour 10
        check = equipe.prevoitTourX(10, pilote);
        assertEquals(1,check.toMinutes());
        assertEquals(85,check.toSeconds());
        assertEquals(85300,check.toMillis());

        // Tour 20
        check = equipe.prevoitTourX(20, pilote);
        assertEquals(1,check.toMinutes());
        assertEquals(86,check.toSeconds());
        assertEquals(86240,check.toMillis());

        // Tour 30
        check = equipe.prevoitTourX(30, pilote);
        assertEquals(1,check.toMinutes());
        assertEquals(88,check.toSeconds());
        assertEquals(88332,check.toMillis());
    }

    @Test
    public void testGainPerteTemps(){
        Equipe equipe = new Equipe("Equipe");
        Pilote pilote = new Pilote("Toto", Duration.ofSeconds(85));
        equipe.addPilote(pilote);

        // Test Fuel et Pneus
        Duration tempsPlein = Duration.ofSeconds(85); // 1:25:000
        Duration tempsMoitie = Duration.ofMillis(84500); // 1:24:500
        equipe.calculCoefGainFuel(tempsPlein, tempsMoitie);
        Duration tempsNeuf = Duration.ofMillis(84200); // 1:24:200
        Duration tempsUses = Duration.ofSeconds(85); // 1:25:000
        equipe.calculCoefPertePneu(tempsNeuf, tempsUses);
        assertEquals(-0.05, equipe.getCoefGainFuel(),0.01);
        // Tour 1
        assertEquals(-50, equipe.tempsGainFuel(1).toMillis(),0.01);
        assertEquals(60,equipe.tempsPertePneu(1).toMillis(),0.001);

        // Tour 2
        assertEquals(-100, equipe.tempsGainFuel(2).toMillis(),0.01);
        assertEquals(124,equipe.tempsPertePneu(2).toMillis(),0.005);

        // Tour 3
        assertEquals(-150, equipe.tempsGainFuel(3).toMillis(),0.01);
        assertEquals(192,equipe.tempsPertePneu(3).toMillis(),0.003);

        // Tour 4
        assertEquals(-200, equipe.tempsGainFuel(4).toMillis(),0.01);
        assertEquals(265,equipe.tempsPertePneu(4).toMillis(),0.006);
    }

}
    