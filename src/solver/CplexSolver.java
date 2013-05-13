package solver;

/* --------------------------------------------------------------------------
 * File: MIPex1.java
 * Version 12.2
 * --------------------------------------------------------------------------
 * Licensed Materials - Property of IBM
 * 5725-A06 5725-A29 5724-Y48 5724-Y49 5724-Y54 5724-Y55
 * Copyright IBM Corporation 2001, 2010. All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with
 * IBM Corp.
 * --------------------------------------------------------------------------
 *
 * MIPex1.java - Entering and optimizing a MIP problem
 */

/*import ilog.concert.*;
 import ilog.cplex.*;


 public class MIPex1 {
 public static void main(String[] args) {
 try {
 IloCplex cplex = new IloCplex();

 IloNumVar[][] var = new IloNumVar[1][];
 IloRange[][]  rng = new IloRange[1][];

 populateByRow(cplex, var, rng);

 if ( cplex.solve() ) {
 double[] x     = cplex.getValues(var[0]);
 double[] slack = cplex.getSlacks(rng[0]);

 System.out.println("Solution status = " + cplex.getStatus());
 System.out.println("Solution value  = " + cplex.getObjValue());

 for (int j = 0; j < x.length; ++j) {
 System.out.println("Variable " + j + ": Value = " + x[j]);
 }

 for (int i = 0; i < slack.length; ++i) {
 System.out.println("Constraint " + i + ": Slack = " + slack[i]);
 }
 }

 cplex.exportModel("mipex1.lp");
 cplex.end();
 }
 catch (IloException e) {
 System.err.println("Concert exception caught '" + e + "' caught");
 }
 }


 static void populateByRow (IloMPModeler  model,
 IloNumVar[][] var,
 IloRange[][]  rng) throws IloException {
 // First define the variables, three continuous and one integer
 double[]        xlb = {0.0, 0.0, 0.0, 2.0};
 double[]        xub = {40.0, Double.MAX_VALUE, Double.MAX_VALUE, 3.0};
 IloNumVarType[] xt  = {IloNumVarType.Float, IloNumVarType.Float,
 IloNumVarType.Float, IloNumVarType.Int};
 IloNumVar[]     x  = model.numVarArray(4, xlb, xub, xt);
 var[0] = x;

 // Objective Function:  maximize x0 + 2*x1 + 3*x2 + x3 
 double[] objvals = {1.0, 2.0, 3.0, 1.0};
 model.addMaximize(model.scalProd(x, objvals));

 // Three constraints
 rng[0] = new IloRange[3];
 // - x0 + x1 + x2 + 10*x3 <= 20
 rng[0][0] = model.addLe(model.sum(model.prod(-1.0, x[0]),
 model.prod( 1.0, x[1]),
 model.prod( 1.0, x[2]),
 model.prod(10.0, x[3])), 20.0);
 // x0 - 3*x1 + x2 <= 30
 rng[0][1] = model.addLe(model.sum(model.prod( 1.0, x[0]),
 model.prod(-3.0, x[1]),
 model.prod( 1.0, x[2])), 30.0);
 // x1 - 3.5*x3 = 0 
 rng[0][2] = model.addEq(model.sum(model.prod( 1.0, x[1]),
 model.prod(-3.5, x[3])), 0.0);
 for (int i=0; i<5;i++)
 {
 System.out.println(i);
 }
 }
 }*/

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
import ilog.concert.*;
import ilog.cplex.*;

