package preschoolSystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;



/**
 * Not finished... need to connect to database, start from the listener.. line 185
 * @author catharine
 *
 */

public class DeleteStudent extends JFrame implements ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea displayArea;
	JTable studentTable;
	JScrollPane scroll;
	String studentID;
	
	MainScreen frame;
	private String studentName;
	
	public DeleteStudent(MainScreen frame) {
		this.frame = frame;
		JPanel topPanel = (Design.buildTopPanel("Choose which Student Details need to be Deleted"));
		JPanel centerPanel = (buildCenterPanel());
		JPanel bottomPanel = (buildbottomPanel());
		
		JPanel wrapPanel = new JPanel(new BorderLayout());
		wrapPanel.add(topPanel, BorderLayout.NORTH);
		wrapPanel.add(centerPanel, BorderLayout.CENTER);
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.swapPanelCards.add(wrapPanel, "DeleteStudent");
	}

	private JPanel buildbottomPanel() {
		JPanel bottomPanel = new JPanel();
		Border greenLine =  BorderFactory.createMatteBorder(2, 2, 2, 2, Design.getDarkGreen());//(top, left, bottom, right)
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 80, 10, 80);//(top, left, bottom, right)
		
		JButton backButton = new JButton("Back");
		backButton.setFont(Design.getButtonFont());
		backButton.setForeground(Design.getDarkGreen());
		backButton.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		backButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
				
			}
		});
		


		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		bottomPanel.setBackground(Design.getLightGreen());
		
		bottomPanel.add(backButton);

		

		return bottomPanel;
	}

	private JPanel buildCenterPanel() {
		JPanel thisPanel = new JPanel(new GridLayout(1, 1));
		
		getStudentDetails();
		scroll = new JScrollPane(studentTable);
		scroll.setBackground(Design.getLightGreen());

		thisPanel.setBorder(new EmptyBorder(50,200,50,200)); //top, left, bottom, right
		thisPanel.setBackground(Design.getLightGreen());
		
		thisPanel.add(scroll);
		
		return thisPanel;
	}

	private void getStudentDetails() {
		Sqlengine dbEngine = new Sqlengine("root", "root");
		
		dbEngine.connect();
		ResultSet students = null;
		ResultSetMetaData rsmd = null;
		int colCount=0;
		String [] colNames = null;
		
		try {
			students = dbEngine.executeQuery("SELECT  StudentID, firstName, surname, DOB FROM student ORDER BY firstName ASC");
			rsmd = students.getMetaData();
			colCount = rsmd.getColumnCount();
	        colNames = new String[colCount];
	        
	       /* for(int i=1;i<=colCount;i++) {
	        	colNames[i-1] = rsmd.getColumnName(i);
	        }*/
	        colNames[0] = "Student ID";
	        colNames[1] = "First Name";
	        colNames[2] = "Surname";
	        colNames[3] = "Date of Birth";
	        
	      //Create a table model (used for controlling a JTable)
			DefaultTableModel model = new DefaultTableModel(colNames,0);
			studentTable = new JTable(model);
			
			
			 
			studentTable.getTableHeader().setFont(Design.getSmallTitleFont());
			studentTable.getTableHeader().setForeground(Design.getDarkGreen());
			studentTable.getTableHeader().setBackground(Design.getLightGreen());
			

			
			studentTable.setFont(Design.getBoldFont());
			studentTable.setForeground(Design.getDarkGreen());
			studentTable.setBackground(Design.getLightGreen());
			studentTable.setRowHeight(30);
			studentTable.setRowMargin(10);
			
			
			
			
			//Similarly a ListSelectionModel represents the current state of the selection
			//for components (like JTables) 
			DefaultListSelectionModel dlsm = new DefaultListSelectionModel();
			//allow single selection only from studentTable
			dlsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			studentTable.setSelectionModel(dlsm);
			dlsm.addListSelectionListener(this); //lets use this JFrame as the event handler for
													//when an item is selected
			
			String [] currentRow = new String[colCount];//array to hold the row data
			while(students.next()) { //move the students pointer on to the next record (starts before the 1st)
				for(int i=1;i<=colCount;i++) {
					currentRow[i-1] = students.getString(i);
				}
				model.addRow(currentRow); //add the row to the table through the table model
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		

		

		 dbEngine.closeConnection();

		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		studentID = (String) studentTable.getValueAt(studentTable.getSelectedRow(), studentTable.getColumnCount()-4);
		studentName = (String) studentTable.getValueAt(studentTable.getSelectedRow(), studentTable.getColumnCount()-3);
		
		UIManager.put("OptionPane.background", Design.getLightGreen());
		UIManager.put("Panel.background", Design.getLightGreen());

		int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to Delete "+studentName+"'s records?" , "Are You Sure?", JOptionPane.YES_NO_OPTION);
	
		if(choice == JOptionPane.YES_OPTION){	//if the user clicks Yes...
			deleteStudent();	
		}else{
			new DeleteStudent(frame);
			frame.myCardLayout.show(frame.swapPanelCards, "DeleteStudent");
		}
		
	}

	private void deleteStudent() { 
		Sqlengine dbEngine = new Sqlengine("root", "root");
	
		dbEngine.connect();

		try {
			dbEngine.executeQuery("DELETE FROM student WHERE student.StudentID = '"+studentID+"'");
			JOptionPane.showMessageDialog(frame, studentName+"'s records have been removed", "Confirmaton", JOptionPane.INFORMATION_MESSAGE);
			new DeleteStudent(frame);
			frame.myCardLayout.show(frame.swapPanelCards, "DeleteStudent");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "There was a problem removing "+studentName+"'s records, please try again", "Error", JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();
		}
		
	}

}
