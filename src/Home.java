import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Toolkit;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.util.*;
import java.io.*;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;

public class Home extends JFrame {
	
	// Allows your variables to be used in the program
	
	private JPanel contentPane;
	private int alignment;
	private static JTextField dateField;
	private static JTable table3;
	private static JTable table4;
	public static int N = 14;
	private static JTextField dayPeriod;
	
		
	// Main method executes everything
	
	public static void main(String[] args) throws Exception{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	public Home() throws Exception {
		setTitle("Market Analytics");
		
		String[] T = {"Inline.jpg","A.JPG","D.JPG","B.JPG","C.JPG"};
		
		// Creates the Home Frame
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FP(T[0])));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1550, 920);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Creates the title bar
		
		JLabel titlebar = new JLabel("");
		titlebar.setIcon(new ImageIcon(FP(T[1])));
		titlebar.setBounds(162, 11, 1201, 83);
		contentPane.add(titlebar);
		
		// Creates a text box to enter your date in
		
		dateField = new JTextField();
		dateField.setBorder(null);
		dateField.setHorizontalAlignment(SwingConstants.CENTER);
		dateField.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		dateField.setBackground(new Color(0, 191, 255));
		dateField.setBounds(524, 117, 175, 32);
		contentPane.add(dateField);
		dateField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Current Date");
		lblNewLabel.setForeground(new Color(0, 191, 255));
		lblNewLabel.setBounds(423, 122, 91, 23);
		contentPane.add(lblNewLabel);
		
		// Creates a text box to enter your look back period in

		dayPeriod = new JTextField();
		dayPeriod.setHorizontalAlignment(SwingConstants.CENTER);
		dayPeriod.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
		dayPeriod.setColumns(10);
		dayPeriod.setBorder(null);
		dayPeriod.setBackground(new Color(0, 191, 255));
		dayPeriod.setBounds(853, 117, 80, 32);
		contentPane.add(dayPeriod);
	
		JLabel lblLookbackPeriod = new JLabel("Lookback Period");
		lblLookbackPeriod.setForeground(new Color(0, 191, 255));
		lblLookbackPeriod.setBounds(740, 122, 103, 23);
		contentPane.add(lblLookbackPeriod);
		
		// Creates a table to display your cross-market correlation matrix
		
		DefaultTableModel model3 = new DefaultTableModel();
		
		table3 = new JTable(model3);
		table3.setShowGrid(false);
		table3.setRowSelectionAllowed(false);
		table3.setBorder(null);
		table3.setFont(new Font("Tahoma", Font.BOLD, 11));
		table3.setRowHeight(20);
 		table3.setOpaque(false);
 		table3.setBackground(new Color(0, 0, 0));
 		table3.setFillsViewportHeight(true);
 		table3.setFocusable(false);
 		table3.setRequestFocusEnabled(false);
 		table3.setSurrendersFocusOnKeystroke(true);
 		table3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		table3.setDropMode(DropMode.ON);
 		table3.setAutoscrolls(false);
 		table3.setSelectionForeground(Color.WHITE);
 		table3.setSize(1244, 330);
 		table3.setLocation(new Point(119, 214));
 		table3.setForeground(new Color(0, 255, 255));
 		table3.setAlignmentX(CENTER_ALIGNMENT);
 		getContentPane().add(table3);
		
		for(int i = 0; i < N + 1; i++){model3.addRow(new Object[]{" "});}
		for(int j = 0; j < N + 1; j++){model3.addColumn("");}
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(alignment);

        TableModel tableModel = table3.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
        {
            table3.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
		
		
		// Creates a table to display your cross-market optimized portfolio and value at risk
		
		DefaultTableModel model4 = new DefaultTableModel();
				
		table4 = new JTable(model4);
		table4.setFont(new Font("Tahoma", Font.BOLD, 11));
		table4.setRowSelectionAllowed(false);
		table4.setShowGrid(false);
		table4.setRowHeight(20);
 		table4.setOpaque(false);
 		table4.setBackground(new Color(0, 0, 0));
 		table4.setFillsViewportHeight(true);
 		table4.setFocusable(false);
 		table4.setRequestFocusEnabled(false);
 		table4.setSurrendersFocusOnKeystroke(true);
 		table4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		table4.setDropMode(DropMode.ON);
 		table4.setAutoscrolls(false);
 		table4.setSelectionForeground(Color.WHITE);
 		table4.setSize(1244, 119);
 		table4.setLocation(new Point(119, 557));
 		table4.setForeground(new Color(0, 255, 255));
 		getContentPane().add(table4);
		
 		
		for(int i = 0; i < 5; i++){model4.addRow(new Object[]{});}
		for(int j = 0; j < N + 1; j++){model4.addColumn("");}
		
		TableModel tableModel2 = table4.getModel();

        for (int columnIndex = 0; columnIndex < tableModel2.getColumnCount(); columnIndex++)
        {
            table4.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
		
		
		// Creates a refresh data button
		
		JButton refreshBut = new JButton("");
		refreshBut.setBorder(null);
		refreshBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					HomeX.MainX(dateField, dayPeriod, table3, table4, N);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		refreshBut.setIcon(new ImageIcon(FP(T[2])));
		refreshBut.setBounds(27, 755, 339, 73);
		contentPane.add(refreshBut);
		
		
				
	}
	
	
	// Fetches icon images and checks to see whether you are using a Windows or Mac
	
	public static String FP(String file){
		
		String X = System.getProperty("os.name");
		X = X.toLowerCase();
		X = X.replaceAll("[^a-z]", "");
		
		if(X.equals("windows")){
			file = "icon\\" + file;
		} else {
			file = "icon/" + file;
		}
		File open = new File(file);
		String zinc = open.getAbsolutePath();
		
		return zinc;
	}
}
