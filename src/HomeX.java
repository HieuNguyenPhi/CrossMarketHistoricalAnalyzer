import java.net.*;
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.awt.Component;
import java.io.*;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class HomeX {
	
	public static DecimalFormat df = new DecimalFormat("0.000%");
	
	public static void MainX(JTextField data, JTextField day, JTable X, JTable Y, int N) throws Exception{
		
		System.out.println("Running");
		
		String[] tick = {"SPY","GLD","OIL","SJNK","GSG","XLK","XLE","XLV","XLY","XLI","IBB","IYR","XLB"};
		
		String[] names = {"S&P500","Gold","Oil","HYBond","Comdts.","Tech","Energy","Healthcare","Discretionary","Staples","BioTech","RealEstate","Materials","Bitcoin"};
		
		String dateY = DateFix(data);
		
		String K;

		int look = Integer.parseInt(day.getText());
		int n = look - 1;
		int m = tick.length;
		
		String[][] B = new String[m][400];
		
		// Fetches your market sector data
		
		for(int i = 0; i < m; i++){
			K = WebX(tick[i],dateY,1);

			B[i] = Historical(K);

		}
		
		// Fetches your Bitcoin data
		
		K = WebX("AAPL",dateY,2);
		String[] C = BitcoinP(K);
		
		/*
		
		double[][] dataX = JoinData(B,C,n,m);
		double[][] ror = Quant.Normalize(dataX);
		double[][] rr = Quant.MeanStd(ror);
		double[][] corr = Quant.Correlation(ror, rr);
		double[] sharpe = Quant.Sharpe(rr);
		double[] w = Quant.Neural(sharpe);
		double[] varX = Quant.VAR(ror, n);
		
		CorrTable(names,corr,X);
		WeightTable(names,w,varX, rr, Y);
		Excel(names, corr, rr, varX, w, dateY, day.getText());
		
		System.out.println("Finished");
		
		//*/
		
	}
	
	// Prints out your correlation matrix
	
	public static void CorrTable(String[] names, double[][] corr, JTable X){
		
		for(int i = 0; i < corr.length; i++){
			X.setValueAt(names[i], 0, 1 + i);
			X.setValueAt(names[i], 1 + i, 0);
		}
		
		int A = corr.length;
		
		
		
		for(int i = 0; i < corr.length; i++){
			for(int j = i + 1; j < A; j++){

				X.setValueAt(df.format(corr[i][j]), 1 + i, 1 + j);
			}
		}
		
	}
	
	// Prints out your optimized weight table
	
	public static void WeightTable(String[] names, double[] weights, double[] varX, double[][] rr, JTable Y){
		
		Y.setValueAt("Allocation", 1, 0);
		Y.setValueAt("VaR(95%)", 2, 0);
		Y.setValueAt("Volatility", 3, 0);
		Y.setValueAt("Avg.Return", 4, 0);
		
		for(int i = 0; i < names.length; i++){
			Y.setValueAt(names[i], 0, 1 + i);
			Y.setValueAt(df.format(weights[i]), 1, 1 + i);
			Y.setValueAt(df.format(varX[i]), 2, 1 + i);
			Y.setValueAt(df.format(rr[i][1]), 3, 1 + i);
			Y.setValueAt(df.format(rr[i][0]), 4, 1 + i);
		}
		
		
	}
	
	// Outputs your result to an excel file
	
	public static void Excel(String[] names, double[][] corr, double[][] rr, double[] varX, double[] w, String date, String p) throws Exception{
		
		date = date.replace("%2C", "");
		
		String file = "results\\" + date + " with a " + p + " day lookback.csv";
		
		PrintWriter x = new PrintWriter(new File(file));
		
		x.write(",");
		
		for(int i = 0; i < w.length; i++){
			x.write(names[i] + ",");
		}
		
		x.write("\n");
		
		for(int i = 0; i < w.length; i++){
			x.write(names[i] + ",");
			for(int j = 0;  j < w.length; j++){
				x.write(corr[i][j] + ",");
			}
			x.write("\n");
		}
		
		x.write("\n\n,");
		
		for(int i = 0; i < w.length; i++){
			x.write(names[i] + ",");
		}
		
		x.write("\nAllocation,");
		
		for(int i = 0; i < w.length; i++){
			x.write(w[i] + ",");
		}
		
		x.write("\nVaR(95%),");
		
		for(int i = 0; i < w.length; i++){
			x.write(varX[i] + ",");
		}
		
		x.write("\nVolatility,");
		
		for(int i = 0; i < w.length; i++){
			x.write(rr[i][1] + ",");
		}
		
		x.write("\nAvg.Return,");
		
		for(int i = 0; i < w.length; i++){
			x.write(rr[i][0] + ",");
		}
		
		x.flush();
		x.close();
	}
	
		
	// Joins your data together
	
	public static double[][] JoinData(String[][] B, String[] C, int n, int m){
		
		double[][] data = new double[m+1][n];  // [Ticker][LookPeriod]
		
		for(int i = 0; i < m; i++){
			for(int j = 0; j < n; j++){
				data[i][j] = Double.parseDouble(B[i][j]);
			}
		}
		
		for(int i = 0; i < n; i++){
			data[m][i] = Double.parseDouble(C[i]);
		}
		
		return data;
	}
	
	// Parses your bitcoin data
	
	public static String[] BitcoinP(String data){
		
		data = data.replace("\"", "");
		String[] A = data.split("data:");
		String[] B = A[1].split("collapse");
		
		data = B[0];
		data = data.replace("]],","");
		data = data.replace("[","");
		
		String[] hold = data.split("],");
		String[][] u = new String[hold.length][8];
		String[] result = new String[hold.length];
		for(int i = 0; i < hold.length; i++){
			u[i] = hold[i].split(",");
			result[i] = u[i][3];
		}
		
		return result;
	}
	
	
	// Parses your historical data
	
	public static String[] Historical(String data){
		
		String[] hold = data.split("],");
		String[][] temp = new String[hold.length][0];
		String[] result = new String[hold.length];
		
		for(int i = 0; i < hold.length; i++){
			temp[i] = hold[i].split(",");
			result[i] = temp[i][4];
			System.out.println(result[i]);
		}
		
		return result;
		
	}
	
	// Converts your date to match shit
	
	public static String DateFix(JTextField data){
		
		String u = data.getText();
		
		String v = u.substring(0, u.length() - 5);
		String[] p = u.split("-");
		String w = p[2];
		
		u = v + "-%2C" + w;
		
		return u;
	}
	
	
	// Connects your program to the internet to fetch financial data
	
	public static String WebX(String stock, String date, int i) throws Exception{
		
		String url = "";

		if(i == 1){
			url = "http://www.google.com/finance/historical?q=NASDAQ:" + stock + "&startdate=Mar+01%2C+2016&enddate=" + date + "&output=csv";;
		}
		if(i == 2){
			url = "https://www.quandl.com/api/v3/datasets/BITSTAMP/USD.json?api_key=znQsdvQsGK8zLycxLihb";
		}
				
		URL a = new URL(url);
		URLConnection b = a.openConnection();
		BufferedReader c = new BufferedReader(new InputStreamReader(b.getInputStream()));
		
		String zinc = "";
		
		
		if(i == 0 || i == 2){
			return c.readLine();
		} else {
			while(c.readLine() != null){
				zinc += c.readLine() + "],";
			}
			zinc = zinc.substring(0,zinc.length()-2);
			zinc = zinc.replace("null","");
			return zinc;
		}
	}
	
	
	
}
