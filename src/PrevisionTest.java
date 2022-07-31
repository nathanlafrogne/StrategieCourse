import org.junit.Test;   
import static org.junit.Assert.*;

import java.time.Duration;
    
public class PrevisionTest {

    private static Equipe init(){
        Equipe equipe = new Equipe("Equipe");
        Pilote pilote = new Pilote("Toto", Duration.ofSeconds(85));
        equipe.addPilote(pilote);

        Duration tempsPlein = Duration.ofSeconds(85); // 1:25:000
        Duration tempsMoitie = Duration.ofMillis(84500); // 1:24:500
        equipe.calculCoefGainFuel(tempsPlein, tempsMoitie);

        Duration tempsNeuf = Duration.ofMillis(84200); // 1:24:200
        Duration tempsUses = Duration.ofSeconds(85);
        equipe.calculCoefPertePneu(tempsNeuf, tempsUses);
        return equipe;
    }
        
    @Test
    public void testConstructeur() {
        Equipe equipe = new Equipe("Equipe");
        Pilote pilote = new Pilote("Toto", Duration.ofSeconds(85));
        equipe.addPilote(pilote);

        Prevision simu = new Prevision(equipe);
        assertEquals(equipe, simu.getEquipe());
        assertEquals(100, simu.getTailleReservoir(),0.01);
        assertEquals(100, simu.getFuelDansReservoir(),0.01);
        assertEquals(0, simu.getNombreToursEnCours());
        assertEquals(3.25,simu.getEssenceParTour(),0.01);
    }

    @Test
    public void testCalculNombreToursRelais(){
        Equipe equipe = new Equipe("Equipe");
        Pilote pilote = new Pilote("Toto", Duration.ofSeconds(85));
        equipe.addPilote(pilote);
        Prevision s = new Prevision(equipe);
        assertEquals(30,s.calculNombreToursRelais());

        s.setTailleReservoir(50);
        assertEquals(15,s.calculNombreToursRelais());
        assertEquals(50, s.getFuelDansReservoir(),0.01);

        s.setEssenceParTours(5);
        assertEquals(10, s.calculNombreToursRelais());
    }

    @Test
    public void testCalculTempsRelais(){
        Equipe equipe = PrevisionTest.init();
        Prevision s = new Prevision(equipe);
        s.setTailleReservoir(9);

        Duration temps = s.calculTempsRelais(0); // 2:50:010
        assertEquals(0, temps.toHours());
        assertEquals(2,temps.toMinutes());
        assertEquals(170,temps.toSeconds());
        assertEquals(170010,temps.toMillis());

        s.setTailleReservoir(10); // 4:15:034
        temps = s.calculTempsRelais(0);
        assertEquals(0, temps.toHours());
        assertEquals(4,temps.toMinutes());
        assertEquals(255,temps.toSeconds());
        assertEquals(255034,temps.toMillis());

        s.setTailleReservoir(9);
        temps = s.calculTempsRelais(2); // 2:50:266
        assertEquals(0, temps.toHours());
        assertEquals(2,temps.toMinutes());
        assertEquals(170,temps.toSeconds());
        assertEquals(170266,temps.toMillis());
    }

    @Test
    public void testNombreRelaisMax(){
        Equipe equipe = PrevisionTest.init();
        Prevision s = new Prevision(equipe);
        assertEquals(1, s.calculNombreRelaisMax());
        equipe.coefPertePneu = 100;
        assertNotEquals(1,s.calculNombreRelaisMax());
    }

}
    