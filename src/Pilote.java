import java.time.Duration;
import java.util.ArrayList;

public class Pilote{

    ArrayList<Tour> tours;
    String nom;
    Duration tempsReference;

    Pilote(String nom){
        this.nom = nom;
        tours = new ArrayList<Tour>();
        tempsReference = Duration.ZERO;
    }

    public String getNom() {
        return nom;
    }

    Pilote(String nom, Duration tempsReference) {
        tours = new ArrayList<Tour>();
        this.nom = nom;
        this.tempsReference = tempsReference;
    }

    public Duration getTempsReference(){
        return tempsReference;
    }

    public void addTour(Tour tour){
        tours.add(tour);
    }


    public Tour getTour(int index){
        return tours.get(index);
    }

    public ArrayList<Tour> getTours(){
        return tours;
    }

    public boolean pasDeTemps(){
        return tours.isEmpty();
    }


}
