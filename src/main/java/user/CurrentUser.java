package user;

import org.eclipse.jetty.server.Authentication;

public class CurrentUser {
    public static UserInfo userInfo;

    public CurrentUser(UserInfo info) {
        userInfo = info;
    }


}
