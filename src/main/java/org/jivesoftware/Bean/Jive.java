package org.jivesoftware.Bean;

public class Jive {

    private AdminConsole adminConsole;
    private String locale;
    private ConnectionProvider connectionProvider;
    private Database database;
    private String setup;

    public AdminConsole getAdminConsole() {
        return adminConsole;
    }

    public void setAdminConsole(AdminConsole adminConsole) {
        this.adminConsole = adminConsole;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public ConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    public void setConnectionProvider(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }
}
