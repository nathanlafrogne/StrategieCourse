
import java.util.ArrayList;


/*
* Classe qui sert d'unité parce que la classe Duration déjà existante n'avait pas une 
* méthode que je voulais.
*/


public class Temps{
    int heure;
    int minutes;
    int secondes;
    int milliemes;

    public Temps(int heure, int minutes, int secondes, int milliemes) {
        this.heure = heure;
        this.minutes = minutes;
        this.secondes = secondes;
        this.milliemes = milliemes;
    }

    public Temps(int minutes, int secondes, int milliemes) {
        this.minutes = minutes;
        this.secondes = secondes;
        this.milliemes = milliemes;
    }

    public Temps(String chaine) {
        String[] parts = chaine.split(":");
        this.minutes = Integer.parseInt(parts[0]);
        this.secondes = Integer.parseInt(parts[1]);
        this.milliemes = Integer.parseInt(parts[2]);
    }

    public Temps() {
        this.heure = 0;
        this.minutes = 0;
        this.secondes = 0;
        this.milliemes = 0;
    }

// Accesseurs

    public int getHeure() {
        return heure;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSecondes() {
        return secondes;
    }

    public int getMillisecondes() {
        return milliemes;
    }


    public Temps addMili(int milliemes) {
        this.addSecondes(milliemes/1000);
        milliemes -= (int)(milliemes/1000)*1000;
        this.milliemes += milliemes;
        return this;
    }

    public Temps addSecondes(int secondes) {
        this.addMinutes(secondes/60);
        secondes -= (int)(secondes/60)*60;
        this.secondes += secondes;
        return this;
    }

    public Temps addSecondesReel(double secondes){
        int milli = (int)((secondes-(int)(secondes))*1000);
        int sec = (int)secondes;
        this.addMili(milli + sec*1000);
        return this;
    }

    public Temps addMinutes(int minutes) {
        this.addHeure(minutes/60);
        minutes -= (int)(minutes/60)*60;
        this.minutes += minutes;
        return this;
    }

    public Temps addHeure(int heure) {
        this.heure += heure;
        return this;
    }

    public Temps add(Temps temps){
        this.addMili(temps.getMillisecondes());
        this.addSecondes(temps.getSecondes());
        this.addMinutes(temps.getMinutes());
        this.addHeure(temps.getHeure());
        return this;
    }

    public double enSeconde(){
        return this.heure*3600+this.minutes*60+this.secondes+this.milliemes/1000;
    }

    public double enMilli(){
        return this.heure*3600000+this.minutes*60000+this.secondes*1000+this.milliemes;
    }

    /**
     * Retourne la différence entre le temps en paramètre et le temps de l'instance
     * @param temps : Temps à comparer avec cette instance
     * @return Temps différence entre les deux temps
     */
    public double differenceAvecEnMilli(Temps temps){
        return -(this.enMilli() - temps.enMilli());
    }

    /**
     * @return false si le temps est nul (0), true sinon
     */
    public boolean estNull(){
        return this.heure == 0 && this.minutes == 0 && this.secondes == 0 && this.milliemes == 0;
    }

    /**
     * Calcul la moyenne entre les deux temps avec un facteur d'importance
     * @param temps : Temps à moyenner avec cette instance
     * @param importance : Facteur d'importance par rapport a l'instance
     * @return Temps moyenne entre les deux temps
     */
    public Temps moyenne(Temps temps,double coef){
        double ceTemps = this.enMilli();
        double autreTemps = temps.enMilli();
        double moyenne = (ceTemps+(coef*autreTemps))/(1+coef);
        Temps ret = new Temps();
        ret.addMili((int)moyenne);
        return ret;
    }

    /**
     * Calcul la moyenne entre plusieurs temps avec les valeurs abérantes
     * @param temps : Liste de temps à moyenner
     * @return Temps moyenne entre les temps
     */
    public static Temps moyenneGeneraleNaive(ArrayList<Temps> temps){
        double compteur = 0;
        for(int k = 0 ; k < temps.size() ; k++){
            compteur += temps.get(k).enMilli();
        }
        compteur /= temps.size();
        Temps ret = new Temps();
        ret.addMili((int)compteur);
        return ret;
    }

}