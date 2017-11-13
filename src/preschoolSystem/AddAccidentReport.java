package preschoolSystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import net.miginfocom.swing.MigLayout;
/**
 * 
 * @author catharine
 * Accident Report - A panel to display an accident report
 *
 */


@SuppressWarnings("unused")
public class AddAccidentReport {
	MainScreen frame;
	String studentID;
	
	JTextArea accidentReportTextArea;
	JDateChooser dateOfReport;
	
	
	/**
	 * 
	 * @param frame of the MainScreen
	 * @param studentID passed from the users choice in ReportChoice
	 * 
	 */
	public AddAccidentReport(MainScreen frame, String studentID){
		
		this.frame=frame;
		this.studentID= studentID;
		
		String studentName = Design.getStudentName(studentID);
		
		JPanel topPanel = (Design.buildTopPanel("Add Accident Report for "+studentName));
		JPanel centerPanel = (buildCenterPanel());
		JPanel bottomPanel = (buildbottomPanel());
		
		JPanel wrapPanel = new JPanel(new BorderLayout());
		wrapPanel.add(topPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);
	
		frame.swapPanelCards.add(wrapPanel, "AddAccidentReport");
	}

	/**
	 * to Build the Panel in the center of the screen
	 * @return a JPanel to be displayed in the center of the Larger "wrapPanel"
	 */
	private JPanel buildCenterPanel() {
	
		
		dateOfReport = new JDateChooser();
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel northPanel = new JPanel();
		JLabel dateLabel = new JLabel("Date of Accident Report: ");
		JLabel infoLabel = new JLabel("If you do not enter a Date, todays date will be used as default.");
		
		dateLabel.setFont(Design.getSmallTitleFont());
		dateLabel.setForeground(Design.getDarkGreen());
		infoLabel.setFont(Design.getBoldFont());
		infoLabel.setForeground(Design.getDarkGreen());
		northPanel.add(dateLabel);
		northPanel.add(dateOfReport);
		northPanel.add(infoLabel);
		northPanel.setBackground(Design.getLightGreen());
		
		centerPanel.add(northPanel, BorderLayout.NORTH);
		centerPanel.setBackground(Design.getLightGreen());
	
		
		accidentReportTextArea = new JTextArea(20,20);//r,c
		
		
		accidentReportTextArea.setLineWrap(true);
		
		accidentReportTextArea.setFont(Design.getButtonFont());
		accidentReportTextArea.setForeground(Design.getDarkGreen());
	
		accidentReportTextArea.setBorder(new EmptyBorder(50,50,20,50));
		accidentReportTextArea.setText("Who: \nWhat: \nWhen: \nWhere: \nHow: \nWhat was done: \nParents Notified and form signed: Yes\\No ");
		ScrollPane scroll = new ScrollPane();
		scroll.setPreferredSize(new Dimension(1000, 500));
		scroll.add(accidentReportTextArea);
		centerPanel.add(scroll, BorderLayout.CENTER);
		

		centerPanel.setBorder(new EmptyBorder(50,50,20,50));
	
		return centerPanel;
		
		
	}
		
		
	 /*
	 * 	To build the JPanel with buttons at the bottom of the screen
	 * @return a JPanel to be displayed at the SOUTH of the "wrapPanel"
	 */
	
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
				new ReportChoice(frame, studentID);
				frame.myCardLayout.show(frame.swapPanelCards, "ReportChoice");
				
			}
		});
		
		JButton homeButton = new JButton("Home");
		homeButton.setFont(Design.getButtonFont());
		homeButton.setForeground(Design.getDarkGreen());
		homeButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		homeButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				//if cancel is pushed, ask if they are sure
				int choice = JOptionPane.showConfirmDialog(frame, "Are you sure?  OK to go back to Home Screen ", "Title", JOptionPane.OK_CANCEL_OPTION);
				
				if(choice == JOptionPane.YES_OPTION){	//if the user clicks OK, go back to Home screen, else do nothing
					frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
				}	
				
			}
		});
		
		JButton saveButton = new JButton("Save");
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
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,150,20,150));
		bottomPanel.setBackground(Design.getLightGreen());
		
		bottomPanel.add(backButton);
		bottomPanel.add(homeButton);
		bottomPanel.add(saveButton);

		return bottomPanel;
	}
	private void saveToDataBase() {
		Sqlengine dbEngine = new Sqlengine("root", "root"); //database engine
		dbEngine.connect();
		String dateOfReportStr;
	
		Connection conn = dbEngine.getConn();
		
		Date dateOfAccidentReport = dateOfReport.getDate();
		if(dateOfAccidentReport == null){
			
			Date todaysDate = new Date();
		
			dateOfReportStr = String.format("%1$tY-%1$tm-%1$td",todaysDate);

		}else{
			dateOfReportStr = String.format("%1$tY-%1$tm-%1$td",dateOfAccidentReport);
		}
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("INSERT INTO accidentreport (accidentReportID, accidentReport, dateOfReport, Student_StudentID) VALUES (NULL,?,?,? )");
			//INSERT INTO `accidentreport` (`accidentReportID`, `accidentReport`, `dateOfReport`, `Student_StudentID`) VALUES (NULL, 'Test Accident Report', '2017-04-02', '6');
			pstmt.setString(1, accidentReportTextArea.getText());
			pstmt.setString(2, dateOfReportStr);
			pstmt.setString(3, studentID);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			// insert the data
		
			pstmt.execute();
			JOptionPane.showMessageDialog(frame, "New Student Details have been added to the database", "Details Saved", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "New Student Details were not added to the database. Please Try Agin", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}