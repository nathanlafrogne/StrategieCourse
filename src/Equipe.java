import java.time.Duration;
import java.util.ArrayList;

public class Equipe {

    public static final int NOMBRE_TOURS_SIMULATION = 10;
    ArrayList<Pilote> pilotes;
    String nom;
    Pilote piloteEnCours;

    Equipe(String nom) {
        pilotes = new ArrayList<Pilote>();
        this.nom = nom;
        this.valeurInitialePneu = -1;
        this.valeurInitialeFuel = 0;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Pilote> getPilotes() {
        return pilotes;
    }

    public Pilote getPilote(int index) {
        return pilotes.get(index);
    }

    public Pilote getPiloteEnCours() {
        return piloteEnCours;
    }

    public void addPilote(Pilote pilote) {
        if(this.getPilotes().isEmpty()){
            this.piloteEnCours = pilote;
        }
        pilotes.add(pilote);
        
    }

    public int getNombrePilotes() {
        return pilotes.size();
    }

    /**
     * Méthode qui permet de changer le pilote au volant
     * @param pilote : le pilote qui prend le volant
     */
    public void changePilote(Pilote pilote){
        this.piloteEnCours = pilote;
    }

    double coefGainFuel; // 20 Pas trop mal
    double coefPertePneu; // 30 pas trop mal
    double valeurInitialeFuel; 
    double valeurInitialePneu; 

    public double getCoefGainFuel() {
        return coefGainFuel;
    }

    public double getCoefPertePneu() {
        return coefPertePneu;
    }

    public double getValeurInitialeFuel() {
        return valeurInitialeFuel;
    }

    public double getValeurInitialePneu() {
        return valeurInitialePneu;
    }


    /**
     * Méthode qui permet de calculer le gain en seconde dû au fuel
     * lors du tour en paramètre
     * @param tour : le tour dont on veut calculer le gain en fuel
     * @return le temps gagné dû au fuel en seconde
     */
    public Duration tempsGainFuel(int tourX){
        double value = this.getCoefGainFuel()*(tourX);
        long time = (long)(value*1000);
        return Duration.ofMillis(time);
    }

    /**
     * Méthode qui permet de calculer le perte en seconde dû aux pneus
     * lors du tour en paramètre
     * @param tour : le tour dont on veut calculer le perte en pneu
     * @return le temps perdu dû aux pneus en seconde
     */
    public Duration tempsPertePneu(int tourX){
        double value = this.valeurInitialePneu + (Math.exp(tourX/this.coefPertePneu));
        long time = (long)(value*1000);
        Duration ajout = Duration.ofMillis(time);
        return ajout;
    }

    /**
     * Calcul le temps prévisionnel d'un tour donné
     * /*\ Fonctionne uniquement pour le premier relais
     * @param tour : le tour dont on veut calculer le temps prévisionnel
     * @param pilote : le pilote qui fait le tour
     * @return le temps prévisionnel en seconde
     */
    public Duration prevoitTourX(int tour, Pilote pilote){
        // Probleme : au tour 60, enleve pour 60 tours en moins de fuel (prend pas en compte le refuel)
        long value = this.tempsPertePneu(tour).toMillis() + this.tempsGainFuel(tour).toMillis()
            + pilote.getTempsReference().toMillis();
        return Duration.ofMillis(value);
        
    }


    
    /* 
     *                          INFORMATIONS
     * 
     * On sait que le temps au tour depend de la dégradation des pneus et 
     * de l'utilisation de l'essence.
     * Donc comme on connait avec une simulation en entrainement le facteur de l'essence, on peut
     * en deduire l'effet des pneus. La formule est donc : 
     * 
     * tempsTotal = tempsPertePneu + tempsGainFuel
     * 
     */

    

     /**
      * Méthode qui calcul le coefficient de gain de temps en fonction du fuel
      * @param tempsMoyenPlein : Temps moyen avec le plein et pneus neufs 
      * sur le nombre de tours de simulation
      * @param tempsMoyenMoitie : Temps moyen avec la moitié du plein et pneus 
      * neufs sur le nombre de tours de simulation
      */
    public void calculCoefGainFuel(Duration tempsMoyenPlein, Duration tempsMoyenMoitie){
        long temp = tempsMoyenPlein.toMillis();
        Duration tempTemps = Duration.ofMillis(temp);
        double diffMilli = tempTemps.minus(tempsMoyenMoitie).toMillis();
        this.coefGainFuel = -diffMilli/(NOMBRE_TOURS_SIMULATION*1000);
    }



    /*
     *                          INFORMATIONS
     * 
     * Pour calculer le coef des pneus, on doit faire un demi relais
     * avec le plein puis remettre le plein et refaire le meme nombre de
     * tours sur ce même train de pneus.
     * On prend alors le temps moyen entre le premier relais pneus neuf et 
     * le deuxième relais pneus usés. On aura alors :
     *   => tempsDiff = tempsMoyenNeuf - tempsMoyenUses
     *   => coefPertePneu = NOMBRE_TOURS_SIMULATION/ln(|tempsDiff|-valeurInitialePneu)
     */



    /**
     * Méthode qui calcul le coefficient de perte de temps en fonction des pneus
     * @param tempsMoyenNeuf : Temps moyen avec des pneus neufs et le plein 
     * sur le nombre de tours de simulation
     * @param tempsMoyenUses : Temps moyen avec des pneus usés et le plein 
     * sur le nombre de tours de simulation
     */
    void calculCoefPertePneu(Duration tempsMoyenNeuf, Duration tempsMoyenUses){
        int neuf = (int)tempsMoyenNeuf.toMillis();
        int uses = (int)tempsMoyenUses.toMillis();
        double d = neuf-uses;
        d /= 1000;
        double x = NOMBRE_TOURS_SIMULATION;
        double K = this.valeurInitialePneu;
        double p = x/Math.log(Math.abs(d)-K);
        this.coefPertePneu = p;
    }


    public static void main(String[] args) {
        Equipe equipe = new Equipe("truc");
        Pilote pilote = new Pilote("Toto",Duration.ofMillis(84200));
        equipe.addPilote(pilote);
        equipe.calculCoefGainFuel(Duration.ofMillis(84200), Duration.ofSeconds(85));
        equipe.tempsGainFuel(0);
    }

}
