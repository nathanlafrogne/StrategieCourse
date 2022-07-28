public class Prevision {
    
    Equipe equipe;
    double tailleReservoir;
    double fuelDansReservoir;
    int nombreToursEnCours;

    // Caractéristiques de la voiture
    public static final double ESSENCE_PAR_TOUR = 3.25;
    public static final int TEMPS_CHANGEMENT_PNEUS = 20;
    public static final int TEMPS_FUEL_10L = 10;

    // Temps caractéristiques de la course
    public static final int HEURE_DE_COURSE = 4;

    Prevision(Equipe equipe) {
        this.equipe = equipe;
        this.tailleReservoir = 100;
        this.fuelDansReservoir = this.tailleReservoir;
        this.nombreToursEnCours = this.calculNombreToursRelais();
    }

    public int calculNombreToursRelais(){
        return (int)(this.tailleReservoir / ESSENCE_PAR_TOUR);
    }

    public Temps calculTempsRelais(int tourDebut){
        Pilote pilote = this.equipe.getPiloteEnCours();
        Temps compteur = new Temps();
        for(int k = tourDebut ; k < this.calculNombreToursRelais() + tourDebut ; k++){
            compteur.add(equipe.prevoitTourX(k, pilote));
        }
        return compteur;
    }

    public int calculNombreRelaisMax(){
        boolean estUtile = true;
        int nombreTours = 0;
        int nombreRelais = 0;
        while(estUtile){
            Temps tempsRelais = this.calculTempsRelais(nombreTours);
            Temps tempsNeuf = this.calculTempsRelais(0);
            if(tempsRelais.enMilli() > tempsNeuf.enMilli() + TEMPS_CHANGEMENT_PNEUS){
                estUtile = false;
            }else{
                nombreTours += this.calculNombreToursRelais();
                nombreRelais++;
            }
        }
        return nombreRelais;
    }

    
}
