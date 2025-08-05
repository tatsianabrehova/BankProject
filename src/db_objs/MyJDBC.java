package db_objs;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class MyJDBC {
    private static final String DB_URL="jdbc:mysql://127.0.0.1:3306/bankapp";
    public static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="tanyabrehova2004";

    public static User validateLogin(String username,String password){
        try{
            Connection connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement preparedStatement=connection.prepareStatement("SElECT*FROM users WHERE `username` = ? AND `password` =?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()){
                int userId=resultSet.getInt("id");
                BigDecimal currentBalance=resultSet.getBigDecimal("current_balance");
                return new User(userId,username,password,currentBalance);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
public static boolean register(String username,String password){
        try{
            Connection connection= DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO users(username,password,current_balance)"+"VALUES(?,?,?)");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setBigDecimal(3,BigDecimal.ZERO);
           preparedStatement.executeUpdate();
            //insertTransaction.executeUpdate();
return true;


        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
}
private static boolean checkUser(String username) {
    try {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT*FROM users WHERE username=?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
return true;}

    public static boolean addTransactionToDb(Transaction transaction){
        try{
            Connection connection =DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement insertTransaction=connection.prepareStatement(
                    "INSERT transactions(user_id,transaction_type,transaction_amount,transaction_date)"+
                            "VALUES(?,?,?,NOW())"
            );

            insertTransaction.setInt(1,transaction.getUserId());
            insertTransaction.setString(2,transaction.getTransactionType());
            insertTransaction.setBigDecimal(3,transaction.getTransactionAmount());
            insertTransaction.executeUpdate();
            return true;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public static boolean updateCurrentBalance(User user){
        try{
         Connection connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

         PreparedStatement updateBalance= connection.prepareStatement(
                 "UPDATE users SET current_balance = ? WHERE id = ?"
         );

         updateBalance.setBigDecimal(1,user.getCurrentBalance());
         updateBalance.setInt(2,user.getId());

         updateBalance.executeUpdate();
         return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public static boolean transfer(User user,String transferredUsername, float transferAmount){
        try{
            Connection connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

            PreparedStatement queryUser=connection.prepareStatement("SELECT*FROM users WHERE username = ?");

            queryUser.setString(1,transferredUsername);

            ResultSet resultSet=queryUser.executeQuery();

            while(resultSet.next()){
                User transferredUser=new User(
                        resultSet.getInt("id"),
                        transferredUsername,
                        resultSet.getString("password"),
                        resultSet.getBigDecimal("current_balance")
                );
                Transaction transferTransaction=new Transaction(
                        user.getId(),"Transfer",new BigDecimal(-transferAmount),null);
                Transaction receivedTransaction= new Transaction(
                        transferredUser.getId(),
                        "Transfer",
                        new BigDecimal(transferAmount),
                        null);

                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(transferredUser);

                user.setCurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(user);

                addTransactionToDb(transferTransaction);
                addTransactionToDb(receivedTransaction);

                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    // get all transactions (used for past transaction)
    public static ArrayList<Transaction> getPastTransaction(User user){
    ArrayList<Transaction> pastTransactions = new ArrayList<>();
    try{
    Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    PreparedStatement selectAllTransaction = connection.prepareStatement(
            "SELECT * FROM transactions WHERE user_id = ?"
            );
    selectAllTransaction.setInt (1, user.getId());
    ResultSet resultSet = selectAllTransaction.executeQuery();
    // iterate anyway
    while(resultSet.next()) {
        Transaction transaction = new Transaction(
                user.getId(),
                resultSet.getString("transaction_type"),
                resultSet.getBigDecimal("transaction_amount"),
                resultSet.getDate("transaction_date"));
        pastTransactions.add(transaction);
    }
}catch (SQLException e){
        e.printStackTrace();
}return pastTransactions;
    }}
