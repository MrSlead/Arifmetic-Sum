public class Main {
    private static DataBaseInfo dataBaseInfo;
    private static ArithmeticSum arithmeticSum;

    public static void main(String[] args) {
        dataBaseInfo = new DataBaseInfo();

        dataBaseInfo.setDriver("org.postgresql.Driver");
        dataBaseInfo.setUrl("jdbc:postgresql://localhost:5432/numbers");
        dataBaseInfo.setName("user");
        dataBaseInfo.setPassword("root");

        arithmeticSum = new ArithmeticSum(dataBaseInfo);

        arithmeticSum.deleteFields();
    }
}
