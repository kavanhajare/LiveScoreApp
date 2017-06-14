package lol.com.epl;

import java.util.ArrayList;

/**
 * Created by arjun on 25/9/15.
 */
public class Fixture {
    String local;
    String visitor;
    String loc_score;
    String vis_score;
    String date;
    long date1;
    public long getDate1() {
        return date1;
    }

    public void setDate1(long date1) {
        this.date1 = date1;
    }


  /*  public Fixture(Fixture[] fix,ArrayList<Fixture> arr){


    }*/

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
