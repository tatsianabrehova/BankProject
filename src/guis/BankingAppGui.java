package guis;

import db_objs.User;

import javax.swing.*;
import java.awt.*;

public class BankingAppGui extends BaseFrame {
private JTextField CurrentBalanceField;
public JTextField getCurrentBalanceField(){
    return CurrentBalanceField;
}

    public BankingAppGui(User user){
        super("Banking App",user);
    }
    @Override
    protected void addGuiComponents() {
String welcomeMessage="<html>"+"<body style='text-align:center'>"+"<b>Hello"+user.getUsername()+"</b><br>"+"What would you like to do today?</body></html>";
        JLabel welcomeMessageLabel= new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0,20,getWidth()-10,40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN,16));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessageLabel);

        JLabel currentBalanceLabel= new JLabel("Current Balance");
        currentBalanceLabel.setBounds(0,80,getWidth()-10,30);
        currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);

        CurrentBalanceField = new JTextField("$"+user.getCurrentBalance());
        CurrentBalanceField.setBounds(15,120,getWidth()-50,40);
        CurrentBalanceField.setFont(new Font("Dialog", Font.BOLD, 28));
        CurrentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        CurrentBalanceField.setEditable(false);
        add(CurrentBalanceField);

        JButton depositButton=new JButton("Deposit");
        depositButton.setBounds(15,180,getWidth()-50,50);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 22));
        add(depositButton);

        JButton withdrawButton=new JButton("Withdraw");//снять
        withdrawButton.setBounds(15,250,getWidth()-50,50);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 22));
        add(withdrawButton);
        //транзакция
        JButton pastTransactionButton=new JButton("Past Transaction");
        pastTransactionButton.setBounds(15,320,getWidth()-50,50);
        pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 22));
        add(pastTransactionButton);

        JButton transferButton=new JButton("Transfer");
        transferButton.setBounds(15,390,getWidth()-50,50);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 22));
        add(transferButton);

        JButton logoutButton=new JButton("Logout");
        logoutButton.setBounds(15,500,getWidth()-50,50);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 22));
        add(logoutButton);
}
}
