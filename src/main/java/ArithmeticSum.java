import java.io.Serializable;

public class ArithmeticSum implements Serializable {
    private String driver;
    private String url;
    private String name;
    private String password;
    private int N;

    public ArithmeticSum() {
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }
}
