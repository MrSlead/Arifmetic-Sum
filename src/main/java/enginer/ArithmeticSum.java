package enginer;

import db_info.DataBaseInfo;
import dto.NumberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArithmeticSum {
    private Logger logger = LoggerFactory.getLogger(ArithmeticSum.class);
    private NumberDto numberDto;

    public ArithmeticSum(DataBaseInfo dataBaseInfo) {
        numberDto = new NumberDto(dataBaseInfo);
    }

    public String arithmeticSum() {
       if(numberDto.start()) {
           int [] numbers = parseXmlFile();

           BigInteger result = BigInteger.valueOf((1 + numbers[numbers.length - 1]))
                                .multiply(BigInteger.valueOf(numbers.length / 2));

           return result.toString();
       }

       return BigInteger.valueOf(-1).toString();
    }

    private int [] parseXmlFile() {
        DocumentBuilder documentBuilder = null;
        try {
            byte[] content = Files.readAllBytes(Paths.get(Paths.get(getClass().getResource("/res/2.xml").toURI()).toString()));
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(content));

            NodeList nodeList = document.getElementsByTagName("entry");
            int size = nodeList.getLength();
            int [] numbers = new int[size];

            for(int x = 0; x < numbers.length; x++) {
                numbers[x] = Integer.parseInt(nodeList.item(x).getAttributes().getNamedItem("field").getNodeValue());
            }

            return numbers;
        } catch (ParserConfigurationException | SAXException | URISyntaxException | IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
        return new int[0];
    }
}