public class CplexSolver {
	public int[] order;
	public CplexSolver(int nodeNum, double[][] distance) {
		try {
			IloCplex cplex = new IloCplex();

			IloNumVar[][] var = new IloNumVar[1][];
			IloRange[][] rng = new IloRange[1][];
			
			double[] objvals = new double[nodeNum * nodeNum + nodeNum];
			
			for(int i = 0; i < nodeNum; i++) {
				for(int j = 0; j < nodeNum; j++) {
					objvals[nodeNum * i + j] = distance[i][j];
				}
				objvals[nodeNum * nodeNum + i] = 0;
			}

			populateByRow(cplex, var, rng, nodeNum, objvals);

			if (cplex.solve()) {
				double[] x = cplex.getValues(var[0]);
				double[] slack = cplex.getSlacks(rng[0]);
				
				System.out.println("Solution status = " + cplex.getStatus());
				System.out.println("Solution value  = " +
				cplex.getObjValue());
				
				for (int j = 0; j < x.length; ++j) {
				System.out.println("Variable " + j + ": Value = " + x[j]); }
				for (int i = 0; i < slack.length; ++i) {
				System.out.println("Constraint " + i + ": Slack = " +
				slack[i]); }
				//int nodeNum = 5;
				//int[] order;
				order = new int[nodeNum];
				order[0] = 0;
				for (int i = 0; i < nodeNum - 1; i++) {
					for (int j = nodeNum * nodeNum; j < nodeNum * nodeNum + nodeNum; j++) {
						if (Math.round(x[j]) == i) {
							order[i + 1] = j - nodeNum * nodeNum;
						}
					}
				}
				/*
				for (int i = 0; i < nodeNum; i++) {
					System.out.println(order[i]);
				}
				*/
			}

			cplex.exportModel("mipex1.lp");
			cplex.end();
		} catch (IloException e) {
			System.err.println("Concert exception caught '" + e + "' caught");
		}
	}

	static void populateByRow(IloCplex model, IloNumVar[][] var,
			IloRange[][] rng, int nodeNum, double[] objvals) throws IloException {
		// First define the variables, three continuous and one integer
		int Var_Count = nodeNum * nodeNum + nodeNum;
		double[] xlb, xub;
		IloNumVarType[] xt;
		xlb = new double[Var_Count];
		xub = new double[Var_Count];
		xt = new IloNumVarType[Var_Count];
		int i, j;
		IloNumExpr temp = null;

		// Initialize
		for (i = 0; i < Var_Count; i++) {
			xlb[i] = 0.0;
			xt[i] = IloNumVarType.Int;
		}

		// The first N^2 variables with upper bound 1
		for (i = 0; i < nodeNum * nodeNum; i++) {
			xub[i] = 1.0;
		}
		// Modification: X_i,i variable with upper bound 0
		for (i = 0; i < nodeNum; i++) {
			xub[nodeNum * i + i] = 0.0;
		}

		// The last N variables with upper bound N-2
		for (i = nodeNum * nodeNum; i < Var_Count; i++) {
			xub[i] = nodeNum - 2;
		}

		// Build variables structure
		IloNumVar[] x = model.numVarArray(Var_Count, xlb, xub, xt);
		var[0] = x;

		// Objective function
		model.addMinimize(model.scalProd(x, objvals));

		// Allocate constraints space
		rng[0] = new IloRange[nodeNum * nodeNum - nodeNum + 2];

		// Out degree constraints
		for (i = 0; i < nodeNum; i++) {
			temp = model.prod(1, x[nodeNum * i]);
			for (j = 1; j < nodeNum; j++) {
				temp = model.sum(temp, model.prod(1, x[nodeNum * i + j]));
			}
			rng[0][i] = model.addEq(temp, 1);
		}

		// In degree constraints
		for (i = 0; i < nodeNum; i++) {
			temp = model.prod(1, x[i]);
			for (j = 1; j < nodeNum; j++) {
				temp = model.sum(temp, model.prod(1, x[i + nodeNum * j]));
			}
			rng[0][i + nodeNum] = model.addEq(temp, 1);
		}

		// Eliminate inner circle
		int count = 2 * nodeNum;
		for (i = 1; i < nodeNum; i++) {
			for (j = 1; j < nodeNum; j++) {
				if (j != i) {
					rng[0][count] = model.addLe(model.sum(
							model.prod(1, x[nodeNum * nodeNum + i]),
							model.prod(-1, x[nodeNum * nodeNum + j]),
							model.prod(nodeNum, x[nodeNum * i + j])), nodeNum - 1);
					count++;
				} else
					continue;
			}
		}
		/*
		temp = model.prod(1, x[nodeNum * nodeNum]);
		for (j = 1; j < nodeNum; j++) {
			temp = model.sum(temp, model.prod(1, x[nodeNum * nodeNum + j]));
		}
		rng[0][count] = model.addEq(temp, (nodeNum-2)*(nodeNum-1)/2);
		*/
	}
}
