package dto;

import db_info.DataBaseInfo;
import entityXML.Number;
import entityXML.NumbersXMLFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NumberDto {
    private Logger logger = LoggerFactory.getLogger(NumberDto.class);
    private Connection connection = null;
    private DataBaseInfo dataBaseInfo;

    public NumberDto(DataBaseInfo dataBaseInfo) {
        this.dataBaseInfo = dataBaseInfo;
        try {
            connection = DriverManager.getConnection(dataBaseInfo.getUrl(), dataBaseInfo.getName(), dataBaseInfo.getPassword());

            logger.info("Created connection");
            logger.info("URL for Connection: " + dataBaseInfo.getUrl());
            logger.info("Name for Connection: " + dataBaseInfo.getName());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean start() {
        try {
            if(dataBaseInfo.getN() >= 1) {
                Class.forName(dataBaseInfo.getDriver());
                logger.info("Used driver for JDBC: " + dataBaseInfo.getDriver());

                if (getCountFieldsInDB() >= 1) {
                    if (deleteFields()) {
                        logger.info("Deleted all fields in table");
                    } else {
                        logger.warn("Couldn't delete all fields in the table");
                    }
                }

                insertFieldsInDB(dataBaseInfo);
                toNumbersXMLFormat();

                return true;
            }
            else {
                logger.warn("Set the positive number of elements using the method DateBaseInfo: setN(value)");
                return false;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private int getCountFieldsInDB() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            logger.debug("Created a Statement for Connection to FIND out the number of fields in the table");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM TEST WHERE id=(SELECT max(id) FROM TEST);");

            int N = 0;
            if(resultSet.next()) {
                N = resultSet.getInt(2);
            }

            logger.debug("Query completed. The number of fields in the table: " + N);

            return N;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }

    private boolean deleteFields() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            logger.debug("Created a Statement for Connection to DELETE the fields in the table");

            statement.executeUpdate("DELETE FROM TEST WHERE id > 0");
            logger.debug("Query completed.");

            if(getCountFieldsInDB() == 0) return true;
            else return false;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private int insertFieldsInDB(DataBaseInfo dataBaseInfo) {
        Statement statement = null;
        try {
            int N = dataBaseInfo.getN();

            if(N >= 1) {
                statement = connection.createStatement();

                logger.debug(String.format("Created a Statement for Connection to insert the %s fields in the table", N));

                for (int i = 1; i <= N; i++) {
                    String insertSQL = String.format("INSERT INTO TEST (\"field\") VALUES ('%d')", i);

                    statement.addBatch(insertSQL);
                }

                statement.executeBatch();
                logger.debug(String.format("Inserted %d fields", N));

                return N;
            }
            else return 0;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    private void toNumbersXMLFormat() {
        Statement statement = null;

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM TEST WHERE id=(SELECT max(id) FROM TEST);");

            int N = 0;
            if(resultSet.next()) {
                logger.debug("Founded " + resultSet.getInt(2) + " fields.");
                N = resultSet.getInt(2);
            }

            List<Number> numbers = Stream.iterate(1, i -> i + 1)
                    .limit(N).map(i -> new Number(i))
                    .collect(Collectors.toList());

            if(numbers.size() >= 1) {
                NumbersXMLFormat numbersXmlFormat = new NumbersXMLFormat(numbers);
                //писать результат сериализации будем в Writer(StringWriter)
                StringWriter writer = new StringWriter();

                //создание объекта Marshaller, который выполняет сериализацию
                JAXBContext context = JAXBContext.newInstance(NumbersXMLFormat.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                // сама сериализация
                marshaller.marshal(numbersXmlFormat, writer);

                String xmlPath1 = "/res/1.xml";
                /*if(!Files.exists(Paths.get(getClass().getResourceAsStream(xmlPath1).toString()))) {
                    Path fileXML = Files.createFile(Paths.get(getClass().getResourceAsStream(xmlPath1).toString()));
                }*/
                
                Files.write(Paths.get(getClass().getResource(xmlPath1).toURI()), writer.toString().getBytes());
                logger.debug("Created xml file with fields of number");


                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(getClass().getResourceAsStream("/res/file.xsl"));
                Transformer transformer = factory.newTransformer(xslt);
                Source xml = new StreamSource(String.valueOf(Paths.get(Paths.get(getClass().getResource(xmlPath1).toURI()).toString())));

                String xmlPath2 = "/res/2.xml";
                transformer.transform(xml, new StreamResult(String.valueOf(Paths.get(Paths.get(getClass().getResource(xmlPath2).toURI()).toString()))));
                logger.debug("Created xml file N2 with fields of number");
            }

        } catch (TransformerException | SQLException | JAXBException | IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
