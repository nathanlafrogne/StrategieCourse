public class Course {

    int temperature;
    Temps duree;
    double[] gapToLeader;
    double[] gapAhead;
    Equipe[] voitures;

    Course(int temperature, Temps duree) {
        this.temperature = temperature;
        this.duree = duree;
        this.gapToLeader = new double[this.voitures.length];
        this.gapAhead = new double[this.voitures.length];
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

}
