package jaspreet.deliver.utils;

/**
 * Created by jaspret on 11/10/15.
 */
public interface Config {

    class AppConfig {

       public static boolean debugEnabled = true;
       public static String SDCARD_DIRECTORY = "/deliver";
       public static String THUMB_DIRECTORY = SDCARD_DIRECTORY + "/thumbs";
       public static String MY_PROFILE = "my_profile";
       public static String MY_PROFILE_TEMP = "my_profile_temp";
       public static int MIN_RANDUM_NUMBER = 100000;
       public static int MAX_RANDUM_NUMBER = 999999;
    }

    class Action{
        public static String ACTION_USER_REGISTERED_ACTIVITY = "ACTION_USER_REGISTERED_ACTIVITY";
        public static String ACTION_GROUP_MESSAGE = "ACTION_GROUP_MESSAGE";
        public static String ACTION_NEW_ACTIVITY = "ACTION_NEW_ACTIVITY";
        public static String BACK_KEY_EVENT ="BACK_KEY_EVENT";
        public static String ACTION_CONNECTION = "ACTION_CONNECTION";
        public static String ACTION_VERIFICATION="ACTION_VERIFICATION";
        public static String ACTION_UPDATE_CHAT_SCREEN = "ACTION_UPDATE_CHAT_SCREEN";
        public static String ACTION_CONTACTSYNC = "ACTION_CONTACTSYNC";
        public static String ACTION_MY_IMAGE = "ACTION_MY_IMAGE";
    }

    class XMPPConfig{

        public static int port= 5222;
        public static int socketTimeOut=30000;
    }



    /**
     * Country code name and codes
     */
    String[] country_codes = { "Afghanistan", "93", "Albania", "355",
            "Algeria", "213", "American Samoa", "1684", "Andorra", "376",
            "Angola", "244", "Anguilla", "1264", "Argentina", "54", "Aruba",
            "297", "Australia", "61", "Austria", "43", "Azerbaijan", "994",
            "Bahamas", "1242", "Bahrain", "973", "Bangladesh", "880",
            "Barbados", "1246", "Belarus", "375", "Belgium", "32", "Belize",
            "501", "Benin", "229", "Bermuda", "1441", "Bhutan", "975",
            "Bolivia", "591", "Botswana", "267", "Brazil", "55", "Brunei",
            "673", "Bulgaria", "359", "Burkina Faso", "226", "Burundi", "257",
            "Cameroon", "237", "Canada", "1", "Cape Verde", "238",
            "Cayman Islands", "1345", "Central African Republic", "236",
            "Chad", "235", "Chile", "56", "China", "86", "Colombia", "57",
            "Comoros", "269", "Cook Islands", "682", "Costa Rica", "506",
            "Cote d'Ivoire", "225", "Croatia", "385", "Cuba", "53", "Cyprus",
            "357", "Czech Republic", "420", "Denmark", "45", "Djibouti", "253",
            "Dominica", "1767", "Ecuador", "593", "Egypt", "20", "El Salvador",
            "503", "Equatorial Guinea", "240", "Eritrea", "291", "Estonia",
            "372", "Ethiopia", "251", "Fiji", "679", "Finland", "358",
            "France", "33", "Gabon", "241", "Gambia", "220", "Georgia", "995",
            "Germany", "49", "Ghana", "233", "Gibraltar", "350", "Greece",
            "30", "Greenland", "299", "Grenada", "1473", "Guadeloupe", "590",
            "Guam", "1671", "Guatemala", "502", "Guinea", "224",
            "Guinea-Bissau", "245", "Guyana", "592", "Haiti", "509",
            "Honduras", "504", "Hong Kong", "852", "Hungary", "36", "Iceland",
            "354", "India", "91", "Iraq", "964", "Iran", "98", "Israel", "972",
            "Italy", "39", "Jamaica", "1876", "Japan", "81", "Jordan", "962",
            "Kazakhstan", "7", "Kenya", "254", "Kiribati", "686", "Kuwait",
            "965", "Kyrgyzstan", "996", "Laos", "856", "Latvia", "371",
            "Lebanon", "961", "Lesotho", "266", "Liberia", "231", "Libya",
            "218", "Lithuania", "370", "Luxembourg", "352", "Madagascar",
            "261", "Malawi", "265", "Malaysia", "60", "Maldives", "960",
            "Mali", "223", "Malta", "356", "Marshall Islands", "692",
            "Martinique", "596", "Mauritania", "222", "Mauritius", "230",
            "Mexico", "52", "Moldova", "373", "Monaco", "377", "Mongolia",
            "976", "Montenegro", "382", "Montserrat", "1664", "Morocco", "212",
            "Mozambique", "258", "Namibia", "264", "Nauru", "674",
            "Netherlands", "31", "Netherlands Antilles", "599",
            "New Caledonia", "687", "New Zealand", "64", "Nicaragua", "505",
            "Niger", "227", "Nigeria", "234", "North Korea", "850",
            "Northern Ireland", "4428", "Norway", "47", "Oman", "968",
            "Pakistan", "92", "Palau", "680", "Panama", "507",
            "Papua New Guinea", "675", "Paraguay", "595", "Peru", "51",
            "Philippines", "63", "Poland", "48", "Portugal", "351",
            "Puerto Rico", "1787", "Qatar", "974", "Reunion", "262", "Romania",
            "40", "Rwanda", "250", "Samoa", "685", "Saudi Arabia", "966",
            "Senegal", "221", "Seychelles", "248", "Sierra Leone", "232",
            "Singapore", "65", "Slovakia", "421", "Slovenia", "386",
            "Solomon Islands", "677", "Somalia", "252", "South Africa", "27",
            "South Korea", "82", "Spain", "34", "Sri Lanka", "94", "Sudan",
            "249", "Suriname", "597", "Swaziland", "268", "Sweden", "46",
            "Switzerland", "41", "Syria", "963", "Taiwan", "886", "Tajikistan",
            "992", "Tanzania", "255", "Thailand", "66", "Togo", "228", "Tonga",
            "676", "Tunisia", "216", "Turkey", "90", "Turkmenistan", "993",
            "Turks and Caicos Islands", "1649", "Tuvalu", "688", "Uganda",
            "256", "Ukraine", "380", "United Arab Emirates", "971","United Kingdom","44",
            "United States", "1", "Uruguay", "598",
            "Uzbekistan", "998", "Venezuela", "58", "Vietnam", "84", "Yemen",
            "967", "Zambia", "260", "Zimbabwe", "263" };

}
