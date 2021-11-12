package user;

/**
 * A class to maintain info about each client.
 */
public class UserInfo {
    private String name;
    private String accessToken;
    private String tokenType;
    private String idToken;
    private String email;

    private int eventId;


    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Constructor
     * @param name
     */
    public UserInfo(String name, String accessToken, String tokenType, String idToken, String email) {
        this.name = name;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.idToken = idToken;
        this.email = email;
    }

    /**
     * return name
     * @return
     */
    public String getName() {
        return name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getEmail() {
        return email;
    }

    public int getEventId() {
        return eventId;
    }
}
