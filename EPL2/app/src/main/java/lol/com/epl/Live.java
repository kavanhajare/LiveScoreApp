package lol.com.epl;

/**
 * Created by arjun on 26/9/15.
 */
public class Live {
    String timer;
    String match_id;
    String local;
    String visitor;
    String loc_score;

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVisitor() {
        return visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public String getLoc_score() {
        return loc_score;
    }

    public void setLoc_score(String loc_score) {
        this.loc_score = loc_score;
    }

    public String getVis_score() {
        return vis_score;
    }

    public void setVis_score(String vis_score) {
        this.vis_score = vis_score;
    }

    String vis_score;
}
