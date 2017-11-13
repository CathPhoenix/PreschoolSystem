package preschoolSystem;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

public class MainScreen extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Frame myFrame;
	
	JButton addStudent, viewStudent, modifyStudent, addReports, deleteStudent, logOut;
	JPanel wrapMainScreenPanel;
	
	//setting up Card Layout
	CardLayout myCardLayout;
	JPanel swapPanelCards;
	String enteredUsername;
	
	public MainScreen(String enteredUsername){
		this.enteredUsername = enteredUsername;
		
		
		setTitle("Welcome");
		setSize(1400,800);
		myFrame=new Frame();
		Container c = getContentPane();		
		setIconImage(Design.getMonkeyImage().getImage());
		
		myCardLayout = new CardLayout();
		swapPanelCards = new JPanel(myCardLayout);
		
		JPanel topPanel = (buildTopPanel());
		JPanel buttonPanel = (buildButtonPanel());
		
		wrapMainScreenPanel = new JPanel(new BorderLayout());	
		wrapMainScreenPanel.add(topPanel, BorderLayout.NORTH);
		wrapMainScreenPanel.add(buttonPanel, BorderLayout.CENTER);
		
		swapPanelCards.add(wrapMainScreenPanel, "homeScreen");
		
		
		c.add(swapPanelCards);
		myCardLayout.show(swapPanelCards,"homeScreen");
	    setLocationRelativeTo(null);	//put to center of the screen
	    setResizable(false);
		setVisible(true);
	}
	
	public JPanel getMainScreenPanel(){
		return this.wrapMainScreenPanel;
	}

	private JPanel buildTopPanel() {
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel wrapTopPanel = new JPanel(new BorderLayout());
		wrapTopPanel.setBackground(Design.getLightGreen());
		
		JLabel welcomeLabel = new JLabel("Welcome to the Administration Page");
		welcomeLabel.setFont(Design.getLargeTitleFont());
		//empty border around the welcome (top, left, bottom, right)
		welcomeLabel.setBorder(BorderFactory.createEmptyBorder(50, 30, 10, 10));
		welcomeLabel.setForeground(Design.getDarkGreen());
		leftPanel.setBackground(Design.getLightGreen());
		
		JLabel loggedInLabel = new JLabel("Logged in as: "+enteredUsername.substring(0, 1).toUpperCase() + enteredUsername.substring(1));	//make first letter of Username Capital
		loggedInLabel.setFont(Design.getBoldFont());
		loggedInLabel.setBorder(BorderFactory.createEmptyBorder(50, 10, 20, 10));//(top, left, bottom, right)
		rightPanel.setBackground(Design.getLightGreen());
		
		leftPanel.add(welcomeLabel);
		rightPanel.add(loggedInLabel);
		wrapTopPanel.add(leftPanel, BorderLayout.WEST);
		wrapTopPanel.add(rightPanel, BorderLayout.EAST);
		
		return wrapTopPanel;
	}

	private JPanel buildButtonPanel() {		
		JPanel buttonPanel = new JPanel(new GridLayout(2,3,70,90)); //GridLayout(rows, columns, horizontal gap, vertical gap)
		Border emptyBorder = BorderFactory.createEmptyBorder(150, 70, 150, 70);
		Border topLineBorder =  BorderFactory.createMatteBorder(3, 0, 0, 0, Design.getDarkGreen());
		

		addStudent = new JButton("Add New Student");
		makeButton(addStudent);
	
		viewStudent = new JButton("View Student's Records");
		makeButton(viewStudent);

		modifyStudent = new JButton("Modify Student's Record");
		makeButton(modifyStudent);

		addReports = new JButton("Add Progress/Accident Report");
		makeButton(addReports);
		
		deleteStudent = new JButton("Delete Student Record");
		makeButton(deleteStudent);
		
		logOut = new JButton("Log Out");
		makeButton(logOut);
		
		buttonPanel.add(addStudent);
		buttonPanel.add(viewStudent);
		buttonPanel.add(modifyStudent);
		buttonPanel.add(addReports);
		buttonPanel.add(deleteStudent);
		buttonPanel.add(logOut);
		
		buttonPanel.setBackground(Design.getLightGreen());
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(topLineBorder, emptyBorder));
		
		return buttonPanel;
	}
	

	private void makeButton(JButton button) {
		
		Border greenLine =  BorderFactory.createMatteBorder(3, 3, 3, 3, Design.getDarkGreen());//(top, left, bottom, right)
		Border emptyBorder = BorderFactory.createEmptyBorder(35, 25, 35, 25);//(top, left, bottom, right)
		
		button.setFont(Design.getButtonFont());
		button.setForeground(Design.getDarkGreen());
		button.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		button.addActionListener(this);
		
	}

	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==logOut){

			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));//to close the GUI	
			UIManager.put("OptionPane.background", Design.getLightGreen());
			UIManager.put("Panel.background", Design.getLightGreen());
			JOptionPane.showMessageDialog(myFrame, "You have now Logged Out", "Good-Bye", JOptionPane.PLAIN_MESSAGE);
			System.exit(0);//this stops the program running in the background
		
		}else if(e.getSource()==addStudent){
			
			//create a new AddStudent1 page and pass it this frame
			new AddStudent1(this);
			
			//show the AddStudent1 page
			myCardLayout.show(swapPanelCards,"AddStudent1");
		
		}else if(e.getSource()==viewStudent){
			
			new ViewStudent1(this);
			myCardLayout.show(swapPanelCards,"ViewStudent1");
		}else if(e.getSource()==modifyStudent){
			new ModifyStudent1(this);
			myCardLayout.show(swapPanelCards,"ModifyStudent1");
		}else if(e.getSource()==deleteStudent){
			new DeleteStudent(this);
			myCardLayout.show(swapPanelCards, "DeleteStudent");
		}else if(e.getSource()==addReports) {
			new ReportsView(this);
			myCardLayout.show(swapPanelCards, "ReportsView");
		}
		
	}
	
/*	public static void clearAllText(){
		AddStudent1.txtFirstname.setText("");
		AddStudent1.txtFirstname.setText("");
		AddStudent1.txtSurname.setText(""); 
		AddStudent1.txtAdd1.setText("");
		AddStudent1.txtAdd2.setText("");
		AddStudent1.txtAdd3.setText("");
		AddStudent1.txtGuardian1Name.setText("");
		AddStudent1.txtGuardian1Phone.setText("");
		AddStudent1.txtGuardian2Name.setText("");
		AddStudent1.txtGuardian2Phone.setText("");
		AddStudent1.txtEmergency.setText("");
		AddStudent1.txtEmergencyPhone.setText("");
		AddStudent1.dateDOB.setCalendar(null);
		AddStudent1.dateCommencement.setCalendar(null);
		
		AddStudent2.docNameTextField.setText("");
		AddStudent2.docAdd1TextField.setText("");
		AddStudent2.docAdd2TextField.setText("");
		AddStudent2.docAdd3TextField.setText("");
		AddStudent2.docPhoneField.setText("");
		
		AddStudent2.allergiesTextArea.setText("");
		AddStudent2.medConditText.setText("");
	}*/
	
	
	public static void main(String[] args) { //ONLY NEED THIS FOR TESTING. The login page will call this GUI
		new MainScreen("admin");

	}

}
