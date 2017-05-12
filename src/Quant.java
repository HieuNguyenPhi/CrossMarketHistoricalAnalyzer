import java.lang.reflect.Array;
import java.math.*;
import java.util.Arrays;
import java.util.Collections;
import java.text.*;

public class Quant {
	
	public static DecimalFormat df = new DecimalFormat("0.000%");
	
	// Calculates your rate of returns for each time step
	
	public static double[][] Normalize(double[][] data){
		
		int n = data.length;
		int m = data[0].length;
		
		double A = 0, B = 0;
		double[][] ror = new double[n][m];
		
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
				if(j > 1){
					ror[i][j-1] = (data[i][j]/data[i][j-1])-1;
				}
			}
		}
		
		
		return ror;
	}
	
	// Computes your mean and standard deviation for your data
	
	public static double[][] MeanStd(double[][] ror){
		
		double[][] rr = new double[ror.length][2];
		
		for(int i = 0; i < ror.length; i++){
			for(int j = 0; j < ror[i].length; j++){
				rr[i][0] += ror[i][j];
			}
			rr[i][0] /= (double) ror[0].length;
		}
		
		for(int i = 0; i < ror.length; i++){
			for(int j = 0; j < ror[i].length; j++){
				rr[i][1] += Math.pow(ror[i][j] - rr[i][0],2);
			}
			rr[i][1] /= (double) ror[0].length;
			rr[i][1] = Math.sqrt(rr[i][1]);
		}
		
				
		return rr;
	}
	
	// Computes your correlation matrix 
	
	public static double[][] Correlation(double[][] ror, double[][] rr){
		
		int n = ror.length;
		int m = ror[0].length;
		
		double[][] corr = new double[n][n];
		double[][] cov = new double[n][n];
		double[][] var = new double[n][n];
		
		double[][] A = new double[n][m];
		double[][] B = new double[m][n];
		
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
				A[i][j] = ror[i][j] - rr[i][0];
				B[j][i] = A[i][j];
			}
		}
		
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				for(int k = 0; k < m; k++){
					cov[i][j] += A[i][k] * B[k][j];
				}
				cov[i][j] /= (double) m;
			}
		}
		
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				var[i][j] = rr[i][1] * rr[j][1];
				corr[i][j] = cov[i][j] / var[i][j];
			}
		}
		
		return corr;
	}
	
	
	// Computes your sharpe ratios for your data
	
	public static double[] Sharpe(double[][] rr){
		
		double[] sharpe = new double[rr.length];
		
		for(int i = 0; i < rr.length; i++){
			sharpe[i] = rr[i][0] / rr[i][1];
		}
		
		return sharpe;
	}
	
	// This contains your sigmoid function
	
	public static double Sigmoid(double X, int d){
		
		double y = 1 / (1 + Math.exp(-X));
		
		if(d == 1){
			y *= (1 - y);
		}
		
		return y;
	}
	
	// This neural net will figure out how to allocate your money
	
	public static double[] Neural(double[] Sharpe){
		
		double[] w = new double[Sharpe.length];
		
		for(int i = 0; i < Sharpe.length; i++){
			w[i] = 1.0;
		}
		
		double O = 0, W = 0;
		double e1 = 0, e2 = 0, d1 = 0, d2 = 0;
		
		double b1 = -1;
		
		double U = Sigmoid(1,0);
		double V = 1.0;
		
		double err = 0.0001;
		
		while(true){
			
			O = 0;
			W = 0;
			
			for(int i = 0; i < Sharpe.length; i++){
				if(w[i] < 0){
					w[i] = 0;
				}
				O += w[i] * Sharpe[i];
				W += w[i];
			}
			
			O += b1;
			O = Sigmoid(O,0);
			W = Sigmoid(W,0);
			
			e1 = V - O;
			e2 = U - W;
			
			d1 = e1 * Sigmoid(O,1);
			d2 = e2 * Sigmoid(W,1);
			
			if(Math.abs(e1) > err && Math.abs(e2) > err){
				for(int i = 0; i < Sharpe.length; i++){
					w[i] += d1 * Sharpe[i];
					w[i] += d2;
				}

				b1 += (e1 + e2);
				
			} else {
				return w;
			}
			
		}
		
	}
	
	// Computes your value at risk for each market
	
	public static double[] VAR(double[][] ror, int N) throws Exception{
		
		double[][] result = new double[ror.length][ror[0].length];
		double[] VAR = new double[ror.length];
		double sV = 0.95 * (double) N;
		double lV = 0.99 * (double) N;
		
		int v1 = (int) Math.round(sV);
		int v2 = (int) Math.round(lV);
		
		for(int i = 0; i < ror.length; i++){
			Arrays.sort(ror[i]);
		}
		
		sV = Math.round(sV);
		lV = Math.round(lV);
		
		int a = ror[0].length - (int) sV;
		int b = ror[0].length - (int) lV;
		
		for(int i = 0; i < ror.length; i++){
			VAR[i] = ror[i][a-1];
		}
		
		return VAR;
	}
	
}
