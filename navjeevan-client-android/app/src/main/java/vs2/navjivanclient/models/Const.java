package vs2.navjivanclient.models;

import java.util.ArrayList;

/**
 * Created by antarix on 15/09/15.
 */
public class Const {

    public  static  final int HOTEL_ID = 1;

    //public static final String SERVER_PATH = "http://vs2.in/FoodDelivery/userApi/";
   // public static final String SERVER_PATH = "http://52.7.138.107/FoodDelivery_test/userApi/";
   // public static final String SERVER_PATH = "http://52.7.138.107/FoodDelivery/userApi/";


    public static final String SERVER_PATH = "http://192.168.43.220:80/userApi/";

    public static final String GET_OFFER_LIST = SERVER_PATH + "get_available_offer.php?";
    public static final String GET_AREA_LIST = SERVER_PATH + "get_area_list.php";
    public static final String GET_CATEGORY = SERVER_PATH +"get_category.php?hotelId=1";

    //FoodItem

    public static final String GET_FOODITEM = SERVER_PATH +"get_menu_item.php?categoryId=";

    public  static String USER_LOGIN = SERVER_PATH + "user_login.php?";
    public  static String USER_REGISTER = SERVER_PATH + "user_registration.php?";
    public  static String ADD_ADDRESS = SERVER_PATH + "add_address.php?";
    public  static String UPDATE_ADDRESS = SERVER_PATH + "update_address.php?";

    public static ArrayList<FoodCart> sCartItems;

    //Order
    public static final String PLACE_ORDER = SERVER_PATH +"add_order.php";

    public static final String MY_ORDER = SERVER_PATH +"get_my_order.php?userId=";

    public static final String CANCEL_ORDER = SERVER_PATH +"user_cancel_order.php?orderId=";


    //PROFILE
    public static final String UPDATE_PROFILE = SERVER_PATH +"update_profile.php?userId=";

    public static final String IMAGE_DIRECTORY = ".Navjeevan";


    public static final int HOTEL_STATUS_ON = 1;
    public static final int HOTEL_STATUS_OFF = 0;

    public static final int NOTIFICATION_SHOW = 1;
    public static final int NOTIFICATION_HIDE = 2;
    public static final int NOTIFICATION_OFFER = 3;

    public static final int HOME_DELIVERY=1;
    public static final int TAKE_AWAY=2;

    public static final int OFFER_CASH_DISCOUNT =1;
    public static final int OFFER_TAKE_AWAY =2;
    public static final int OFFER_ITEM_FREE =3;


 /*   public static final Map<String, String> areaPincodes;
    static
    {
        areaPincodes = new HashMap<String, String>();

        areaPincodes.put("0", "Select your area");
        areaPincodes.put("1", "Vapi Town");
         areaPincodes.put("2", "Vapi Chala");
        areaPincodes.put("3", "Vapi Gunjan");
        areaPincodes.put("4", "Vapi GIDC");
        areaPincodes.put("5", "Vapi Chanod coloni");

    }*/
/*    public static String[] arrayList = null;
    public static String[] getAreaList() {
        try {
            arrayList = new String[6];
            arrayList[0] = "Select your area";
            arrayList[1] = "Vapi Town";
            arrayList[2] = "Vapi Chala";
            arrayList[3] = "Vapi Gunjan";
            arrayList[4] = "Vapi GIDC";
            arrayList[5] = "Vapi Chanod coloni";
        } catch (Exception e) {
            // TODO: handle exception
        }
        return arrayList;
    }*/

}
