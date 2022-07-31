import java.time.Duration;
import java.util.ArrayList;

public class Course {

    int temperature;
    Duration duree;
    double[] gapToLeader;
    double[] gapAhead;
    ArrayList<Equipe> voitures;

    /**
     * Constructeur de la classe Course par défaut
     * @param temperature initialisé à 25°C
     * @param duree initialisé à 6h
     */
    Course(){
        this.temperature = 25;
        this.duree = Duration.ofHours(6);
        this.voitures = new ArrayList<Equipe>();
    }

    /**
     * Constructeur de la classe Course
     * @param temperature : temperature de la course
     * @param duree : durée de la course
     */
    Course(int temperature, Duration duree) {
        this.temperature = temperature;
        this.duree = duree;
        this.voitures = new ArrayList<Equipe>();
    }

    /**
     * Méthode qui permet d'actualiser la température de course
     * @return temperature : la nouvelle température de course
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /**
     * Méthode qui permet d'ajouter une voiture à la course.
     * /!\ Doit être ajoutée avant le debut de la simulation
     * @param voiture : la voiture à ajouter
     */
    public void addVoiture(Equipe voiture) {
        this.voitures.add(voiture);
        
    }

    /**
     * Initialise les coefs d'une équipe
     * @param equipe : l'équipe à initialiser
     */

    public static void main(String args[]){
        Course course = new Course();

        // Debut de la partie qui peut etre remplaçée par un truc automatique
        Equipe equipe1 = new Equipe("Equipe 1");
        Pilote pilote1 = new Pilote("Pilote 1", Duration.ofSeconds(85)); // 1:25:000
        Pilote pilote2 = new Pilote("Pilote 2", Duration.ofSeconds(85).plusMillis(300)); // 1:25:300
        equipe1.addPilote(pilote1);
        equipe1.addPilote(pilote2);
        course.addVoiture(equipe1);
        // Fin de la partie remplaçable
        //Duration temp1 = Duration.ofMillis(pilote1.tempsReference.toMillis());
        //equipe1.calculCoefGainFuel(pilote1.getTempsReference().plusMili(500), pilote1.getTempsReference());
        //equipe1.calculCoefPertePneu(pilote1.getTempsReference(), pilote1.getTempsReference().addMili(800));

        Prevision simulation = new Prevision(equipe1);
        simulation.calculNombreRelaisMax();
    }

}
