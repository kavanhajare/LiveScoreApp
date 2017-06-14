package com.example.karanc.Others;

/**
 * Created by karanc on 20-06-2016.
 */
public class ListModel_Reviews {
    private String CollegeName="";
    private String Desc="";
    private String Stars="";

    /* Set Methods*/

    public void setCollegeName(String CollegeName){
        this.CollegeName=CollegeName;
    }

    public void setDesc(String Desc){
        this.Desc=Desc;
    }

    public void setStars(String Stars){
        this.Stars=Stars;
    }

    /* Get Methods */

    public String getCollegeName(){
        return this.CollegeName;
    }

    public String getDesc(){
        return this.Desc;
    }

    public String getStars(){
        return this.Stars;
    }
}
