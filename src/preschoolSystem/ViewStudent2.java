package preschoolSystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.*;

import javax.swing.border.*;

/**
 * @author catharine
 *
 */
public class ViewStudent2 {
	MainScreen frame;
	String studentID;
	String details1;
	String details2;
	String docID;
	JTextArea showDetails1, showDetails2;
	
	/**
	 * @param frame of the MainScreen
	 * @param studentID passed from the viewStudent1
	 */
	public ViewStudent2(MainScreen frame, String studentID){
		
		this.frame=frame;
		this.studentID= studentID;
		
		JPanel topPanel = (Design.buildTopPanel("View Student Records"));
		JPanel centerPanel = (buildCenterPanel());
		JPanel bottomPanel = (buildbottomPanel());
		
		JPanel wrapPanel = new JPanel(new BorderLayout());
		wrapPanel.add(topPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);
	

		
		frame.swapPanelCards.add(wrapPanel, "ViewStudent2");
	}

	
	private JPanel buildbottomPanel() {
		JPanel bottomPanel = new JPanel(new GridLayout(1,2,150,0));
		Border greenLine =  BorderFactory.createMatteBorder(2, 2, 2, 2, Design.getDarkGreen());//(top, left, bottom, right)
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 80, 10, 80);//(top, left, bottom, right)
		
		JButton backButton = new JButton("Back");
		backButton.setFont(Design.getButtonFont());
		backButton.setForeground(Design.getDarkGreen());
		backButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		backButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new ViewStudent1(frame);
				frame.myCardLayout.show(frame.swapPanelCards, "ViewStudent1");
				
			}
		});
		
		JButton homeButton = new JButton("Home");
		homeButton.setFont(Design.getButtonFont());
		homeButton.setForeground(Design.getDarkGreen());
		homeButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		homeButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
				
			}
		});
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,150,20,150));
		bottomPanel.setBackground(Design.getLightGreen());
		
		bottomPanel.add(backButton);
		bottomPanel.add(homeButton);

		

		return bottomPanel;
	}

	private JPanel buildCenterPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Design.getLightGreen());
		
		JPanel leftPanel = new JPanel(new GridLayout(1,2));
		leftPanel.setBackground(Design.getLightGreen());
		
		JPanel rightPanel = new JPanel(new GridLayout(2,1, 0, 100)); //GridLayout(rows, columns, horizontal gap, vertical gap)
		rightPanel.setBorder(new EmptyBorder(100,50,100,50)); //top, left, bottom, right
		rightPanel.setBackground(Design.getLightGreen());
		
		details1= "";
		details2= "";
		docID = "";
		showDetails1 = new JTextArea();
		showDetails1.setEditable(false);
		showDetails1.setFont(Design.getBoldFont());
		showDetails1.setForeground(Design.getDarkGreen());
		showDetails1.setBackground(Design.getLightGreen());
		showDetails1.setBorder(new EmptyBorder(50,50,10,20));
		
		showDetails2 = new JTextArea();
		showDetails2.setLineWrap(true);
		showDetails2.setEditable(false);
		showDetails2.setFont(Design.getBoldFont());
		showDetails2.setForeground(Design.getDarkGreen());
		showDetails2.setBackground(Design.getLightGreen());
		showDetails2.setBorder(new EmptyBorder(50,50,10,50));
		
		getDetailsToDisplay();
		
		 
		showDetails1.setText(details1);
		showDetails2.setText(details2);
		leftPanel.add(showDetails1);

		
		leftPanel.add(showDetails2);
		
		//Buttons for right hand side of screen (Accident and Progress Button)
		Border greenLine =  BorderFactory.createMatteBorder(3, 3, 3, 3, Design.getDarkGreen());//(top, left, bottom, right)
		Border emptyBorder = BorderFactory.createEmptyBorder(35, 25, 35, 25);//(top, left, bottom, right)
		JButton accidentBtn = new JButton("View Accident Report");
		
		accidentBtn.setFont(Design.getButtonFont());
		accidentBtn.setForeground(Design.getDarkGreen());
		accidentBtn.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		accidentBtn.addActionListener(new ActionListener() {	
			
			public void actionPerformed(ActionEvent e) {
				new AccidentReportList(frame, studentID);
				frame.myCardLayout.show(frame.swapPanelCards, "AccidentReportList");
			}
		});
		
		JButton progressBtn = new JButton("View Progress Report");
		progressBtn.setFont(Design.getButtonFont());
		progressBtn.setForeground(Design.getDarkGreen());
		progressBtn.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		progressBtn.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				new ProgressReportList(frame, studentID);
				frame.myCardLayout.show(frame.swapPanelCards, "ProgressReportList");
			}
		});
		
		rightPanel.add(accidentBtn);
		rightPanel.add(progressBtn);
		
		
		centerPanel.add(leftPanel, BorderLayout.CENTER);
		centerPanel.add(rightPanel, BorderLayout.EAST);
		
		return centerPanel;
	}


	private void getDetailsToDisplay() {
		//connect to database
		Sqlengine dbEngine = new Sqlengine("root", "root");
		dbEngine.connect();
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		//int colCount=0;

		
		String sqlStatement = "SELECT doctor_docID, firstName, surname, DOB, add1, add2, add3, guardian1, guardian1Phone, guardian2, guardian2Phone, emergencyContactName, emergencyContactPhone, commencement, cessation, medicalCondition, allergies FROM student where StudentID='"+studentID+"'";
		
		try {
			rs = dbEngine.executeQuery(sqlStatement);
			rsmd = rs.getMetaData();
			rs.next();//move result set pointer to first and only row
			//colCount = rsmd.getColumnCount();//get number of columns
			//create a string with all student details
			
			docID = rs.getString(1);
	        
			//put in headings and correlating data into strings to display in text areas
			for(int i=2;i<=11;i++) {
	        	details1 = details1 + rsmd.getColumnName(i).toUpperCase()+": "+rs.getString(i)+"\n\n"; 
	        }
	        for(int i=12;i<=17;i++) {
	        	details2 = details2 + rsmd.getColumnName(i).toUpperCase()+": "+rs.getString(i)+"\n\n"; 
	        }
	        
		}
		catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}	
		
		String sqlStatement2 = "SELECT docName, docPhone FROM doctor where docID='"+docID+"'";

		try {
			rs1 = dbEngine.executeQuery(sqlStatement2);
			rs1.next();//move result set pointer to first and only row
			
			//adding the doctors name and number to the string to be displayed in the textarea
	        details2 = details2 + "DOCTORS NAME: "+rs1.getString(1)+"\n\n"; 
	       	details2 = details2 + "DOCTORS PHONE: "+rs1.getString(2)+"\n\n"; 
	        
		}
		catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		//close connection
		dbEngine.closeConnection();
	}

}
