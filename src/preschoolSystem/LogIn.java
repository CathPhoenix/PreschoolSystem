package preschoolSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.*;

import preschoolSystem.Sqlengine;

/**
 *  Login - Providing a login frame for the administrator of the system
 *  @author catharine
 *
 */


public class LogIn extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton loginButton;
	JTextField userNameTextField;
	JPasswordField passwordField;
	JFrame myFrame;

	Sqlengine dbEngine = new Sqlengine("root", "root"); //database engine
	
	public LogIn(){
		setTitle("Log In");
		setSize(400, 300);
		Container c = getContentPane();
			
		myFrame = new JFrame();
		setIconImage(Design.getMonkeyImage().getImage());
		
		//panels
		JPanel topPanel = buildTopPanel();
		JPanel centerPanel = buildCenterPanel();
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(loginButton);		
		
		buttonPanel.setBackground(Design.getLightGreen());

		c.add(topPanel, BorderLayout.NORTH);
		c.add(centerPanel, BorderLayout.CENTER);
		c.add(buttonPanel, BorderLayout.SOUTH);		
		
		//put to center of the screen
	    setLocationRelativeTo(null);
	    //user cannot resize
		setResizable(false);
		setVisible(true);
	}

	private JPanel buildCenterPanel() {
	
		JPanel centerPanel = new JPanel();
		JPanel userPanel = new JPanel();
		JPanel passPanel = new JPanel();
		
		JLabel userNameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password:  ");
		
		userNameTextField = new JTextField(20);
		passwordField = new JPasswordField(20);
		
		loginButton = new JButton("Log In");
		loginButton.addActionListener(this);
		Border greenLine =  BorderFactory.createMatteBorder(2, 2, 2, 2, Design.getDarkGreen());
		Border emptyBorder = BorderFactory.createEmptyBorder(5, 20, 5, 20);
		loginButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
	
		userPanel.add(userNameLabel);
		userPanel.add(userNameTextField);
		passPanel.add(passwordLabel);
		passPanel.add(passwordField);
			
		centerPanel.add(userPanel, BorderLayout.NORTH);
		
		centerPanel.add(passPanel, BorderLayout.CENTER);
		
		centerPanel.setBackground(Design.getLightGreen());
		userPanel.setBackground(Design.getLightGreen());
		passPanel.setBackground(Design.getLightGreen());
		
		return centerPanel;
	}
	/**
	 * Top Panel - this sets a default look to each frame
	 * using a utility package
	 * @return - top panel to the frame
	 */
	private JPanel buildTopPanel() {
		JPanel topPanel = new JPanel(new BorderLayout());
		
		JLabel titleLabel = new JLabel("Montessori Monkeys");
		titleLabel.setFont(Design.getSmallTitleFont());
		titleLabel.setForeground(Design.getDarkGreen());
		
		//empty border around the title (top, left, bottom, right)
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				
		JLabel adminLogin = new JLabel("Admin Login");
		adminLogin.setFont(Design.getBoldFont());
		adminLogin.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
		
		topPanel.add(titleLabel, BorderLayout.NORTH);
		topPanel.add(adminLogin, BorderLayout.CENTER);	
		topPanel.setBackground(Design.getLightGreen());
		
		return topPanel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LogIn();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==loginButton){
			String enteredUsername = null;
			char[] enteredPassChar = null;
			String thisUsersPassword = null;
			String enteredPassStr = null;
			
			//if the userNameText field wasn't blank..
			if(!userNameTextField.getText().equals("")){
				enteredUsername = userNameTextField.getText();
				enteredPassChar = passwordField.getPassword();
				
				enteredPassStr = new String(enteredPassChar);
					
				
				thisUsersPassword = getPasswordByUsername(enteredUsername);
				
				//System.out.println("This users pass "+thisUsersPassword);	//Testing
				//System.out.println("entered pass "+enteredPassStr);	//Testing
				
				//if we didn't get back a blank password for this user(ie no user with that name) AND if the passwords match...
				if(!thisUsersPassword.equals("") && thisUsersPassword.equals(enteredPassStr)){	
					
					new preschoolSystem.MainScreen(enteredUsername); //launch the Main Screen
					
					this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));//to close the login GUI
				
				}else{// If password and username don't match, or if we get a blank password back from database (ie no user with the entered name)
					
					//Make the background of the JOptionPane light green
					UIManager.put("OptionPane.background", Design.getLightGreen());
					UIManager.put("Panel.background", Design.getLightGreen());
			
					JOptionPane.showMessageDialog(myFrame, "User-Name or Password incorrect. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);				
				}
			}else{	//if the username textfield was left blank
				
				//Make error message background green
				UIManager.put("OptionPane.background", Design.getLightGreen());
				UIManager.put("Panel.background", Design.getLightGreen());
		
				JOptionPane.showMessageDialog(myFrame, "User-Name or Password incorrect. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);						
			}	
		}
	}

	public String getPasswordByUsername(String enteredUsername) {
		//connect to database
		dbEngine.connect();
		ResultSet user = null;
		
		try {
			//try SELECT statement
			user = dbEngine.executeQuery("SELECT password FROM users WHERE Username = '"+enteredUsername+"' COLLATE  utf8_bin");

		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		try {
			if(user.next()){	//if the user was found in the database, return the password

				try {
					return user.getString(1);	//getString(1) is the column with the password.. only one column selected
				} catch (SQLException e) {
					System.out.println("SQL Exception when trying to return the users password");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception when trying to see if user was found in database (user.next())");
			e.printStackTrace();
		}
		
		dbEngine.closeConnection();	//close database connection
		return "";		//if no user was found, return an empty string
	}
}
