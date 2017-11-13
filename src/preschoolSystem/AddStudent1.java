package preschoolSystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import net.miginfocom.swing.MigLayout;

/**
 * @author Tiornan
 *
 */
public class AddStudent1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel wrapPanel;
	private JTextField txtFirstname;
	private JTextField txtSurname;
	private JTextField txtAdd1;
	private JTextField txtAdd2;
	private JTextField txtAdd3;
	private JTextField txtGuardian1Name;
	private JTextField txtGuardian1Phone;
	private JTextField txtGuardian2Name;
	private JTextField txtGuardian2Phone;
	private JTextField txtEmergency;
	private JTextField txtEmergencyPhone;
	static String strFirstname, strSurname, strDOB,strAdd1,strAdd2,strAdd3,strGuardian1Name,strGuardian1Phone,strGuardian2Name,strGuardian2Phone,strEmergency,strEmergencyPhone,strCommencementDate;
	static JDateChooser dateDOB ;
	static JDateChooser dateCommencement;

	MainScreen frame;
	
	/**
	 * 
	 * @param frame
	 */
	public AddStudent1(MainScreen frame) {
		
		this.frame=frame;
			
		UIManager.put("OptionPane.background", Design.getLightGreen());
		UIManager.put("Panel.background", Design.getLightGreen());
		
		wrapPanel = new JPanel(new BorderLayout());
		
		JPanel panel = new JPanel(new BorderLayout()); //the panel at the top, with the title of the page
		
		panel.setBackground(Design.getLightGreen());
		panel.setBorder(BorderFactory.createMatteBorder(0,0,3,0,Design.getDarkGreen()));
		
		wrapPanel.add(panel, BorderLayout.NORTH);
		
		JLabel lblCreateARecord = new JLabel("Enter Student Details");
		lblCreateARecord.setFont(Design.getLargeTitleFont());
		lblCreateARecord.setForeground(Design.getDarkGreen());
		panel.add(lblCreateARecord, BorderLayout.WEST);
		lblCreateARecord.setBorder(new EmptyBorder(50, 30, 10, 10));
		
		//panel to hold the form details
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		wrapPanel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[][][][][][][][][]", "[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));//9 columns, 30 rows
		panel_1.setBackground(Design.getLightGreen());
	
		Border emptyLblBorder = BorderFactory.createEmptyBorder(10, 50, 20, 0);//(top, left, bottom, right)
		Border emptyLblRightBorder = BorderFactory.createEmptyBorder(10, 200, 20, 0);//(top, left, bottom, right)
		
		//Adding the textfields and labels for the form
		JLabel lblFirstname = new JLabel("Firstname");
		panel_1.add(lblFirstname, "cell 1 1");
		lblFirstname.setFont(Design.getBoldFont());
		lblFirstname.setForeground(Design.getDarkGreen());
		lblFirstname.setBorder(emptyLblBorder);
		
		txtFirstname = new JTextField();
		panel_1.add(txtFirstname, "cell 3 1,growx");
		txtFirstname.setColumns(30);
		
		JLabel lblGuardian1 = new JLabel("Guardian 1 Name");
		panel_1.add(lblGuardian1, "cell 6 1");
		lblGuardian1.setFont(Design.getBoldFont());
		lblGuardian1.setForeground(Design.getDarkGreen());
		lblGuardian1.setBorder(emptyLblRightBorder);
		
		txtGuardian1Name = new JTextField();
		panel_1.add(txtGuardian1Name, "cell 8 1,growx");
		txtGuardian1Name.setColumns(30);
		
		JLabel lblSurname = new JLabel("Surname");
		panel_1.add(lblSurname, "cell 1 4");
		lblSurname.setFont(Design.getBoldFont());
		lblSurname.setForeground(Design.getDarkGreen());
		lblSurname.setBorder(emptyLblBorder);
		
		txtSurname = new JTextField();
		panel_1.add(txtSurname, "cell 3 4,growx");
		txtSurname.setColumns(30);
		
		JLabel lblGuardian1Phone = new JLabel("Guardian 1 Phone");
		panel_1.add(lblGuardian1Phone, "cell 6 4");
		lblGuardian1Phone.setFont(Design.getBoldFont());
		lblGuardian1Phone.setForeground(Design.getDarkGreen());
		lblGuardian1Phone.setBorder(emptyLblRightBorder);
		
		txtGuardian1Phone = new JTextField();
		panel_1.add(txtGuardian1Phone, "cell 8 4,growx");
		txtGuardian1Phone.setColumns(30);
		
		JLabel lblDOB = new JLabel("Date Of Birth");
		panel_1.add(lblDOB, "cell 1 7");
		lblDOB.setFont(Design.getBoldFont());
		lblDOB.setForeground(Design.getDarkGreen());
		lblDOB.setBorder(emptyLblBorder);
	
		dateDOB = new JDateChooser();
		panel_1.add(dateDOB, "cell 3 7,growx");
		
		JLabel lblGuardian2 = new JLabel("Guardian 2 Name");
		panel_1.add(lblGuardian2, "cell 6 7");
		lblGuardian2.setFont(Design.getBoldFont());
		lblGuardian2.setForeground(Design.getDarkGreen());
		lblGuardian2.setBorder(emptyLblRightBorder);
		
		txtGuardian2Name = new JTextField();
		panel_1.add(txtGuardian2Name, "cell 8 7,growx");
		txtGuardian2Name.setColumns(30);
		
		JLabel lblAdd1 = new JLabel("Address Line 1");
		panel_1.add(lblAdd1, "cell 1 10");
		lblAdd1.setFont(Design.getBoldFont());
		lblAdd1.setForeground(Design.getDarkGreen());
		lblAdd1.setBorder(emptyLblBorder);
		
		txtAdd1 = new JTextField();
		panel_1.add(txtAdd1, "cell 3 10,growx");
		txtAdd1.setColumns(30);
		
		JLabel lblGuardian2Phone = new JLabel("Guardian 2 Phone");
		panel_1.add(lblGuardian2Phone, "cell 6 10");
		lblGuardian2Phone.setFont(Design.getBoldFont());
		lblGuardian2Phone.setForeground(Design.getDarkGreen());
		lblGuardian2Phone.setBorder(emptyLblRightBorder);
		
		txtGuardian2Phone = new JTextField();
		panel_1.add(txtGuardian2Phone, "cell 8 10,growx");
		txtGuardian2Phone.setColumns(30);
		
		JLabel lblAdd2 = new JLabel("Address Line 2");
		panel_1.add(lblAdd2, "cell 1 13");
		lblAdd2.setFont(Design.getBoldFont());
		lblAdd2.setForeground(Design.getDarkGreen());
		lblAdd2.setBorder(emptyLblBorder);
		
		txtAdd2 = new JTextField();
		panel_1.add(txtAdd2, "cell 3 13,growx");
		txtAdd2.setColumns(30);
		
		JLabel lblEmergency = new JLabel("Emergency Contact");
		panel_1.add(lblEmergency, "cell 6 13");
		lblEmergency.setFont(Design.getBoldFont());
		lblEmergency.setForeground(Design.getDarkGreen());
		lblEmergency.setBorder(emptyLblRightBorder);
		
		txtEmergency = new JTextField();
		panel_1.add(txtEmergency, "cell 8 13,growx");
		txtEmergency.setColumns(30);
		
		JLabel lblAdd3 = new JLabel("Address line 3");
		panel_1.add(lblAdd3, "cell 1 16");
		lblAdd3.setFont(Design.getBoldFont());
		lblAdd3.setForeground(Design.getDarkGreen());
		lblAdd3.setBorder(emptyLblBorder);
		
		txtAdd3 = new JTextField();
		panel_1.add(txtAdd3, "cell 3 16,growx");
		txtAdd3.setColumns(30);
		
		JLabel lblEmergencyPhone = new JLabel("Emergency Phone");
		panel_1.add(lblEmergencyPhone, "cell 6 16");
		lblEmergencyPhone.setFont(Design.getBoldFont());
		lblEmergencyPhone.setForeground(Design.getDarkGreen());
		lblEmergencyPhone.setBorder(emptyLblRightBorder);
		
		txtEmergencyPhone = new JTextField();
		panel_1.add(txtEmergencyPhone, "cell 8 16,growx");
		txtEmergencyPhone.setColumns(30);
		
		JLabel lblCommencement = new JLabel("Commencement Date");
		panel_1.add(lblCommencement, "cell 6 19");
		lblCommencement.setFont(Design.getBoldFont());
		lblCommencement.setForeground(Design.getDarkGreen());
		lblCommencement.setBorder(emptyLblRightBorder);
		
		dateCommencement = new JDateChooser();
		panel_1.add(dateCommencement, "cell 8 19,growx");

		
		JPanel bottomPanel = (buildbottomPanel());
		//JPanel panel_2 = new JPanel();
		wrapPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.swapPanelCards.add(wrapPanel, "AddStudent1");
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
				frame.myCardLayout.show(frame.swapPanelCards, "homeScreen");
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
		
		JButton btnNext = new JButton("Next");
		btnNext.setFont(Design.getButtonFont());
		btnNext.setForeground(Design.getDarkGreen());
		btnNext.setBorder(BorderFactory.createCompoundBorder(greenLine, emptyBorder));
		btnNext.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				//if next is pushed, we need to save details from this page
				Date dob = dateDOB.getDate();
				Date commence = dateCommencement.getDate();
			
				//DOB and commencement date must be entered (otherwise it will cause a SQL error on next page)
				if(dob==null || commence==null){
					JOptionPane.showMessageDialog(frame, "You must enter a Date of Birth and a Commencement Date", "Error", JOptionPane.ERROR_MESSAGE);				
				}else{//if DOB and Commencement date entered then save all details entered on this page into strings for SQL statement on next/AddStudent2 page
					
					strFirstname = txtFirstname.getText();
					strSurname = txtSurname.getText();
					
					//make the format for the DOB string match SQL requirements
					strDOB = String.format("%1$tY-%1$tm-%1$td",dob);
					/**
					 * All parameters must now be saved and passed to next addStudent2 frame
					 */
					strAdd1 = txtAdd1.getText();
					strAdd2 = txtAdd2.getText();
					strAdd3 = txtAdd3.getText();
					strGuardian1Name = txtGuardian1Name.getText();
					strGuardian1Phone = txtGuardian1Phone.getText();
					
					strGuardian2Name = txtGuardian2Name.getText();
					strGuardian2Phone = txtGuardian2Phone.getText();
					strEmergency = txtEmergency.getText();
					strEmergencyPhone = txtEmergencyPhone.getText();
				
					strCommencementDate = String.format("%1$tY-%1$tm-%1$td",commence);
					
					//load and show next screen
					new AddStudent2(frame);
					frame.myCardLayout.show(frame.swapPanelCards, "AddStudent2");
				}
			}

		});
		
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		bottomPanel.setBackground(Design.getLightGreen());

		bottomPanel.add(btnBack);
		bottomPanel.add(btnCancel);
		bottomPanel.add(btnNext);
		
		return bottomPanel;
	}
	
}