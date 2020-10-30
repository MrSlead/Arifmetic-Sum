import db_info.DataBaseInfo;
import dto.NumberDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArithmeticSumTest {
   /* private Logger logger = LoggerFactory.getLogger(ArithmeticSumTest.class);

    private static DataBaseInfo dataBaseInfo;
    private static NumberDto numberDto;

    @BeforeClass
    public static void setDataBaseInfo() {
        dataBaseInfo = new DataBaseInfo();

        dataBaseInfo.setDriver("org.postgresql.Driver");
        dataBaseInfo.setUrl("jdbc:postgresql://localhost:5432/numbers");
        dataBaseInfo.setName("user");
        dataBaseInfo.setPassword("root");
        dataBaseInfo.setN(8);

        numberDto = new NumberDto(dataBaseInfo);
    }

    @Test
    public void deleteFields() {
        Assert.assertEquals(true, numberDto.deleteFields());
    }

    @Test
    public void getCountFieldsInDB() {
        Assert.assertEquals(0, numberDto.getCountFieldsInDB());
    }

    @Test
    public void insertFields() {
        Assert.assertEquals(5, numberDto.insertFieldsInDB(dataBaseInfo));
    }*/
}
