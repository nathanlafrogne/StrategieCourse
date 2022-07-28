import org.junit.Test;  
import static org.junit.Assert.*; 
    
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
        
        Temps tempsPlein = new Temps(0,1,25,0);
        Temps tempsMoitie = new Temps(0,1,24,500);
        equipe.calculCoefGainFuel(tempsPlein, tempsMoitie);
        assertEquals(-0.5/10 , equipe.getCoefGainFuel(),0.001);

        Temps tempsNeuf = new Temps(0,1,24,200);
        Temps tempsUses = new Temps(0,1,25,000);
        equipe.calculCoefPertePneu(tempsNeuf, tempsUses);
        assertEquals(17 , equipe.getCoefPertePneu(),0.02);
    }

}
    