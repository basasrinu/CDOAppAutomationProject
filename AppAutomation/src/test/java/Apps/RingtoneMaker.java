package Apps;
/*
 * Srinivas created this class on the 26/04/2021
 */
public class RingtoneMaker implements VariableInterface {

    @Override
    public String getTestAppName() {
        return "Ringtone maker";
    }

    @Override
    public String getTestAppPackage() {
        return "com.korrisoft.ringtone.maker";
    }

    @Override
    public String getTestAppActivity() {
        return "NewHomeActivity";
    }

    @Override
    public Boolean getCallLogPermission() {
        return false;
    }
}
