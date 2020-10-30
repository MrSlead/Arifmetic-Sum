import db_info.DataBaseInfo;
import enginer.ArithmeticSum;

public class Main {

    public static void main(String[] args) {
        DataBaseInfo dataBaseInfo = new DataBaseInfo();

        dataBaseInfo.setDriver("org.postgresql.Driver");
        dataBaseInfo.setUrl("jdbc:postgresql://localhost:5432/numbers");
        dataBaseInfo.setName("user");
        dataBaseInfo.setPassword("root");
        dataBaseInfo.setN(155);

        ArithmeticSum arithmeticSum = new ArithmeticSum(dataBaseInfo);

        System.out.println(arithmeticSum.arithmeticSum());
    }
}
