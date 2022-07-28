import java.util.ArrayList;

public class Pilote{

    ArrayList<Temps> tours;
    String nom;
    Temps tempsReference;

    Pilote(String nom){
        this.nom = nom;
        tours = new ArrayList<Temps>();
        tempsReference = new Temps();
    }

    public String getNom() {
        return nom;
    }

    Pilote(String nom, Temps tempsReference) {
        tours = new ArrayList<Temps>();
        this.nom = nom;
        this.tempsReference = tempsReference;
    }

    public Temps getTempsReference(){
        return tempsReference;
    }

    public void addTour(Temps tour){
        tours.add(tour);
    }

    public void removeTour(Tour tour){
        tours.remove(tour);
    }

    public Temps getTour(int index){
        return tours.get(index);
    }

    public ArrayList<Temps> getTours(){
        return tours;
    }

    public boolean pasDeTemps(){
        return tours.isEmpty();
    }


}
