package com.example.rentalcarsapp.helper;
/**
 * Author by HUYNH NHAT MINH.
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 5/21/2021.
 * Company: FPT大学.
 */
public class RegexValidate {
    // String regex
    public static final String VALID_FULL_NAME = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W|_]+$";
    //public static final String VALID_URL = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)";
    public static final String VALID_EMAIL = "^[A-z][a-z0-9_\\.]{1,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";
    public static final String VALID_PHONE_NUMBER = "(84|0[3|5|7|8|9])+([0-9]{9})\\b";
    public static final String VALID_PASSWORD = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!*@#%^&+=]).*";


    // String massage error
    public static final String MESSAGE_ERROR_CONFIRM_PASSWORD = "Password and confirm password not matching.";
    public static final String MESSAGE_ERROR_FULL_NAME = "Full name excluding numbers and special characters.";
    //public static final String VALID_URL = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)";
    public static final String MESSAGE_ERROR_EMAIL = "Incorrect email format, eg admin@gmail.com";
    public static final String MESSAGE_ERROR_PHONE_NUMBER = "Phone number malformed e.g. 84832511369.";
    public static final String MESSAGE_ERROR_PASSWORD = "Password must be at least 8 characters including uppercase, lowercase letters, numbers and special characters.";
    public static final String MESSAGE_SHOW_RESET_PASSWORD = "Enter the email associated with your account and we'll send an email with instructions to reset your password.";

}
