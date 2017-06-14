package com.example.karanc.Others;

/**
 * Created by karanc on 04-06-2016.
 */
public class ListModel {
    private String CollegeName="";
    private String Fees="";
    private String Coll_Image="";
    private String ImageId="";


    /* Set Methods*/

    public void setCollegeName(String CollegeName){
        this.CollegeName=CollegeName;
    }

    public void setFees(String Fees){
        this.Fees=Fees;
    }

    public void setColl_Image(String Coll_Image){
        this.Coll_Image=Coll_Image;
    }

    public void setImageId(String ImageId){
        this.ImageId=ImageId;
    }


    /* Get Methods */

    public String getCollegeName(){
        return this.CollegeName;
    }

    public String getFees(){
        return this.Fees;
    }

    public String getColl_Image(){
        return this.Coll_Image;
    }

    public String getImageId(){
        return this.ImageId;
    }
}
