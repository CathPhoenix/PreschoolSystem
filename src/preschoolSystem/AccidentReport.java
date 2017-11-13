package preschoolSystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Accident Report - A Panel to display an Accident Report
 * @author catharine
 *
 */
@SuppressWarnings("unused")
public class AccidentReport {
	MainScreen frame;
	String studentID;
	String dateOfReport;
	
	/**
	 * constructor to build a JPanel to display an Accident Report
	 * @param frame of the MainScreen
	 * @param studentID passed from the users choice in AccidentReportList
	 * @param dateOfReport passed from the users choice in AccidentReportList
	 */
	public AccidentReport(MainScreen frame, String studentID, String dateOfReport){
		
		this.frame=frame;
		this.studentID= studentID;
		this.dateOfReport=dateOfReport;
		
		JPanel topPanel = (Design.buildTopPanel("Accident Report"));
		JPanel centerPanel = (buildCenterPanel());
		JPanel bottomPanel = (buildbottomPanel());
		
		JPanel wrapPanel = new JPanel(new BorderLayout());
		wrapPanel.add(topPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.swapPanelCards.add(wrapPanel, "AccidentReport");
	}
	
	/**
	 * to Build the Panel in the center of the screen
	 * @return a JPanel to be displayed in the center of the Larger "wrapPanel"
	 */
	private JPanel buildCenterPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Design.getLightGreen());
		
		//Display Date of report at top on page
		JLabel dateLabel = new JLabel("Date Of Report: "+dateOfReport);
		dateLabel.setFont(Design.getBoldFont());
		dateLabel.setForeground(Design.getDarkGreen());
		centerPanel.add(dateLabel, BorderLayout.NORTH);
	
		//Connect to Database
		Sqlengine dbEngine = new Sqlengine("root", "root");
		dbEngine.connect();

		ResultSet rs = null;

		//SQL Statement to be executed
		String sqlStatement = "SELECT accidentReport FROM accidentreport WHERE Student_StudentID ='"+studentID+"' AND dateOfReport = '"+dateOfReport+"'";
		try {
			//execute SQL statement
			rs = dbEngine.executeQuery(sqlStatement);
			
			//if there is a result
			if(rs.next()){
				
				//create a textarea
				JTextArea myTextArea = new JTextArea();
				
				myTextArea.setText(rs.getString(1));	//get the text from the result
				myTextArea.setLineWrap(true);
				myTextArea.setEditable(false);
				myTextArea.setFont(Design.getButtonFont());
				myTextArea.setForeground(Design.getDarkGreen());
				//myTextArea.setBackground(Design.getLightGreen());	//Leave Background white for text area
				myTextArea.setBorder(new EmptyBorder(50,50,20,50));
				
				//add textarea to a scrollpane
				ScrollPane scroll = new ScrollPane();
				scroll.setPreferredSize(new Dimension(1000, 500));
				scroll.add(myTextArea);
				centerPanel.add(scroll, BorderLayout.CENTER);
			}

		}
		catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		dbEngine.closeConnection();
		
		centerPanel.setBorder(new EmptyBorder(50,50,20,50));
	
		return centerPanel;
	}

	/**
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
				//if back button pushed - load a new AccidentReportList page (so no record is pre-selected)
				new AccidentReportList(frame, studentID);
				frame.myCardLayout.show(frame.swapPanelCards, "AccidentReportList");
			}
		});
		
		JButton homeButton = new JButton("Home");
		homeButton.setFont(Design.getButtonFont());
		homeButton.setForeground(Design.getDarkGreen());
		homeButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		homeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//if home button pushed, load the home screen/ MainScreen
				frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");			
			}
		});
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,150,20,150));	//set empty border to space elements out
		bottomPanel.setBackground(Design.getLightGreen());
		
		bottomPanel.add(backButton);
		bottomPanel.add(homeButton);

		return bottomPanel;
	}
}
