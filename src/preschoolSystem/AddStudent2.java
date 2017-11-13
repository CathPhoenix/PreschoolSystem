package preschoolSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.Border;



/**
 * 
 * 21/3/17 - need to add SQL statements to insert student details into database (doctors details done)
 * @author catharine
 *
 */



public class AddStudent2 extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton backButton, cancelButton, saveButton;
	static JTextField docNameTextField, docAdd1TextField, docAdd2TextField, docAdd3TextField, docPhoneField;
	static JTextArea allergiesTextArea, medConditText;
	
	Sqlengine dbEngine; //database engine
	
	MainScreen frame;
	
	
public AddStudent2(MainScreen frame){
		
		this.frame=frame;
		dbEngine = new Sqlengine("root", "root"); //database engine
		
		
		
		JPanel topPanel = (Design.buildTopPanel("Student Medical Records"));
		JPanel enterMedicalInfoPanel = (buildMedicalInfoPanel());
		JPanel bottomPanel = (buildbottomPanel());
		
		JPanel wrapPanel = new JPanel(new BorderLayout());
		wrapPanel.add(topPanel, BorderLayout.NORTH);
		wrapPanel.add(enterMedicalInfoPanel, BorderLayout.CENTER);
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);

		
		frame.swapPanelCards.add(wrapPanel, "AddStudent2");
		
	}
	
	private JPanel buildbottomPanel() {
		JPanel bottomPanel = new JPanel(new GridLayout(1,3,50,0));
		Border greenLine =  BorderFactory.createMatteBorder(2, 2, 2, 2, Design.getDarkGreen());//(top, left, bottom, right)
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);//(top, left, bottom, right)
		
		backButton = new JButton("Back");
		backButton.setFont(Design.getButtonFont());
		backButton.setForeground(Design.getDarkGreen());
		backButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		backButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				frame.myCardLayout.show(frame.swapPanelCards, "AddStudent1");
				
			}
		});
		
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(Design.getButtonFont());
		cancelButton.setForeground(Design.getDarkGreen());
		cancelButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		cancelButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.background", Design.getLightGreen());
				UIManager.put("Panel.background", Design.getLightGreen());
				int n = JOptionPane.showConfirmDialog(frame, "Are you sure? Press Cancel to return to adding details, or OK to go back to Home Screen ", "Title", JOptionPane.OK_CANCEL_OPTION);
				if(n == JOptionPane.YES_OPTION){	//if the user clicks OK, go back to Home screen, else do nothing
					frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
				}
				

				
			}
		});
		
		saveButton = new JButton("Save");
		saveButton.setFont(Design.getButtonFont());
		saveButton.setForeground(Design.getDarkGreen());
		saveButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		saveButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				//MainScreen.clearAllText();
				saveToDataBase();
				frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
				
				
			}

		});
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		bottomPanel.setBackground(Design.getLightGreen());
		
		bottomPanel.add(backButton);
		bottomPanel.add(cancelButton);
		bottomPanel.add(saveButton);
		
		
		
		
		
		return bottomPanel;
	}

	private JPanel buildMedicalInfoPanel() {
		JPanel leftPanel = new JPanel(new GridLayout(5, 1));
		JPanel rightPanel = new JPanel(new GridLayout(2, 1));
		JPanel wrapPanel = new JPanel(new BorderLayout());
		
		JPanel allergiesPanel = new JPanel(new BorderLayout());
		JPanel medConditPanel = new JPanel(new BorderLayout());
		
		
		docNameTextField = new JTextField(20);
		JPanel docNamePanel =buildPanel("Doctor's Name: ", docNameTextField);

		
		
		docAdd1TextField = new JTextField(20);
		
		JPanel docAdd1Panel =buildPanel("Address 1: ", docAdd1TextField);

		
		docAdd2TextField = new JTextField(20);
		JPanel docAdd2Panel =buildPanel("Address 2: ", docAdd2TextField);
	
		
		docAdd3TextField = new JTextField(20);
		JPanel docAdd3Panel =buildPanel("Address 3: ", docAdd3TextField);
		

		docPhoneField = new JTextField(20);
		JPanel docPhonePanel =buildPanel("Phone Number: ", docPhoneField);
		
		
		

		JLabel allergiesLabel = new JLabel("Any Allergies: ");
		allergiesTextArea = new JTextArea(10,50);
		allergiesTextArea.setLineWrap(true);
		allergiesPanel.add(allergiesLabel,BorderLayout.CENTER);
		allergiesPanel.add(allergiesTextArea, BorderLayout.SOUTH);
		allergiesPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 50, 200));//empty border (top, left, bottom, right)
		allergiesPanel.setBackground(Design.getLightGreen());
		allergiesLabel.setFont(Design.getBoldFont());
		allergiesLabel.setForeground(Design.getDarkGreen());
		
		
		JLabel medConditLabel = new JLabel("Any Other Medical Conditions: ");
		medConditText = new JTextArea(10,50);
		medConditText.setLineWrap(true);
		medConditPanel.add(medConditLabel, BorderLayout.CENTER);
		medConditPanel.add(medConditText, BorderLayout.SOUTH);
		medConditPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 50, 200));
		medConditPanel.setBackground(Design.getLightGreen());
		medConditLabel.setFont(Design.getBoldFont());
		medConditLabel.setForeground(Design.getDarkGreen());
		
		
		leftPanel.add(docNamePanel);
		leftPanel.add(docAdd1Panel);
		leftPanel.add(docAdd2Panel);
		leftPanel.add(docAdd3Panel);
		leftPanel.add(docPhonePanel);
		leftPanel.setBackground(Design.getLightGreen());
		leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
		

		
		rightPanel.add(allergiesPanel);
		rightPanel.add(medConditPanel);
		
		wrapPanel.add(leftPanel, BorderLayout.WEST);
		wrapPanel.add(rightPanel, BorderLayout.CENTER);
		

		
		
		return wrapPanel;
	}

	private JPanel buildPanel(String labelString, JTextField thisTextField) {
		JPanel thisPanel = new JPanel();
		JLabel thisLabel = new JLabel(labelString);
		
		thisPanel.add(thisLabel);

		thisPanel.add(thisTextField);
		
		thisLabel.setFont(Design.getBoldFont());
		thisLabel.setForeground(Design.getDarkGreen());
		
		thisPanel.setBackground(Design.getLightGreen());
		

		return thisPanel;
	}

	/*private JPanel buildTopPanel() {

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Design.getLightGreen());
		
		JLabel titleLabel = new JLabel("Student Medical Record");
		titleLabel.setFont(Design.getLargeTitleFont());
		
		titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 30, 10, 10)); //empty border (top, left, bottom, right)
		titleLabel.setForeground(Design.getDarkGreen());
		
		topPanel.add(titleLabel);

		
		topPanel.setBorder(BorderFactory.createMatteBorder(0,0,3,0,Design.getDarkGreen()));
		
		
		
		return topPanel;
	}*/
	/**
	 * SaveToDataBase - all details from the addStudent classes is now passed into
	 * the database and saved. 
	 */
	private void saveToDataBase() {
		dbEngine.connect();
		ResultSet docIDResult = null;
		// create a Statement from the connection
		Connection conn = dbEngine.getConn();
		Statement statement = null;
	
		try {
			statement = conn.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//need to check if doctor is already in database
		try {
			docIDResult = dbEngine.executeQuery("SELECT docID FROM doctor WHERE docName LIKE '"+docNameTextField.getText()+"' AND docPhone LIKE '"+docPhoneField.getText()+"'");

			//docIDResult = dbEngine.executeQuery("SELECT docID FROM doctor WHERE docName LIKE 'Cat' AND docPhone LIKE '123456'");
			//docIDResult = dbEngine.executeQuery("SELECT `docID` FROM `doctor` WHERE `docName` LIKE 'Cat' AND `docPhone` LIKE '123456'");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int checkForDocID = 0;//initialise
		
		try {
			if(docIDResult.next()){	//if there is a doctor already in the database.. get the docID

				checkForDocID = docIDResult.getInt(1);	//get the id of the doctor inserted to put docID into the student table
	
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		if(checkForDocID==0){ //if no doctor found in the database, add the doctors details to the doctor table
			
			
			//THIS ONE WORKS!! no back ticks `` or single quotes around table name and columns... single quotes '' around values other than NULL 
			String sqlStatement= "INSERT INTO doctor (docID, docName, docAdd1, docAdd2, docAdd3, docPhone) VALUES (NULL, '"+docNameTextField.getText()+"', '"+docAdd1TextField.getText()+"', '"+docAdd2TextField.getText()+"', '"+docAdd3TextField.getText()+"', '"+ docPhoneField.getText()+"')" ;

	
			try {
				// insert the data
				statement.executeUpdate(sqlStatement);
				JOptionPane.showMessageDialog(frame, "New Student Details have been added to the database", "Details Saved", JOptionPane.INFORMATION_MESSAGE);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		//need to get docID back from above to put into student table
		try {
			docIDResult = dbEngine.executeQuery("SELECT docID FROM doctor WHERE docName LIKE '"+docNameTextField.getText()+"' AND docPhone LIKE '"+docPhoneField.getText()+"'");

			docIDResult.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int docIDint = 0; //initialise - integer to use in INSERT INTO student
		
		try {
			//System.out.println(docIDResult.getInt(1));
			docIDint = docIDResult.getInt(1);	//get the id of the doctor inserted to put docID into the student table
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		/**
		 * FOR TESTING HAD TO MODIFY DATABASE CODE	
		 * doctor_docID --> is now the correct column name
		 * commencement --> changed from commencementDate
		 * cessation --> changed from cessationDate
		 */
		String sqlStatement2="INSERT INTO student (StudentID, doctor_docID, firstName, surname, DOB, add1, add2, add3, guardian1, guardian1Phone, guardian2, guardian2Phone, emergencyContactName, emergencyContactPhone, commencement, cessation, medicalCondition, allergies) VALUES (NULL, "+docIDint+", '"+preschoolSystem.AddStudent1.strFirstname+"', '"+preschoolSystem.AddStudent1.strSurname+"', '"+preschoolSystem.AddStudent1.strDOB+"', '"+preschoolSystem.AddStudent1.strAdd1+"', '"+preschoolSystem.AddStudent1.strAdd2+"', '"+preschoolSystem.AddStudent1.strAdd3+"', '"+preschoolSystem.AddStudent1.strGuardian1Name+"', '"+preschoolSystem.AddStudent1.strGuardian1Phone+"', '"+preschoolSystem.AddStudent1.strGuardian2Name+"', '"+preschoolSystem.AddStudent1.strGuardian2Phone+"', '"+preschoolSystem.AddStudent1.strEmergency+"', '"+preschoolSystem.AddStudent1.strEmergencyPhone+"', '"+preschoolSystem.AddStudent1.strCommencementDate+"', NULL, '"+medConditText.getText()+"', '"+allergiesTextArea.getText()+"')";
		
																																																																	//INSERT INTO `student` (`studentID`, `doctorID`, `firstName`, `surname`, 																						`DOB`, `add1`, `add2`, `add3`, 																												`guardian1`, `guardian1Phone`, 																				`guardian2`, `guardian2Phone`, 																				`emergencyContactName`, `emergencyContactPhone`, `commencementDate`, `cessationDate`, `medicalCondition`, `allergies`) VALUES (NULL, '', '', '', '', NULL, NULL, NULL, '', '', NULL, NULL, '', '', NULL, NULL, NULL, NULL)
		
		// insert the data

		/**
		 * The below will be to Update the student table... yet to do!!
		 */
		try {
			statement.executeUpdate(sqlStatement2);
		} catch (SQLException e) {
			System.out.println("second sql statement");
			e.printStackTrace();
		}
		
		//dbEngine.executeQuery("INSERT INTO 'student' ('doctorID', 'firstName', 'surname', 'DOB', 'add1', 'add2', 'add3', 'guardian1', 'guardian1Phone', 'guardian2', 'guardian2Phone', 'emergencyContactName', 'emergencyContactPhone','medicalCondition', 'allergies')  VALUES ('"
			//										+docIDint+"',"+firstName+"', '"+surname+"', '"+DOB+"', '"+add1+"', '"+add2+"', '"add3+"', '"+guardian1+"', '"+guardian1Phone+"', '"+guardian2+"', '"+guardian2Phone+"', '"+emergencyContactName+"', '"+emergencyContactPhone+"', '"+medicalCondition+"', '"+allergies+"'");
		//INSERT INTO 'student' ('studentID', 'doctorID', 'firstName', 'surname', 'DOB', 'add1', 'add2', 'add3', 'guardian1', 'guardian1Phone', 'guardian2', 'guardian2Phone', 'emergencyContactName', 'emergencyContactPhone', 'commencementDate', 'cessationDate', 'medicalCondition', 'allergies')
		
		
		dbEngine.closeConnection();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
