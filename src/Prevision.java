import java.time.Duration;

public class Prevision {
    
    Equipe equipe;
    double tailleReservoir;
    double fuelDansReservoir;
    int nombreToursEnCours;
    double essenceParTour;

    // Caractéristiques de la voiture
    public static final int TEMPS_CHANGEMENT_PNEUS = 20;
    public static final int TEMPS_FUEL_10L = 10;

    // Temps caractéristiques de la course
    public static final int HEURE_DE_COURSE = 4;

    Prevision(Equipe equipe) {
        this.equipe = equipe;
        this.tailleReservoir = 100;
        this.fuelDansReservoir = this.tailleReservoir;
        this.essenceParTour = 3.25;
        this.nombreToursEnCours = 0;
    }

    // Getters & Setters

    public Equipe getEquipe(){
        return this.equipe;
    }

    public double getTailleReservoir(){
        return this.tailleReservoir;
    }

    public double getFuelDansReservoir(){
        return this.fuelDansReservoir;
    }

    public int getNombreToursEnCours(){
        return this.nombreToursEnCours;
    }

    public double getEssenceParTour(){
        return this.essenceParTour;
    }

    public void setTailleReservoir(int taille){
        this.tailleReservoir = taille;
        if(fuelDansReservoir > taille){
            fuelDansReservoir = taille;
        }
    }

    public void setEssenceParTours(double essence){
        this.essenceParTour = essence;
    }

    /**
     * Méthode qui calcul le nombre de tours qu'il est possible de faire en un relais
     * en fonction de la quantité de fuel consommée par tour.
     * @return le nombre de tours complet qu'il est possible de faire en un relais
     */
    public int calculNombreToursRelais(){
        return (int)(this.tailleReservoir / this.essenceParTour);
    }

    /**
     * Méthode qui calcul le temps qu'il faut pour faire un relais
     * @param tourDebut : Le tour du début du relais sur les pneus dans la course
     * @return la durée du relais en Duration
     */
    public Duration calculTempsRelais(int tourDebut){
        long compteur = this.calculNombreToursRelais();
        compteur *= this.equipe.getPiloteEnCours().getTempsReference().toMillis();
        // On a le temps sans perte
        for(int k = 0 ; k < this.calculNombreToursRelais() ; k++){
            compteur += this.equipe.tempsGainFuel(k).toMillis();
        }
        // On a ajouté le temps de gain des pneus
        for(int j = tourDebut ; j < this.calculNombreToursRelais() + tourDebut ; j++){
            compteur += this.equipe.tempsPertePneu(j).toMillis();
        }
        // On a ajouté le temps de perte du fuel
        return Duration.ofMillis(compteur);
    }

    /**
     * Méthode qui calcul le nombre de relais maximum qu'il est rentable de faire en terme de temps
     * @return le nombre de relais max qu'il faut faire sur les pneus
     */
    public int calculNombreRelaisMax(){
        boolean estUtile = true;
        int nombreTours = 0;
        int nombreRelais = 0;
        while(estUtile){
            Duration tempsRelais = this.calculTempsRelais(nombreTours);
            Duration tempsNeuf = this.calculTempsRelais(0);
            if(tempsRelais.toSeconds() >= tempsNeuf.toSeconds() + TEMPS_CHANGEMENT_PNEUS){
                estUtile = false;
            }else{
                nombreTours += this.calculNombreToursRelais();
                nombreRelais++;
            }
        }
        return nombreRelais;
    }


       public static void main(String[] args) {
        Equipe equipe = new Equipe("truc");
        Pilote pilote = new Pilote("Toto",Duration.ofMillis(84200));
        equipe.addPilote(pilote);
        equipe.calculCoefGainFuel(Duration.ofMillis(84200), Duration.ofSeconds(85));
        equipe.tempsGainFuel(0);
    }
}
