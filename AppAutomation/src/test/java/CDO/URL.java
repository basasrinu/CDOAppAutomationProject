package CDO;
/*
 * Srinivas created this class on the 03/05/2021
 */
public class URL extends BaseSetup {
    //Opt-in continue button url
    public String getOptInContinueButtonURL() {
        return appPackage+":id/content_accept_btn";
    }

    //ok button url of backbutton optin consent dialog
    public String getConsentDialogOkButtonURL() {
        return appPackage+":id/optin_consent_dialog_reuired_btn"; }

    //phone more info
    public String getPhonePermissionMore() {
        return appPackage+":id/content_more_1_tv"; }

    //contact more info
    public String getContactPermissionMore() {
        return appPackage+":id/content_more_1_tv"; }

    //permission deny button url
    public String getPermissionDenyURL() {
        if(androidVersion.equals("10"))
            return "com.android.permissioncontroller:id/permission_deny_button";
        else
            return "com.android.packageinstaller:id/permission_deny_button"; }

    //permission accept button url
    public String getPermissionAcceptURL() {
        if(androidVersion.equals("10"))
            return "com.android.permissioncontroller:id/permission_allow_button";
         else
            return "com.android.packageinstaller:id/permission_allow_button"; }

    //location, overlay & battery optimization screen continue button url
    public String getContinueButtonURL() {
        return appPackage +":id/optin_theme_cta_btn";
    }

    //Overlay toggle button url
    public String getOverLaySwitchButtonURL() {
        return "android:id/switch_widget";
    }

    // Contact image on After call screen icon url
    public String getAfterCallImageButtonURL() {
        return  appPackage+":id/rl_contactview_container"; }

    //After call activity
    public String getAfterCallActivity() {
        return  "com.calldorado.ui.aftercall.CallerIdActivity";
    }

    //Allow background location button url on Android version 10 & above
    public String getLocationAllowAlwaysButtonURL() {
        return "com.android.permissioncontroller:id/permission_allow_always_button"; }

    //Allow foreground location button url on Android version 10 & above
    public String getLocationAllowForegroundButtonURL() {
        return "com.android.permissioncontroller:id/permission_allow_foreground_only_button"; }

    //Allow location button url for below Android version 10
    public String getLocationAllowButtonURL() {
        return "com.android.packageinstaller:id/permission_allow_button";
    }
}
