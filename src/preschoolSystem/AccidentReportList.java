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
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * @author Catharine
 *
 */


@SuppressWarnings("unused")
public class AccidentReportList {

	JTable dateTable;
	MainScreen frame;
	String studentID;
	
	/**
	 * @param frame
	 * @param studentID
	 */
	public AccidentReportList(MainScreen frame, String studentID){
		
		this.frame=frame;
		this.studentID= studentID;
		
		JPanel topPanel = (Design.buildTopPanel("Accident Report List"));
		JPanel centerPanel = (buildCenterPanel());
		JPanel bottomPanel = (buildbottomPanel());
		
		JPanel wrapPanel = new JPanel(new BorderLayout());
		wrapPanel.add(topPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.swapPanelCards.add(wrapPanel, "AccidentReportList");
	}
	
	private JPanel buildCenterPanel() {
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Design.getLightGreen());
		
		//connect to DataBase
		Sqlengine dbEngine = new Sqlengine("root", "root");
		dbEngine.connect();
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		int colCount=0;
		String [] colNames = null;
		
		try {
			rs = dbEngine.executeQuery("SELECT dateOfReport FROM accidentreport WHERE Student_StudentID ='"+studentID+"'");
			rsmd = rs.getMetaData();
			colCount = rsmd.getColumnCount();
	        colNames = new String[colCount];

	        colNames[0] = "Date Of Report";

	        //Create a table model (used for controlling a JTable)
			DefaultTableModel model = new DefaultTableModel(colNames, 0);
			
			//A new Table to display the list of dates
			dateTable = new JTable(model);
			
			//set font and colours for table headers and the cells
			dateTable.getTableHeader().setFont(Design.getSmallTitleFont());
			dateTable.getTableHeader().setForeground(Design.getDarkGreen());
			dateTable.getTableHeader().setBackground(Design.getLightGreen());
					
			dateTable.setFont(Design.getBoldFont());
			dateTable.setForeground(Design.getDarkGreen());
			dateTable.setBackground(Design.getLightGreen());
			dateTable.setRowHeight(30);
			dateTable.setRowMargin(10);
		
			
			//a ListSelectionModel represents the current state of the selection
			DefaultListSelectionModel dlsm = new DefaultListSelectionModel();
			//allow single selection only from studentTable
			dlsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			dateTable.setSelectionModel(dlsm);
			dlsm.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					String dateOfReport = (String) dateTable.getValueAt(dateTable.getSelectedRow(), dateTable.getColumnCount()-1);
					new AccidentReport(frame, studentID, dateOfReport);
					frame.myCardLayout.show(frame.swapPanelCards, "AccidentReport");
				}
			}); 
			
			String [] currentRow = new String[colCount];//array to hold the row data
			while(rs.next()) { //move the students pointer on to the next record (starts before the 1st)
				for(int i=1;i<=colCount;i++) {
					currentRow[i-1] = rs.getString(i);
				}
				model.addRow(currentRow); //add the row to the table through the table model
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//Close Database Connection
		dbEngine.closeConnection();
		
		//add the table to a scroll pane
		JScrollPane scroll = new JScrollPane(dateTable);
		scroll.setBackground(Design.getLightGreen());
		
		//give the panel to give space on the edges
		centerPanel.setBorder(new EmptyBorder(50,200,50,200)); //top, left, bottom, right
			
		centerPanel.add(scroll);
		
		return centerPanel;
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
				//back button goes to previous page - view student details
				frame.myCardLayout.show(frame.swapPanelCards, "ViewStudent2");
			}
		});
		
		JButton homeButton = new JButton("Home");
		homeButton.setFont(Design.getButtonFont());
		homeButton.setForeground(Design.getDarkGreen());
		homeButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		homeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//home button goes to the Main Screen
				frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
			}
		});
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,150,20,150));
		bottomPanel.setBackground(Design.getLightGreen());
		
		bottomPanel.add(backButton);
		bottomPanel.add(homeButton);

		return bottomPanel;
	}
}
