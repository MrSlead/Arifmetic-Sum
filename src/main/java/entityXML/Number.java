package entityXML;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement()
public class Number implements Serializable {
    @XmlElement(name = "field")
    private long number;

    public Number(long n) {
        number = n;
    }

    public Number() {

    }

    public long getNumber() {
        return number;
    }
}
