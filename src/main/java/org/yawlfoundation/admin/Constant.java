package org.yawlfoundation.admin;

/**
 * Created by gary on 1/03/2017.
 */
public class Constant {

    public static final String RESULT="result";
    public static final String PLAIN="plain";
    public static final String EXCEPTION="exception";
    public static final String SUCCESS = "<success/>";

    public static final String FORWARD_TO_SPECIFICATION="forward:/specification";
    public static final String FORWARD_TO_CUSTOMSERVICE="forward:/customService";
    public static final String FORWARD_TO_USER="forward:/user";
    public static final String FORWARD_TO_CASE="forward:/case";
    public static final String FORWARD_TO_YAWL="forward:/yawl";
    public static final String FORWARD_TO_WORKITEM="forward:/workitem";


    public static final String DATABASE_DRIVER = "spring.datasource.driver-class-name";
    public static final String DATABASE_URL = "spring.datasource.url";
    public static final String DATABASE_USER = "spring.datasource.username";
    public static final String DATABASE_PASSWORD = "spring.datasource.password";


    public static final String SESSIONHANDLE_NAME="sessionHandle";

    public static final int SESSION_RETRY_TIMES=3;
    public static final String ZK_ENGINE_PATH="/engine";




    public static final String CASE_ENGINE_PREFIX="caseEngine:";

    public enum InterfaceType{
        INTERFACE_A,
        INTERFACE_B
    }

}
