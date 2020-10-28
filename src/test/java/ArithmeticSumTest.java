import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArithmeticSumTest {
    private Logger logger = LoggerFactory.getLogger(ArithmeticSumTest.class);

    private static DataBaseInfo dataBaseInfo;
    private static ArithmeticSum arithmeticSum;

    @BeforeClass
    public static void setDataBaseInfo() {
        dataBaseInfo = new DataBaseInfo();

        dataBaseInfo.setDriver("org.postgresql.Driver");
        dataBaseInfo.setUrl("jdbc:postgresql://localhost:5432/numbers");
        dataBaseInfo.setName("user");
        dataBaseInfo.setPassword("root");
        dataBaseInfo.setN(5);

        arithmeticSum = new ArithmeticSum(dataBaseInfo);
    }

    @Test
    public void getCountFieldsInDB() {
        Assert.assertEquals(0, arithmeticSum.getCountFieldsInDB());
    }

    @Test
    public void deleteFields() {
        Assert.assertEquals(true, arithmeticSum.deleteFields());
    }

    @Test
    public void insertFields() {
        Assert.assertEquals(5, arithmeticSum.insertFieldsInDB(dataBaseInfo));
    }
}
