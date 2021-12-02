package user;

/**
 * A class to maintain info about each client.
 */
public class UserInfo {
    private String firstName;
    private String lastName;
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
     * @param firstName
     */
    public UserInfo(String firstName, String accessToken, String tokenType, String idToken, String email) {
        this.firstName = firstName;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.idToken = idToken;
        this.email = email;
    }

    /**
     * return name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
