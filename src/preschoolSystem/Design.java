package preschoolSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Design {
	
	/**************** Image ************************/
	public static ImageIcon getMonkeyImage(){
		return new ImageIcon("images/monkey.png");
	}
	
	
	/**************** Fonts  ************************/
	public static Font getBoldFont(){
		return new Font("Comic Sans MS", Font.BOLD, 18);
	}
	
	public static Font getSmallTitleFont(){
		return new Font("Cooper Black", Font.PLAIN, 24);
	}
	public static Font getLargeTitleFont(){
		return new Font("Cooper Black", Font.PLAIN, 30);
	}
	
	public static Font getButtonFont(){
		return new Font("Lucida Sans", Font.BOLD, 16);
	}
	
	
	/**************** Colors ************************/
	
	public static Color getDarkGreen(){
		return new Color(0,153,0);
	}
	
	public static Color getLightGreen(){
		return new Color(204,255,204);
	}
	
	/**************** Top Panel*****************************/
	public static JPanel buildTopPanel(String title) {
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Design.getLightGreen());
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(Design.getLargeTitleFont());
		
		titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 30, 10, 10)); //empty border (top, left, bottom, right)
		titleLabel.setForeground(Design.getDarkGreen());
		
		topPanel.add(titleLabel);

		topPanel.setBorder(BorderFactory.createMatteBorder(0,0,3,0,Design.getDarkGreen()));
	
		return topPanel;
	}
	
	/**************** StudentName*****************************/
	public static String getStudentName(String studentID) {
		
		String firstName = null;
		String surname = null;
		Sqlengine dbEngine = new Sqlengine("root", "root");
		
		dbEngine.connect();
		
		Connection conn = dbEngine.getConn();

		PreparedStatement preparedStatement;
		try {
			
			preparedStatement = conn.prepareStatement("SELECT firstName, surname FROM student WHERE StudentID = ?");
			preparedStatement.setString(1, studentID);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			firstName = rs.getString("firstName");
			surname = rs.getString("surname");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return firstName+" "+surname;
	}
}
