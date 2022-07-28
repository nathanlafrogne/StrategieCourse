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

    public void changePilote(Pilote pilote){
        this.piloteEnCours = pilote;
    }

    double coefGainFuel; // 20 Pas trop mal
    double valeurInitialeFuel; 
    double coefPertePneu; // 30 pas trop mal
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

    private double tempsGainFuel(int index){
        return this.coefGainFuel*index;
    }

    private double tempsPertePneu(int index){
        return Math.exp(index/this.coefPertePneu);
    }

    Temps prevoitTourX(int index, Pilote pilote){
        return pilote.tempsReference.addSecondes(this.tempsGainFuel(index) + this.tempsPertePneu(index));
    }


    
    /* On sait que le temps au tour depend de la dégradation des pneus et de l'utilisation de l'essence
     * Donc comme on connait avec une simulation en entrainement le facteur de l'essence, on peut
     * en deduire l'effet des pneus. La formule est donc : 
     * 
     * tempsTotal = tempsPertePneu + tempsGainFuel
     * 
     */

    void calculCoefGainFuel(Temps tempsMoyenPlein, Temps tempsMoyenMoitie){
        double diffMilli = tempsMoyenPlein.differenceAvecEnMilli(tempsMoyenMoitie);
        this.coefGainFuel = diffMilli/(NOMBRE_TOURS_SIMULATION*1000);
    }

    /*
     * Pour calculer le coef des pneus, on doit faire un demi relais
     * avec le plein puis remettre le plein et refaire le meme nombre de
     * tours sur ce même train de pneus.
     * On prend alors le temps moyen entre le premier relais pneus neuf et 
     * le deuxième relais pneus usés. On aura alors :
     *   => tempsDiff = tempsMoyenNeuf - tempsMoyenUses
     *   => coefPertePneu = NOMBRE_TOURS_SIMULATION/ln(|tempsDiff|-valeurInitialePneu)
     */

    void calculCoefPertePneu(Temps tempsMoyenNeuf, Temps tempsMoyenUses){
        double d = tempsMoyenNeuf.differenceAvecEnMilli(tempsMoyenUses)/1000;
        double x = NOMBRE_TOURS_SIMULATION;
        double K = this.valeurInitialePneu;
        double p = x/Math.log(d-K);
        this.coefPertePneu = p;
    }

    public static void main(String args[]){
        Equipe equipe = new Equipe("Equipe 1");
        Temps neuf = new Temps(1,25,000);
        Pilote pilote = new Pilote("Pilote 1", neuf);
        equipe.addPilote(pilote);
        Temps uses = new Temps(1,25,800);
        equipe.calculCoefPertePneu(neuf, uses);
        Temps plein = new Temps(1,25,000);
        Temps moitie = new Temps(1,24,500);
        equipe.calculCoefGainFuel(plein, moitie);
        Prevision prevision = new Prevision(equipe);
        System.out.println(prevision.calculNombreRelaisMax());
    }

}
