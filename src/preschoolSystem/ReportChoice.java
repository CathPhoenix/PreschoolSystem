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
public class ReportChoice extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**************************** Change TextField's for dates to JDateChooser ********************************/
	static JDateChooser dateDOB ;
	static JDateChooser dateCommencement;
	
	MainScreen frame;
	String studentID;
	String docID;
	


	public ReportChoice(MainScreen frame, String studentID) {
		
		this.frame = frame;
		this.studentID = studentID;
		
		
		JPanel topPanel = (Design.buildTopPanel("Which report do you want to file for "+Design.getStudentName(studentID)));
		JPanel centerPanel = (buildCenterPanel());
		JPanel bottomPanel = (buildbottomPanel());
		
		JPanel wrapPanel = new JPanel(new BorderLayout());
		wrapPanel.add(topPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.swapPanelCards.add(wrapPanel, "ReportChoice");
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
				//if back pushed, go to list of students
				new ReportsView(frame);
				frame.myCardLayout.show(frame.swapPanelCards, "ReportsView");
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(Design.getButtonFont());
		btnCancel.setForeground(Design.getDarkGreen());
		btnCancel.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		
		btnCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//if cancel is pushed, ask if they are sure
				int choice = JOptionPane.showConfirmDialog(frame, "Are you sure?  OK to go back to Home Screen ", "Title", JOptionPane.OK_CANCEL_OPTION);
				
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
				
				//will create a method saveToDataBase to handle saving modifications to database
				//saveToDataBase();
				frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
				JOptionPane.showMessageDialog(frame, "New Student Details have been added to the database", "Details Saved", JOptionPane.INFORMATION_MESSAGE);				
			}
		
		});
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		bottomPanel.setBackground(Design.getLightGreen());

		bottomPanel.add(btnBack);
		bottomPanel.add(btnCancel);
		//bottomPanel.add(btnSave);
		
		return bottomPanel;
	}

	private JPanel buildCenterPanel() {
		
		JPanel centerPanel = new JPanel(new GridLayout(1,2,200,90)); //GridLayout(rows, columns, horizontal gap, vertical gap)
		Border emptyBorder = BorderFactory.createEmptyBorder(220, 150, 220, 150);
		Border topLineBorder =  BorderFactory.createMatteBorder(3, 0, 0, 0, Design.getDarkGreen());
		
		JButton btnAddAccidentReport = new JButton("Add Accident Report");
		centerPanel.add(btnAddAccidentReport);
		btnAddAccidentReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddAccidentReport(frame, studentID);
				frame.myCardLayout.show(frame.swapPanelCards, "AddAccidentReport");
				
			}
		});
		
		JButton btnAddProgressReport = new JButton("Add Progress Report");
		centerPanel.add(btnAddProgressReport);
		btnAddProgressReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddProgressReport(frame, studentID);
				frame.myCardLayout.show(frame.swapPanelCards, "AddProgressReport");
				
			}
		});
		
		Border greenLine =  BorderFactory.createMatteBorder(3, 3, 3, 3, Design.getDarkGreen());//(top, left, bottom, right)
		Border emptyBorder2 = BorderFactory.createEmptyBorder(35, 25, 35, 25);//(top, left, bottom, right)
		
		btnAddAccidentReport.setFont(Design.getButtonFont());
		btnAddAccidentReport.setForeground(Design.getDarkGreen());
		btnAddAccidentReport.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder2));
		
		btnAddProgressReport.setFont(Design.getButtonFont());
		btnAddProgressReport.setForeground(Design.getDarkGreen());
		btnAddProgressReport.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder2));
		
		
		centerPanel.setBackground(Design.getLightGreen());
		centerPanel.setBorder(BorderFactory.createCompoundBorder(topLineBorder, emptyBorder));

		return centerPanel;
		
	}
	

	
}
