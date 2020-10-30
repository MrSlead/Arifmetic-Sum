package entityXML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "entries")
public class NumbersXMLFormat implements Serializable {
    @XmlElement(name = "entry")
    private List<Number> numbers;

    public NumbersXMLFormat(List<Number> numbers) {
        this.numbers = numbers;
    }

    public NumbersXMLFormat() {
    }

    public List<Number> getNumber() {
        return numbers;
    }
}
