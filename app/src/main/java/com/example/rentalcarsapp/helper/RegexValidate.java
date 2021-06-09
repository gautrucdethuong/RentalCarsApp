package com.example.rentalcarsapp.helper;
/**
 * Author by HUYNH NHAT MINH.
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 5/21/2021.
 * Company: FPT大学.
 */
public class RegexValidate {
    // String regex
    public static final String VALID_FULL_NAME = "^[a-z A-Z]+";
    public static final String VALID_URL = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)";
    public static final String VALID_EMAIL = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";
    public static final String VALID_PHONE_NUMBER = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    public static final String VALID_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
}
