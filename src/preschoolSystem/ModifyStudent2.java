package preschoolSystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("unused")
public class ModifyStudent2 extends JFrame{

	/**
	 * 
	 * 
	 * Need to look at update sql statement.. the doc id??? 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**************************** Change TextField's for dates to JDateChooser ********************************/
	//static JDateChooser dateDOB ;
	//static JDateChooser dateCommencement;
	static JDateChooser dateCessation;
	
	//String strDOB, strCommencement;
	String strCessation;
	
	MainScreen frame;
	String studentID;
	String docID;
	
	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtSurname;
	private JTextField txtDOB;
	private JTextField txtStudentAdd1;
	private JTextField txtStudentAdd2;
	private JTextField txtStudentAdd3;
	private JTextField txtNumG2;
	private JTextField txtEmergency;
	private JTextField txtNumEmergency;
	private JTextField txtStart;
	private JTextField txtEnd;
	private JTextField txtDoctor;
	private JTextField txtAdd1;
	private JTextField txtDocAdd2;
	private JTextField txtDocAdd3;
	private JTextField txtNumDoc;
	private JTextField txtGuardian1;
	private JTextField txtNumG1;
	private JTextField txtGuardian2;
	private JTextArea  textArea;
	private JTextArea  textArea_1;
	


	public ModifyStudent2(MainScreen frame, String studentID) {
		
		this.frame = frame;
		this.studentID = studentID;
		
		
		JPanel topPanel = (Design.buildTopPanel("Modify "+Design.getStudentName(studentID)+"'s Record"));
		JPanel centerPanel = (buildCenterPanel());
		JPanel bottomPanel = (buildbottomPanel());
		
		JPanel wrapPanel = new JPanel(new BorderLayout());
		wrapPanel.add(topPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.swapPanelCards.add(wrapPanel, "ModifyStudent2");
	}

	private JPanel buildbottomPanel() {
		JPanel bottomPanel = new JPanel(new GridLayout(1,3,50,0));
		Border greenLine =  BorderFactory.createMatteBorder(2, 2, 2, 2, Design.getDarkGreen());//(top, left, bottom, right)
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);//(top, left, bottom, right)
		
		JButton btnBack = new JButton("Back");
		
		btnBack.setFont(Design.getButtonFont());
		btnBack.setForeground(Design.getDarkGreen());
		btnBack.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if back pushed, go to homescreen
				new ModifyStudent1(frame);
				frame.myCardLayout.show(frame.swapPanelCards, "ModifyStudent1");
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(Design.getButtonFont());
		btnCancel.setForeground(Design.getDarkGreen());
		btnCancel.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		
		btnCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//if cancel is pushed, ask if they are sure
				int choice = JOptionPane.showConfirmDialog(frame, "Are you sure? Press Cancel to return to adding details, or OK to go back to Home Screen ", "Title", JOptionPane.OK_CANCEL_OPTION);
				
				if(choice == JOptionPane.YES_OPTION){	//if the user clicks OK, go back to Home screen, else do nothing
					frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
				}	
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(Design.getButtonFont());
		btnSave.setForeground(Design.getDarkGreen());
		btnSave.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		btnSave.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
							
				//Date dob = dateDOB.getDate();
				//Date commence = dateCommencement.getDate();
				Date endDate = dateCessation.getDate();
				
				//make the format for the DOB string match SQL requirements
				//strDOB = String.format("%1$tY-%1$tm-%1$td",dob);
				//strCommencement = String.format("%1$tY-%1$tm-%1$td",commence);
				strCessation = String.format("'%1$tY-%1$tm-%1$td'", endDate);
				System.out.println(strCessation);
			
				//DOB and commencement date must be entered (otherwise it will cause a SQL error on next page)
				/*if(strDOB.equals("NULL-NULL-NULL") ){
					strDOB= "NULL";
				}else if(strCommencement.equals("NULL-NULL-NULL") ){//if DOB and Commencement date entered then save all details entered on this page into strings for SQL statement on next/AddStudent2 page
					strCommencement="NULL";
				}else*/
				if(strCessation.equals("'null-null-null'") ){
					strCessation="NULL"; //strCessation =" Null "		or = " 'datejsdhf' "
					System.out.println(strCessation);
				}
				
				/**********************************************************************************/
				
				//will create a method saveToDataBase to handle saving modifications to database
				saveToDataBase();
				frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
				JOptionPane.showMessageDialog(frame, Design.getStudentName(studentID)+"'s Record have been modified", "Details Saved", JOptionPane.INFORMATION_MESSAGE);				
			}
		
		});
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		bottomPanel.setBackground(Design.getLightGreen());

		bottomPanel.add(btnBack);
		bottomPanel.add(btnCancel);
		bottomPanel.add(btnSave);
		
		return bottomPanel;
	}
