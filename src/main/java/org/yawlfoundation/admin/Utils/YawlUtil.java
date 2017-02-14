package org.yawlfoundation.admin.Utils;

import org.yawlfoundation.yawl.util.StringUtil;

/**
 * Created by root on 17-2-8.
 */
public class YawlUtil {

    public static String failureMessage(String msg) {
        return StringUtil.wrap(StringUtil.wrap(msg, "reason"), "failure");
    }


    public static String successMessage(String msg) {
        return StringUtil.wrap(msg, "success");
    }


    public static String getBuildProperties() {
        return "<buildproperties> " +
                "  <OSVersion>10.11.4</OSVersion>" +
                "  <BuiltBy>adamsmj</BuiltBy>" +
                "  <BuildNumber>1,401</BuildNumber>" +
                "  <OS>Mac OS X</OS>" +
                "  <JavaVersion>1.8.0_45</JavaVersion>" +
                "  <Version>4.1</Version>" +
                "  <BuildDate>2016/05/10 12:08</BuildDate>" +
                "  </buildproperties>";
    }
}
