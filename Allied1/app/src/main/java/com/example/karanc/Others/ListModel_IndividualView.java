package com.example.karanc.Others;

/**
 * Created by karanc on 11-06-2016.
 */
public class ListModel_IndividualView {

    private String CollegeName;
    private String CollegeAddress;
    private String CollegeFees;
    private String CollegePhNo;
    private String CollegeMailAddress;
    private String  CollegeWebsite;
    private String CollegeType;
    private String CollegeBranch;
    private String CollegeBranchSeats;
    /* Set Methods*/

    public void setCollegeAddress(String CollegeAddress){
        this.CollegeAddress=CollegeAddress;
    }
    public void setCollegePhNo(String CollegePhNo){
        this.CollegePhNo=CollegePhNo;
    }
    public void setCollegeMailAddress(String CollegeMailAddress){this.CollegeMailAddress=CollegeMailAddress;}
    public void setCollegeWebsite(String CollegeWebsite){
        this.CollegeWebsite=CollegeWebsite;
    }
    public void setCollegeType(String CollegeType){
        this.CollegeType=CollegeType;
    }
    public void setCollegeBranch(String CollegeBranch){
        this.CollegeBranch=CollegeBranch;
    }
    public void setCollegeBranchSeats(String CollegeBranchSeats){this.CollegeBranchSeats=CollegeBranchSeats;}
    public void setCollegeName(String CollegeName){
        this.CollegeName=CollegeName;
    }
    public void setCollegeFees(String Fees){
        this.CollegeFees=Fees;
    }

    /* Get Methods */

    public String getCollegeAddress(){return this.CollegeAddress;}
    public String getCollegePhNo(){return this.CollegePhNo;}
    public String getCollegeMailAddress(){return this.CollegeMailAddress;}
    public String getCollegeWebsite(){return this.CollegeWebsite;}
    public String getCollegeType(){return this.CollegeType;}
    public String getCollegeBranch(){return this.CollegeBranch;}
    public String getCollegeBranchSeats(){return this.CollegeBranchSeats;}
    public String getCollegeName(){
        return this.CollegeName;
    }
    public String getCollegeFees(){
        return this.CollegeFees;
    }

}
