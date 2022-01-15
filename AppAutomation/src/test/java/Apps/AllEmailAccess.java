package Apps;
/*
 * Srinivas created this class on the 26/04/2021
 */
public class AllEmailAccess implements VariableInterface {
    @Override
    public String getTestAppName() {
        return "AllEmailAccess";
    }

    @Override
    public String getTestAppPackage() {
        return  "info.myapp.allemailaccess";
    }

    @Override
    public String getTestAppActivity() {
        return "MainActivity";
    }

    @Override
    public Boolean getCallLogPermission() {
        return true;
    }
}