/*********************************** Tiornan Code to go here ************************************************************/
	private JPanel buildCenterPanel() {
		//fillTable (frame, studentID);
		JPanel centerPanel = new JPanel();
		
		centerPanel.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][center][][][][][][]", "[][][][][][][][][][][][][][][grow][][][][][][][]"));
		
		JLabel lblFirstName = new JLabel("First Name");
		centerPanel.add(lblFirstName, "cell 0 1,alignx left");
		
		txtFirstName = new JTextField();
		centerPanel.add(txtFirstName, "cell 2 1,alignx center");
		txtFirstName.setColumns(10);
		
		JLabel lblDoctorsName = new JLabel("Doctors Name");
		centerPanel.add(lblDoctorsName, "cell 6 1,alignx left");
		
		txtDoctor = new JTextField();
		
		centerPanel.add(txtDoctor, "cell 8 1,alignx center");
		txtDoctor.setColumns(10);
		
		JLabel lblGuardian = new JLabel("Guardian 1");
		centerPanel.add(lblGuardian, "cell 12 1,alignx left");
		
		txtGuardian1 = new JTextField();
		centerPanel.add(txtGuardian1, "cell 14 1,alignx center");
		txtGuardian1.setColumns(10);
		
		
		JLabel lblCommencementDate = new JLabel("Commencement Date");
		centerPanel.add(lblCommencementDate, "cell 18 1,alignx left");
		
		//dateCommencement = new JDateChooser();
		//centerPanel.add(dateCommencement, "cell 20 1,growx");
		txtStart = new JTextField();
		centerPanel.add(txtStart, "cell 20 1,growx");
		txtStart.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname");
		centerPanel.add(lblSurname, "cell 0 3,alignx left");
		
		txtSurname = new JTextField();
		centerPanel.add(txtSurname, "cell 2 3,alignx center");
		txtSurname.setColumns(10);
		
		JLabel lblAddress_3 = new JLabel("Address 1");
		centerPanel.add(lblAddress_3, "cell 6 3,alignx left");
		
		txtAdd1 = new JTextField();
		centerPanel.add(txtAdd1, "cell 8 3,alignx center");
		txtAdd1.setColumns(10);
		
		JLabel lblPhoneNumber_1 = new JLabel("Phone Number");
		centerPanel.add(lblPhoneNumber_1, "cell 12 3,alignx left");
		
		txtNumG1 = new JTextField();
		centerPanel.add(txtNumG1, "cell 14 3,alignx center");
		txtNumG1.setColumns(10);
		
		JLabel lblCessitationDate = new JLabel("Cessation Date");
		centerPanel.add(lblCessitationDate, "cell 18 3,alignx left");
		
		dateCessation = new JDateChooser();
		centerPanel.add(dateCessation, "cell 20 3,growx");
		//txtEnd = new JTextField();
		//centerPanel.add(txtEnd, "cell 20 3,growx");
		//txtEnd.setColumns(10);
		
		JLabel lblDateOfBirth = new JLabel("Date Of Birth");
		centerPanel.add(lblDateOfBirth, "cell 0 5,alignx left");
		
		//dateDOB = new JDateChooser();
		//centerPanel.add(dateDOB, "cell 2 5,alignx center");
		txtDOB = new JTextField();
		centerPanel.add(txtDOB, "cell 2 5,alignx center");
		txtDOB.setColumns(10);
		
		JLabel lblAddress_4 = new JLabel("Address 2");
		centerPanel.add(lblAddress_4, "cell 6 5,alignx left");
		
		txtDocAdd2 = new JTextField();
		centerPanel.add(txtDocAdd2, "cell 8 5,alignx center");
		txtDocAdd2.setColumns(10);
		
		JLabel lblGuardian_1 = new JLabel("Guardian 2");
		centerPanel.add(lblGuardian_1, "cell 12 5,alignx left");
		
		txtGuardian2 = new JTextField();
		centerPanel.add(txtGuardian2, "cell 14 5,alignx center");
		txtGuardian2.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address 1");
		centerPanel.add(lblAddress, "cell 0 7,alignx left");
		
		txtStudentAdd1 = new JTextField();
		centerPanel.add(txtStudentAdd1, "cell 2 7,alignx center");
		txtStudentAdd1.setColumns(10);
		
		JLabel lblAddress_5 = new JLabel("Address 3");
		centerPanel.add(lblAddress_5, "cell 6 7");
		
		txtDocAdd3 = new JTextField();
		centerPanel.add(txtDocAdd3, "cell 8 7,alignx center");
		txtDocAdd3.setColumns(10);
		
		JLabel lblPhoneNumber_2 = new JLabel("Phone Number");
		centerPanel.add(lblPhoneNumber_2, "cell 12 7,alignx left");
		
		txtNumG2 = new JTextField();
		centerPanel.add(txtNumG2, "cell 14 7,alignx center");
		txtNumG2.setColumns(10);
		
		JLabel lblAddress_1 = new JLabel("Address 2");
		centerPanel.add(lblAddress_1, "cell 0 9,alignx left");
		
		txtStudentAdd2 = new JTextField();
		centerPanel.add(txtStudentAdd2, "cell 2 9,alignx center");
		txtStudentAdd2.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		centerPanel.add(lblPhoneNumber, "cell 6 9");
		
		txtNumDoc = new JTextField();
		centerPanel.add(txtNumDoc, "cell 8 9,alignx center");
		txtNumDoc.setColumns(10);
		
		JLabel lblEmergencyContact = new JLabel("Emergency Contact");
		centerPanel.add(lblEmergencyContact, "cell 12 9,alignx left");
		
		txtEmergency = new JTextField();
		centerPanel.add(txtEmergency, "cell 14 9,alignx center");
		txtEmergency.setColumns(10);
		
		JLabel lblAddress_2 = new JLabel("Address 3");
		centerPanel.add(lblAddress_2, "cell 0 11,alignx left");
		
		txtStudentAdd3 = new JTextField();
		centerPanel.add(txtStudentAdd3, "cell 2 11,alignx center");
		txtStudentAdd3.setColumns(10);
		
		JLabel lblPhoneNumber_3 = new JLabel("Phone Number");
		centerPanel.add(lblPhoneNumber_3, "cell 12 11,alignx left");
		
		txtNumEmergency = new JTextField();
		centerPanel.add(txtNumEmergency, "cell 14 11,alignx center");
		txtNumEmergency.setColumns(10);
		
		JLabel lblAllergies = new JLabel("Allergies");
		centerPanel.add(lblAllergies, "cell 0 14,aligny top");
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		centerPanel.add(textArea, "cell 2 14,grow");
		
		JLabel lblMedicalConditions = new JLabel("Medical Conditions");
		centerPanel.add(lblMedicalConditions, "cell 6 14,aligny top");
		
		setLblFont(lblFirstName);
		setLblFont(lblSurname);
		setLblFont(lblAddress);
		setLblFont(lblAddress_1);
		setLblFont(lblAddress_2);
		setLblFont(lblAddress_3);
		setLblFont(lblAddress_4);
		setLblFont(lblAddress_5);
		setLblFont(lblAllergies);
		setLblFont(lblCessitationDate);
		setLblFont(lblCommencementDate);
		setLblFont(lblDateOfBirth);
		setLblFont(lblDoctorsName);
		setLblFont(lblEmergencyContact);
		setLblFont(lblGuardian);
		setLblFont(lblGuardian_1);
		setLblFont(lblMedicalConditions);
		setLblFont(lblPhoneNumber);
		setLblFont(lblPhoneNumber_1);
		setLblFont(lblPhoneNumber_2);
		setLblFont(lblPhoneNumber_3);
		
		textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		centerPanel.add(textArea_1, "cell 8 14,grow");
		
		
		
		
	
	//}
	//public void fillTable(MainScreen frame, String studentID){
		//this.frame=frame;
		//this.studentID=studentID;
		Sqlengine dbEngine = new Sqlengine ("root", "root");
		dbEngine.connect();
		ResultSetMetaData rsmd = null;
		ResultSetMetaData rsmd2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		
		String sqlStatement = "SELECT * FROM student where StudentID='"+studentID+"'";
		
		
		try{
			rs = dbEngine.executeQuery(sqlStatement);
			rsmd = rs.getMetaData();
			rs.next();
			
			
			//System.out.println(rs2.getString(2));
			/**
			 * setting all the fields with the values from the database
			 */
			txtFirstName.setText(rs.getString(3));
			txtSurname.setText(rs.getString(4));
			txtDOB.setText(rs.getString(5));
			txtDOB.setEditable(false);
			//dateDOB.equals(rs.getString(5));
			txtStudentAdd1.setText(rs.getString(6));
			txtStudentAdd2.setText(rs.getString(7));
			txtStudentAdd3.setText(rs.getString(8));
			txtGuardian1.setText(rs.getString(9));
			txtNumG1.setText(rs.getString(10));
			txtGuardian2.setText(rs.getString(11));
			txtNumG2.setText(rs.getString(12));
			txtEmergency.setText(rs.getString(13));
			txtNumEmergency.setText(rs.getString(14));
			
			//dateCommencement.equals(rs.getString(15));
			
			txtStart.setText(rs.getString(15));
			txtStart.setEditable(false);
			dateCessation.equals(rs.getString(16));
			//txtEnd.setText(rs.getString(16));
			textArea.setText(rs.getString(17));
			textArea_1.setText(rs.getString(18));
			

			docID = rs.getString(2);
			
			String sqlStatement2 = "SELECT * FROM doctor where docID='"+docID+"'";
			rs2 = dbEngine.executeQuery(sqlStatement2);
			rsmd2 = rs2.getMetaData();
			rs2.next();
			
			txtDoctor.setText(rs2.getString(2));
			txtAdd1.setText(rs2.getString(3));
			txtDocAdd2.setText(rs2.getString(4));
			txtDocAdd3.setText(rs2.getString(5));
			txtNumDoc.setText(rs2.getString(6));
			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		centerPanel.setBackground(Design.getLightGreen());
		
		return centerPanel;
	}
		

	private void setLblFont(JLabel lbl) {
		lbl.setFont(Design.getBoldFont());
		lbl.setForeground(Design.getDarkGreen());
	}

	private void saveToDataBase() {
		Sqlengine dbEngine = new Sqlengine ("root", "root");
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
			docIDResult = dbEngine.executeQuery("SELECT docID FROM doctor WHERE docName LIKE '"+txtDoctor.getText()+"' AND docPhone LIKE '"+txtNumDoc.getText()+"'");

			//docIDResult = dbEngine.executeQuery("SELECT docID FROM doctor WHERE docName LIKE 'Cat' AND docPhone LIKE '123456'");
			//docIDResult = dbEngine.executeQuery("SELECT docID FROM doctor WHERE docName LIKE 'Cat' AND docPhone LIKE '123456'");

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
			
			
			//THIS ONE WORKS!! no back ticks  or single quotes around table name and columns... single quotes '' around values other than NULL 
			String sqlStatement= "INSERT INTO doctor (docID, docName, docAdd1, docAdd2, docAdd3, docPhone) VALUES (NULL, '"+txtDoctor.getText()+"', '"+txtAdd1.getText()+"', '"+txtDocAdd2.getText()+"', '"+txtDocAdd3.getText()+"', '"+ txtNumDoc.getText()+"')" ;

	
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
			docIDResult = dbEngine.executeQuery("SELECT docID FROM doctor WHERE docName LIKE '"+txtDoctor.getText()+"' AND docPhone LIKE '"+txtNumDoc.getText()+"'");

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
		 * docID --> is now the correct column name
		 * commencement --> changed from commencementDate
		 * cessation --> changed from cessationDate
		 */
		//String sqlStatement2="INSERT INTO student (StudentID, doctor_docID, firstName, surname, DOB, add1, add2, add3, guardian1, guardian1Phone, guardian2, guardian2Phone, emergencyContactName, emergencyContactPhone, commencement, cessation, medicalCondition, allergies) VALUES (NULL, "+docIDint+", '"+txtFirstName.getText()+"', '"+txtSurname.getText()+"', '"+txtDOB.getText()+"', '"+txtStudentAdd1.getText()+"', '"+txtStudentAdd2.getText()+"', '"+txtStudentAdd3.getText()+"', '"+txtGuardian1.getText()+"', '"+txtNumG1.getText()+"', '"+txtGuardian2.getText()+"', '"+txtNumG2.getText()+"', '"+txtEmergency.getText()+"', '"+txtNumEmergency.getText()+"', '"+txtStart.getText()+"',"+strCessation+", '"+textArea.getText()+"', '"+textArea_1.getText()+"')";
		
		
		//String sqlStatement2="UPDATE student SET doctor_docID = '"+docIDint+"', firstName = '"+txtFirstName.getText()+"', surname = '"+txtSurname.getText()+"', add1 = '"+txtStudentAdd1.getText()+"', add2 = '"+txtStudentAdd2.getText()+"', add3 = '"+txtStudentAdd3.getText()+"', guardian1 = '"+txtGuardian1.getText()+"', guardian1Phone = '"+txtNumG1.getText()+"', guardian2 = '"+txtGuardian2.getText()+"', guardian2Phone = '"+txtNumG2.getText()+"', emergencyContactName = '"+txtEmergency.getText()+"', emergencyContactPhone = '"+txtNumEmergency.getText()+"', cessation = "+strCessation+", medicalCondition = '"+textArea.getText()+"', allergies = '"+textArea_1.getText()+"' WHERE student.StudentID = "+studentID+" AND student.doctor_docID = "+docID;
		String sqlStatement2="UPDATE student SET doctor_docID = '"+docIDint+"', firstName = '"+txtFirstName.getText()+"', surname = '"+txtSurname.getText()+"', add1 = '"+txtStudentAdd1.getText()+"', add2 = '"+txtStudentAdd2.getText()+"', add3 = '"+txtStudentAdd3.getText()+"', guardian1 = '"+txtGuardian1.getText()+"', guardian1Phone = '"+txtNumG1.getText()+"', guardian2 = '"+txtGuardian2.getText()+"', guardian2Phone = '"+txtNumG2.getText()+"', emergencyContactName = '"+txtEmergency.getText()+"', emergencyContactPhone = '"+txtNumEmergency.getText()+"', cessation = "+strCessation+", medicalCondition = '"+textArea.getText()+"', allergies = '"+textArea_1.getText()+"' WHERE student.StudentID = "+studentID+" AND student.doctor_docID = "+docID;

		System.out.println("docIDint:"+docIDint);																																																													//INSERT INTO student (studentID, doctorID, firstName, surname, 																						DOB, add1, add2, add3, 																												guardian1, guardian1Phone, 																				guardian2, guardian2Phone, 	//+strCessation strCessation =" Null "		or = " 'datejsdhf' "																			emergencyContactName, emergencyContactPhone, commencementDate, cessationDate, medicalCondition, allergies) VALUES (NULL, '', '', '', '', NULL, NULL, NULL, '', '', NULL, NULL, '', '', NULL, NULL, NULL, NULL)
		System.out.println("docID:"+docID);
		System.out.println("studentID:"+studentID);
				
				//(NULL, "+docIDint+", '"+txtFirstName.getText()+"', '"+txtSurname.getText()+"', '"+										txtDOB.getText()+"', '"+txtStudentAdd1.getText()+"', '"+txtStudentAdd2.getText()+"', '"+txtStudentAdd3.getText()+"', '"+txtGuardian1.getText()+"', '"+txtNumG1.getText()+"', '"+txtGuardian2.getText()+"', '"+txtNumG2.getText()+"', '"+txtEmergency.getText()+"', '"+txtNumEmergency.getText()+"', 													'"+txtStart.getText()+"',"+strCessation+", '"+textArea.getText()+"', '"+textArea_1.getText()+"')"
		// insert the data

		/**
		 * The below will be to Update the student table... yet to do!!
		 * 
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
}
