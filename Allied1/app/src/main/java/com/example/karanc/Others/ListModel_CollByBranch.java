package com.example.karanc.Others;

/**
 * Created by karanc on 13-06-2016.
 */
public class ListModel_CollByBranch {
    private String CollegeName="";
    private String CollegeSeats="";
    private String Coll_Image="";


    /* Set Methods*/

    public void setCollegeName(String CollegeName){
        this.CollegeName=CollegeName;
    }

    public void setCollegeSeats(String CollegeSeats){
        this.CollegeSeats=CollegeSeats;
    }

    public void setColl_Image(String Coll_Image){
        this.Coll_Image=Coll_Image;
    }

    /* Get Methods */

    public String getCollegeName(){
        return this.CollegeName;
    }

    public String getCollegeSeats(){
        return this.CollegeSeats;
    }

    public String getColl_Image(){
        return this.Coll_Image;
    }
}
