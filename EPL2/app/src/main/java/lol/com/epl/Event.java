package lol.com.epl;

/**
 * Created by arjun on 29/9/15.
 */
public class Event {
    String type;
    String team;
    String result;
    String min;
    String player;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getTypeId(String type) {
        int typeId= R.drawable.abc_ab_share_pack_mtrl_alpha;
        switch (type) {
            case "goal":
                typeId=R.drawable.football;
                break;
            case "yellowcard":
                typeId=R.drawable.yellow;
                break;
            case  "redcard":
                typeId = R.drawable.red;


    }
        return typeId;
    }
}
