package com.ids.mercury.utils

import com.ids.mercury.controller.MyApplication

/**
 * Created by Ibrahim on 8/23/2017.
 */

object AppConstants {



    const val MDPI = "32"
    const val HDPI = "24"
    const val XHDPI = "48"
    const val XXHDPI = "72"
    const val XXXHDPI = "96"
    const val MDPI_FOLDER = "mdpi"
    const val HDPI_FOLDER = "hdpi"
    const val XHDPI_FOLDER = "xhdpi"
    const val XXHDPI_FOLDER = "xxhdpi"
    const val DRAWABLE = "drawable"
    const val LOGIN_FRAG = "login"
    const val SIGNUP_FRAG = "signup"
    const val FORGET_FRAG = "forget"
    const val VERIFICATION_FRAG = "verification"
    const val SET_PASSWORD_FRAG = "setPassword"
    const val LOGIN = 1

    const val LANG_ENGLISH = "en"
    const val LANG_ARABIC = "ar"
    const val SELECTED_LANGUAGE = "key_language_code"
    const val DEVICE_TYPE_ANDROID = "2"
    const val GENERAL_NOTIFICATION = "general_notification"
    const val IS_LOGGED_IN = "isloggedin"
    const val CNM = "cnm"
    const val CPS = "cps"
    const val MEMBER_ID = "mid"
    const val SESSION_ID = "session_id"
    const val ACTIVITY_ID = "activity_id"
    const val DEVICE_ID = "device_id"


    const val FIREBASE_FORCE_UPDATE = "android_force_update"
    const val FIREBASE_ANDROID_VERSION = "android_version"
    const val FIREBASE_URL_LIST = "url_list"
    const val FIREBASE_PAYMENT_JS_URL = "PaymentJsUrl"
    const val FIREBASE_CHECKOUT_SESSION_URL = "CreateCheckoutSessionUrl"
    const val FIREBASE_PASSWORD = "PASSWORD"
    const val FIREBASE_OPERATOR_ID = "OPERATOR_ID"
    const val FIREBASE_MERCHANT_ID = "MERCHANT_ID"
    const val FIREBASE_SENDER_EMAIL = "sender_email"
    const val FIREBASE_SENDER_PASSWORD = "sender_password"
    const val FIREBASE_RECEIVER_EMAIL = "receiver_email"
    const val FIREBASE_EMAIL_SUBJECT = "email_subject"


    const val MENU_RULES_LABEL = "rules"
    const val MENU_ACADEMIES_LABEL = "academies"
    const val MENU_GYM_CLASSES_LABEL = "gymClasses"
    const val MENU_CLASS_SCHEDULE_LABEL = "classSchedule"
    const val MENU_CSR_LABEL = "csr"
    const val FROM_PAGE = "from_page"
    const val IMAGE_URL = "image_url"
    const val MENU_MEMBERSHIP_STATUS_ID = 1
    const val MENU_FITNESS_ID = 2
    const val MENU_ACADEMIES_ID = 3
    const val MENU_RENT_A_COURT_ID = 4
    const val MENU_PAYMENT_HISTORY_ID = 5
    const val MENU_GIFT_CARD_ID = 6
    const val MENU_GUESS_PASSES_ID = 7
    const val MENU_REFFER_A_FRIEND_ID = 8
    const val MENU_CSR_ID = 9
    const val MENU_RULES_AND_REGULATIONS_ID = 10
    const val MENU_FEEDBACK = 11

    const val TYPE_GYM = 1
    const val TYPE_PT = 2

    const val PAGE_CLASSES = 1
    const val PAGE_ACADEMY = 2

    const val TYPE_EMAIL = 1
    const val TYPE_SMS = 2

    const val TYPE_CSR = 1
    const val TYPE_RULES = 2

    var htmlText = """<html>
    <head>
        <script src=myUrl
                data-error="errorCallback"
                data-cancel="cancelCallback"
        data-complete="http://um.ids.com.lb/receiptPage">
            </script>

        <script type="text/javascript">
            function errorCallback(error) {
                  console.log(JSON.stringify(error));
            }
            function cancelCallback() {
                  console.log('Payment cancelled');
            }
            
            Checkout.configure({
                merchant:'<your_merchant_id>',
                order: {
                    amount: <amount>,
                    currency: 'USD',
                    description: 'Ordered goods',
                   id: '<unique_order_id>'
                    },
                    session: {
                      id: '<checkout_session_id>'
                    },
                interaction: {
                  operation: 'PURCHASE',
                    merchant: {
                        name: 'Your merchant name',
                        address: {
                            line1: '200 Sample St',
                            line2: '1234 Example Town'     
                        }    
                    }
                }
            });
        </script>
    </head>
    <body onload="Checkout.showPaymentPage();">
    </body>
</html>"""
}
