import db_info.DataBaseInfo;
import enginer.ArithmeticSum;

public class Main {

    public static void main(String[] args) {
        DataBaseInfo dataBaseInfo = new DataBaseInfo();

        dataBaseInfo.setDriver("org.postgresql.Driver");
        dataBaseInfo.setUrl("jdbc:postgresql://localhost:5432/numbers");
        dataBaseInfo.setName("postgres");
        dataBaseInfo.setPassword("admin");
        dataBaseInfo.setN(1001);

        ArithmeticSum arithmeticSum = new ArithmeticSum(dataBaseInfo);

        System.out.println(arithmeticSum.arithmeticSum());
    }
}
