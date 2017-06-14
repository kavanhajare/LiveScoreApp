/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblayout;

/**
 *
 * @author karanc
 *
 */
import java.lang.Math.*;

import static java.lang.Math.random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author karanc
 */
public class DBLayout 
{
 //   static final String JDBC_DRIVER="com.mysql.jdbc.Driver";

    static final String URL = "jdbc:mysql://localhost:3306/questions";
    static final String user = "masked";
    static final String pwd = "www1";

    public static void main(String args[]) throws SQLException {
        int ch, q, m, id = 0;
        String itr = null;
 // System.out.println("-*gjhgjhgjhg-*-*-*-Question Paper Generator*--*-*-*-*"); 

        Thread t = null;
        Scanner sc = new Scanner(System.in);

        Connection con = null;
        ResultSet rs = null;
        System.out.println("-*-*-*-*-Question Paper Generator*--*-*-*-*");
        System.out.println("Press Y to get questions");
        System.out.println("Press E to exit");
        PreparedStatement pst = null;

        try {
            //  Class.forName("com.mysql.jdbc.driver");
            con = DriverManager.getConnection(URL, user, pwd);
            do {
                System.out.println("Press Y to get questions");
                String f = sc.next();
        //System.out.println(f);
                // while(f!="Y"||f!="y");
                System.out.println("Subjects: ");
                System.out.println("1.MFCS");
                System.out.println("2.Data Communication & Networking");
                System.out.println("Enter Subject of interest: ");
                ch = sc.nextInt();

                if (ch == 1) {
                    System.out.println("The Modules:");
                    System.out.println("1.Relations & Functions");
                    System.out.println("2.Trees & Cut-sets");
                    System.out.println("3.Permutations & Combinations");
                    System.out.println("Enter Any of Above");
                    int r = sc.nextInt();
    //   System.out.println(r);

                    if (r == 1) {
                        System.out.println("Enter No of Questions");
                        System.out.println("Enter Weightage");
                        q = sc.nextInt();
                        m = sc.nextInt();
                        for(int ra=0;ra<q;ra++)
                        {
                        if (m == 1) {
                       pst = con.prepareStatement("SELECT * from relation ORDER BY RAND() LIMIT 1 ");
                         pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                           
                            }
                        }
                        
                        if (m == 2) {
                            pst = con.prepareStatement("SELECT * from relation  ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        if (m == 3) {
                            pst = con.prepareStatement("SELECT * from relation ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        System.out.println(m);
                        if (m != 2 && m != 3 && m != 1) {
                            System.out.println("No questions of such weightage");
                        }
                        }
                    } else if (r == 2) {
                        System.out.println("Enter No of Questions");
                        System.out.println("Enter Weightage");
                        q = sc.nextInt();
                        m = sc.nextInt();
                        if (m == 1) {
                            pst = con.prepareStatement("SELECT * from trees  ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        if (m == 2) {
                            pst = con.prepareStatement("SELECT * from trees ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        if (m == 3) {
                            pst = con.prepareStatement("SELECT * from trees ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        if (m == 5) {
                            pst = con.prepareStatement("SELECT * from trees ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        if (m != 1 && m != 2 && m != 3 && m != 5) {
                            System.out.println("No questions of such weightage");

                        }
                    } else {
                        System.out.println("Enter No of Questions");
                        System.out.println("Enter Weightage");
                        q = sc.nextInt();
                        m = sc.nextInt();
                        if (m == 1) {
                            pst = con.prepareStatement("SELECT * from mfcs ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        if (m == 2) {
                            pst = con.prepareStatement("SELECT * from mfcs ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }

                        }
                        if (m == 3) {
                            pst = con.prepareStatement("SELECT * from mfcs ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        if (m == 5) {
                            pst = con.prepareStatement("SELECT * from mfcs ORDER BY RAND() LIMIT 1");
                            pst.executeQuery();
                            rs = pst.executeQuery();
                            while (rs.next()) {
                                System.out.println(rs.getInt(1));
                                System.out.println(":");
                                System.out.println(rs.getString(2));
                                /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                                 System.out.println();*/
                            }
                        }
                        if (m != 1 && m != 2 && m != 3 && m != 5) {
                            System.out.println("No questions of such weightage");

                        }
                    }
                }
                if (ch == 2) {
                    System.out.println("Data Communication & Networking");
                    System.out.println("Enter No of Questions");
                    System.out.println("Enter Weightage");

                    q = sc.nextInt();
                    m = sc.nextInt();
                    for(int raj=0;raj<q;raj++)
                    {
                    if (m == 1) {
                        pst = con.prepareStatement("SELECT * from answers ORDER BY RAND() LIMIT 1");
                        pst.executeQuery();
                      rs = pst.executeQuery();
                        while (rs.next()) {
                            System.out.println(rs.getInt(1));
                            System.out.println(":");
                            System.out.println(rs.getString(2));
                            /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                             System.out.println();*/
                        }
                    }
                    if (m == 2) {
                        pst = con.prepareStatement("SELECT * from answers ORDER BY RAND() LIMIT 1");
                        pst.executeQuery();
                        rs = pst.executeQuery();
                        while (rs.next()) {
                            System.out.println(rs.getInt(1));
                            System.out.println(":");
                            System.out.println(rs.getString(2));
                            /*System.out.println(rs.getInt("id")+ ".\t" +rs.getString("name"));
                             System.out.println();*/
                        }
                    }
                    if (m!=1 && m != 2 && m != 3) {
                        System.out.println("No questions of Such Weightage");
                    }
                }
                System.out.println("Do you want more Questions: ");
                itr = sc.next();

            }
            }
                
           while (itr.equals("y") || itr.equals("Y"));
        
            if (!itr.equals("y") || !itr.equals("Y")) {
                System.out.println("Execution Ended");
                System.out.println("Visit again");
            }
                  
        } catch (SQLException e) {
            Logger log = Logger.getLogger(Retrieve.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
         //e.printStackTrace();

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                Logger log;
                log = Logger.getLogger(Retrieve.class.getName());
                log.log(Level.WARNING, se.getMessage(), se);
            }
        }        
        
    }
    }

   
/**
 * @param args the command line arguments
 */
