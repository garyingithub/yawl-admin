package org.yawlfoundation.admin;


/**
 * Created by gary on 28/02/2017.
 */
public class AdminException extends Exception {

    public AdminException(String action,String message){

        super(action+":"+message);
    }

}
