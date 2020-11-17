package Database;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
import Database.DbConnection;
import org.postgresql.util.PGTimestamp;

public class DatabaseHelper {
    public static String getPushPaymentAcquirerNotification() throws Exception{
        //check month ODD or EVEN
        LocalDate today=LocalDate.now();
        int monthInt=today.getMonthValue();
        String query,log="";

        if(monthInt%2==0)
            query = "Select * from log_ln_even order by created desc limit 1 offset 2";
        else
            query = "Select * from log_ln_odd order by created desc limit 1 offset 2";

        PreparedStatement preparedStatement = null;

        try {
            Connection con = DbConnection.connect();

            preparedStatement = con.prepareStatement(query);
            ResultSet rs=preparedStatement.executeQuery();

            while(rs.next()) {
                log=rs.getString("message");
            }

            DbConnection.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return log;

    }

    public static void updateTxnStateFromTxnTable(String lqdTxnID) throws Exception{
        //update 'state' of txn to 00030 in LN txn_odd/ txn_even to make txn valid

        //check month ODD or EVEN
        LocalDate today=LocalDate.now();
        int monthInt=today.getMonthValue();
        String query;

        if(monthInt%2==0)
            query = "UPDATE transaction_even set state='00030' where lqdtransactionid=?";
        else
            query = "UPDATE transaction_odd set state='00030' where lqdtransactionid=?";

        PreparedStatement preparedStatement = null;

        try {
            Connection con = DbConnection.connect();

            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,lqdTxnID);
            preparedStatement.executeUpdate();

            DbConnection.closeConnection();
        }catch (SQLException e) {
            System.out.println("Error in updateTxnStateFromTxnTable method");
            e.printStackTrace();
        }
    }

    public static boolean checkRecordPresentInTxnTable(String lqdTxnID,String relatedID) throws Exception {
        boolean recordExist=false;
        //check month ODD or EVEN
        LocalDate today=LocalDate.now();
        int monthInt=today.getMonthValue();
        String query;

        if(monthInt%2==0)
            query = "Select * from transaction_even where lqdtransactionid=\'"+lqdTxnID+"\'";
        else
            query = "Select * from transaction_odd where lqdtransactionid=\'"+lqdTxnID+"\'";

        PreparedStatement preparedStatement = null;

        try {
            Connection con = DbConnection.connect();

            preparedStatement = con.prepareStatement(query);
            ResultSet rs=preparedStatement.executeQuery();

            while(rs.next()) {
                System.out.println("LqdTxnID from LN="+rs.getString("lqdtransactionid"));

                if(rs.getString("lqdtransactionid").equals(lqdTxnID) && rs.getString("relatedid").equals(relatedID)) {
                    recordExist=true;
                }
            }

            DbConnection.closeConnection();
        }catch (SQLException e) {
            System.out.println("Error in checkRecordPresentInTxnTable method");
            e.printStackTrace();
        }

        return recordExist;
    }

    public static ArrayList<String> getPayloadCode (String acquiringID)throws Exception{
        ArrayList<String> payloads = new ArrayList<>();
        String query = "select * from acquirer_processor_qr_config where acquirerprocessorid=\'"+ acquiringID+"\'";

        PreparedStatement preparedStatement = null;

        try {
            Connection con = DbConnection.connect();
            preparedStatement = con.prepareStatement(query);
            ResultSet rs=preparedStatement.executeQuery();

            while(rs.next()) {
                payloads.add(rs.getString("qrcode"));
            }

            DbConnection.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return payloads;
    }

    public static Timestamp getTxnTime (String lqdTxnID)throws Exception{
        LocalDate today=LocalDate.now();
        int monthInt=today.getMonthValue();
        String query;
        //Date txnDateTime = new;
        //Date date = new Date();
        Timestamp txnTime = new Timestamp(new Date().getTime());
        if(monthInt%2==0)
            query = "Select * from transaction_even where lqdtransactionid=?";
        else
            query = "Select * from transaction_odd where lqdtransactionid=?";

        PreparedStatement preparedStatement = null;

        try {
            Connection con = DbConnection.connect();
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,lqdTxnID);
            ResultSet rs=preparedStatement.executeQuery();

            while(rs.next()) {
                txnTime = rs.getTimestamp("transactiondatetimelqd");
            }

            DbConnection.closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return txnTime;
    }

    public static void updateTxnTime(Timestamp txnDateTime, String liquidTxnID) throws Exception{
        //check month ODD or EVEN
        LocalDate today=LocalDate.now();
        int monthInt=today.getMonthValue();
        String query;

        if(monthInt%2==0)
            query = "UPDATE transaction_even set transactiondatetimelqd =?,transactiondatetimeacq=?,transactiondatetimeiss=? where lqdtransactionid=?";
        else
            query = "UPDATE transaction_odd set transactiondatetimelqd =?,transactiondatetimeacq=?,transactiondatetimeiss=? where lqdtransactionid=?";

        PreparedStatement preparedStatement = null;

        try {
            Connection con = DbConnection.connect();

            preparedStatement = con.prepareStatement(query);
            preparedStatement.setTimestamp(1,txnDateTime);
            preparedStatement.setTimestamp(2,txnDateTime);
            preparedStatement.setTimestamp(3,txnDateTime);
            preparedStatement.setString(4,liquidTxnID);
            preparedStatement.executeUpdate();

            DbConnection.closeConnection();
        }catch (SQLException e) {
            System.out.println("Error in updateTxnDateTimeFromTxnTable method");
            e.printStackTrace();
        }
    }


}
