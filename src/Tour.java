import java.time.Duration;

public class Tour extends Temps{
Duration tempsTour;
Pilote pilote;

    Tour(Duration temps, Pilote pilote) {
        this.tempsTour = temps;
        this.pilote = pilote;
    }

    public Duration getTempsTour() {
        return tempsTour;
    }

    public int getTempsEnSeconde(){
        return (int)tempsTour.toSeconds();
    }

    public int getTempsEnMillisecondes(){
        return (int)tempsTour.toMillis();
    }
}
