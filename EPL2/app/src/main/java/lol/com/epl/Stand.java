package lol.com.epl;

/**
 * Created by arjun on 25/9/15.
 */
public class Stand {
    String team;
    String pos;
    String win;
    String lose;
    int points;
    String rec;
    String icon;

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getLose() {
        return lose;
    }

    public void setLose(String lose) {
        this.lose = lose;
    }

    public String getIcon() {

        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconId(String icon){

        int iconId = R.drawable.chelsea;
        switch (icon) {
            case "Manchester City FC":
                iconId = R.drawable.city;
                break;
            case "Manchester United FC":
                iconId = R.drawable.united;
                break;
            case "West Ham FC":
                iconId = R.drawable.west_ham;;
                break;

            case "Arsenal FC":
                iconId = R.drawable.arsenal;
                break;
            case "Everton FC":
                iconId = R.drawable.everton;
                break;
            case "Swansea FC":
                iconId = R.drawable.swansea;;
                break;
            case "Crystal Palace FC":
                iconId = R.drawable.crystal;
                break;
            case "Leicester City FC":
                iconId = R.drawable.leicester;
                break;
            case "Tottenham Hotspur FC":
                iconId = R.drawable.hotspur;
                break;
            case "Watford FC":
                iconId = R.drawable.watford;
                break;
            case "Norwich FC":
                iconId = R.drawable.norwich;
                break;

            case "West Brom FC":
                iconId = R.drawable.west_brom;
                break;
            case "Liverpool FC":
                iconId = R.drawable.liverpool;
                break;
            case "Bournemouth FC":
                iconId = R.drawable.bourn;
                break;
            case "Chelsea FC":
                iconId = R.drawable.chelsea;
                break;
            case "Southampton FC":
                iconId = R.drawable.southampton;
                break;
            case "Aston Villa FC":
                iconId = R.drawable.aston_villa;
                break;
            case "Stoke City FC":
                iconId = R.drawable.stoke;
                break;
            case "Newcastle Utd FC":
                iconId = R.drawable.new_castle;
                break;

        }






return iconId;
    }

}
