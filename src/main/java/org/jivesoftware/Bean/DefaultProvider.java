package org.jivesoftware.Bean;

public class DefaultProvider {
    private String driver;
    private String serverURL;
    private String username;
    private String password;
    private String testSQL;
    private String testBeforeUse;
    private String testAfterUse;
    private String minConnections;
    private String maxConnections;
    private String connectionTimeout;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTestSQL() {
        return testSQL;
    }

    public void setTestSQL(String testSQL) {
        this.testSQL = testSQL;
    }

    public String getTestBeforeUse() {
        return testBeforeUse;
    }

    public void setTestBeforeUse(String testBeforeUse) {
        this.testBeforeUse = testBeforeUse;
    }

    public String getTestAfterUse() {
        return testAfterUse;
    }

    public void setTestAfterUse(String testAfterUse) {
        this.testAfterUse = testAfterUse;
    }

    public String getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(String minConnections) {
        this.minConnections = minConnections;
    }

    public String getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(String maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
