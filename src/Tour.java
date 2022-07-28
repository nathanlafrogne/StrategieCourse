public class Tour extends Temps{
Temps tempsTour;
Pilote pilote;

    Tour(Temps temps, Pilote pilote) {
        this.tempsTour = temps;
        this.pilote = pilote;
    }

    public Temps getTempsTour() {
        return tempsTour;
    }

    public int getTempsEnSeconde(){
        return (int)tempsTour.enSeconde();
    }

    public int getTempsEnMillisecondes(){
        return (int)tempsTour.enMilli();
    }
}
