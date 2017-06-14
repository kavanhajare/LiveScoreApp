package in.vs2.navjeevanadmin.models;

/**
 * Created by antarix on 15/09/15.
 */
public class Const {

   // public static final String SERVER_PATH = "http://52.7.138.107/FoodDelivery_test/";
   // public static final String SERVER_PATH = "http://52.7.138.107/FoodDelivery/";


    public static final String SERVER_PATH = "http://192.168.43.220:80/adminApi/";

    public static  final int HOTEL_ID = 1;

    //Verification
    public static final String VERIFY_ADMIN = SERVER_PATH +"update_hotel_deviceid.php?hotelId=1";

    public static final String UPDATE_HOTEL_STATUS = SERVER_PATH +"update_hotel_status.php?hotelId=1";


    //Categories
    public static final String GET_CATEGORY = SERVER_PATH +"get_category.php?hotelId=1";

    public static final String ADD_CATEGORY = SERVER_PATH +"add_category.php?hotelId=1";

    public static final String UPDATE_CATEGORY = SERVER_PATH +"update_category.php?hotelId=1";



    //FoodItem

    public static final String GET_FOODITEM = SERVER_PATH +"get_menu_item.php?categoryId=";

    public static final String ADD_FOODITEM = SERVER_PATH +"add_menu_item.php?categoryId=";

    public static final String UPDATE_FOODITEM = SERVER_PATH +"update_menu_item.php?menuId=";

    public static final String UPDATE_FOODITEM_STATUS = SERVER_PATH +"update_menu_item_status.php?menuId=";

    //Offers
    public static final String GET_OFFERS = SERVER_PATH +"get_offer_list.php?";
    public static final String ADD_OFFERS = SERVER_PATH +"add_offer.php?";
    public static final String UPDATE_OFFERS = SERVER_PATH +"update_offer.php?";
    public static final String DELETE_OFFERS = SERVER_PATH +"delete_offer.php?OfferId=";




    //ORDERS

    public static final String GET_ORDER = SERVER_PATH +"get_hotel_order.php?hotelId=1";

    public static final String GET_PENDING_ORDER = SERVER_PATH +"get_hotel_pending_order.php?hotelId=1&status=1";

    public static final String ACCEPT_ORDER = SERVER_PATH +"hotel_accepted_order.php?orderId=";

    public static final String CANCEL_ORDER = SERVER_PATH +"hotel_cancel_order.php?orderId=";


    //REPORTS
    public static final String GET_MONTHLY_REPORT = SERVER_PATH +"get_month_report.php?hotelId=1";
    //http://vs2.in/FoodDelivery/get_month_report.php?hotelId=1&month=09&year=2015

    public static final String GET_DAILY_REPORT = SERVER_PATH +"get_daily_report.php?hotelId=1";
    //http://vs2.in/FoodDelivery/get_daily_report.php?hotelId=1&date=09/26/2015

    public static final int OFFER_CASH_DISCOUNT =1;
    public static final int OFFER_TAKE_AWAY =2;
    public static final int OFFER_ITEM_FREE =3;

    public static String[] getOfferTypeList() {
        String[] arrayList = new String[4];
        try {
            arrayList = new String[4];
            arrayList[0] = "Select Offer Type";
            arrayList[1] = "Cash Discount";
            arrayList[2] = "Take Away Discount";
            arrayList[3] = "Item Free";
        } catch (Exception e) {
            // TODO: handle exception
        }
        return arrayList;
    }
}
