package guis;

import db_objs.User;

import javax.swing.*;
import java.awt.*;

public class BankingAppGui extends BaseFrame {


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
        welcomeMessageLabel.setBounds(0,20,getWidth()-10,40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 16));


    }
}
