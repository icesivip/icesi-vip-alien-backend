package icesi.vip.alien.service.simplexAlgorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Jama.Matrix;
import icesi.vip.alien.model.linearProgramming.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Fernando Muñoz Morales
 */
public class SensivilityAnalysis {

	private ArrayList<Integer> posSlacks;
	int[] base;
	Solution solution;
	Model modeloS;
	Matrix finalM;
	Matrix shadowPrice;
	private String equationsFO;
	private String equationsConstraints;
	double[][] intervalsFO;
	double[][] intervalsConstraints;

	public SensivilityAnalysis(int[] base, String[] varN, Model modelo, Solution solution, Matrix shadowPrice,
			Matrix equalities, Matrix finalM) {
		this.base = base;
		modeloS = modelo;
		this.finalM = finalM;
		this.solution = solution;
		this.shadowPrice = shadowPrice;
		String[] varNames = varN;
		posSlacks = new ArrayList();
		for (int i = 0; i < varNames.length; i++) {
			if (varNames[i].startsWith("S") || varNames[i].startsWith("A"))
				posSlacks.add(i);
		}
	}

	public String getEquationsFO() {
		return equationsFO;
	}

	public double[][] getShadowPrice() {
		return Simplex.roundMatrix(shadowPrice.getArray());
	}

	public String getEquationsConstraints() {
		return equationsConstraints;
	}

	String buildEquationsConstraints() {
		StringBuilder equations = new StringBuilder("<html><body>");
		StringBuilder equationsToShow = new StringBuilder("<html><body>");
		try {
			int x = 0;
			for (int i = 1; i < finalM.getRowDimension(); i++) {
				if (i > 1) {
					equations.append("<br>");
					equationsToShow.append("<br>");
				}
				x = base[i - 1];
				Variable var = modeloS.getVariableAt(x);
				equations.append(var.getName() + " = ");
				equations.append(solution.getVariableValue(var) + " ");
				equationsToShow.append(var.getName() + " = ");
				equationsToShow.append(withoutDecimal(Simplex.roundDouble(solution.getVariableValue(var))) + " ");
				for (int j = 0; j < posSlacks.size(); j++) {
					double actual = finalM.getArray()[i][posSlacks.get(j)];
					if (actual < 0) {
						equations.append(actual + " D" + (j + 1) + " ");
						equationsToShow.append(
								"- " + withoutDecimal(-Simplex.roundDouble(actual)) + " D<sub>" + (j + 1) + "</sub> ");
					} else {
						equations.append("+" + actual + " D" + (j + 1) + " ");
						if (Simplex.roundDouble(actual) > 0)
							if (Simplex.roundDouble(actual) != 1)
								equationsToShow.append("+ " + withoutDecimal(Simplex.roundDouble(actual)) + " D<sub>"
										+ (j + 1) + "</sub> ");
							else
								equationsToShow.append("+ " + "D<sub>" + (j + 1) + "</sub> ");
					}
				}
				x++;
			}
//                }
			equations.append("</body></html>");
			equationsToShow.append("</body></html>");
		} catch (Exception ex) {
			Logger.getLogger(Simplex.class.getName()).log(Level.SEVERE, null, ex);
		}
		equationsConstraints = equationsToShow.toString();
		return equations.toString();
	}

	public double[][] getIntervalsDConstraints() {
		String toSolve = buildEquationsConstraints();
		String[] lines = toSolve.split("<br>");
		String[] line = lines[0].split(" ");
		double[][] doubMatrix = new double[lines.length][(line.length - 3) / 2];
		double[] constants = new double[lines.length];
		double[][] intervals = initializeIntervals(new double[constants.length][2]);
		// toma doubles necesarios
		for (int i = 0; i < lines.length; i++) {
			line = lines[i].split(" ");
			constants[i] = Double.parseDouble(line[2]);
			for (int j = 3; j < line.length; j += 2) {
				if (!line[j].startsWith("<"))
					doubMatrix[i][(j - 3) / 2] = Double.parseDouble(line[j]);
			}
		}
		Matrix aimp = new Matrix(doubMatrix);
		for (int i = 0; i < doubMatrix.length; i++) {
			for (int j = 0; j < doubMatrix[0].length; j++) {
				double aIngresar = constants[i] / doubMatrix[i][j] * -1;
				if (aIngresar > 0) {
					if (intervals[j][1] > aIngresar)
						intervals[j][1] = Simplex.roundDouble(aIngresar);
				} else {
					if (intervals[j][0] < aIngresar)
						intervals[j][0] = Simplex.roundDouble(aIngresar);
				}
			}
		}
		Matrix alv = new Matrix(intervals);
		toPositiveIntervals(intervals);
		intervalsConstraints = Simplex.roundMatrix(intervals);
		return intervals;
	}

	public double[][] getIntervalsDFO() {
		String toSolve = buildEquationsFO();
		String[] lines = toSolve.split("<br>");
		String[] line = lines[0].split(" ");
		double[][] doubMatrix = new double[lines.length][getInitialVars()];
		double[] constants = new double[lines.length];

		double[][] intervals = initializeIntervals(new double[getInitialVars()][2]);
		for (int i = 0; i < lines.length; i++) {
			line = lines[i].split(" ");
			constants[i] = Double.parseDouble(line[2]);
			for (int j = 3; j < line.length; j += 2) {
//                evita tomar un codigo html
				if (!line[j].startsWith("<")) {
//                Funciona para no más de 10 restricciones
					int posD = Integer.parseInt(line[j + 1].charAt(1) + "") - 1;
					doubMatrix[i][(posD)] = Double.parseDouble(line[j]);
				}
			}
		}
		Matrix aimp = new Matrix(doubMatrix);
		for (int i = 0; i < doubMatrix.length; i++) {
			for (int j = 0; j < doubMatrix[0].length; j++) {
				double aIngresar = constants[i] / doubMatrix[i][j] * -1;
				if (aIngresar > 0) {
					if (intervals[j][1] > aIngresar)
						intervals[j][1] = Simplex.roundDouble(aIngresar);
				} else {
					if (intervals[j][0] < aIngresar)
						intervals[j][0] = Simplex.roundDouble(aIngresar);
				}
			}
		}
		Matrix alv = new Matrix(intervals);
		toPositiveIntervals(intervals);
		intervalsFO = Simplex.roundMatrix(intervals);
		return intervals;
	}

	private void toPositiveIntervals(double[][] intervals) {
		for (int x = 0; x < intervals.length; x++) {
			for (int y = 0; y < intervals[x].length; y++) {
				if (y == 0)
					intervals[x][y] *= -1;
			}
		}
	}

	private int getInitialVars() {
		int count = 0;
		for (int i = 0; i < modeloS.getVariableCount(); i++) {
			if (modeloS.getVariableAt(i).getName().startsWith("X"))
				count++;
		}
		return count;
	}

	String buildEquationsFO() {
		StringBuilder equationsToShow = new StringBuilder("<html><body>");
		StringBuilder equations = new StringBuilder("<html><body>");
		try {
			int x = 0;
			for (int i = 1; i < modeloS.getVariableCount(); i++) {
				while (x < modeloS.getVariableCount() && (solution.getVariableValue(modeloS.getVariableAt(x)) != 0
						|| modeloS.getVariableAt(x).getName().startsWith("A"))) {
					x++;
				}
				if (x == modeloS.getVariableCount())
					break;

				if (i > 1) {
					equations.append("<br>");
					equationsToShow.append("<br>");
				}
				Variable var = modeloS.getVariableAt(x);
				equations.append(var.getName() + " = ");
				equationsToShow.append(var.getName() + " = ");
				for (int j = 0; j < finalM.getRowDimension(); j++) {
					if (j == 0) {
						String constant;
						constant = finalM.get(j, x) + " ";
						equations.append(constant);
						equationsToShow.append(withoutDecimal(Simplex.roundDouble(finalM.get(j, x))) + " ");
					} else if (modeloS.getVariableAt(base[j - 1]).getName().startsWith("X")) {
						if (finalM.get(j, x) < 0) {
							equations.append(finalM.get(j, x) + " D"
									+ (modeloS.getVariableAt(base[j - 1]).getName().charAt(1)) + " ");
							equationsToShow.append("- " + withoutDecimal(-Simplex.roundDouble(finalM.get(j, x)))
									+ " D<sub>" + (modeloS.getVariableAt(base[j - 1]).getName().charAt(1)) + "</sub> ");
						} else {
							equations.append("+" + finalM.get(j, x) + " D"
									+ (modeloS.getVariableAt(base[j - 1]).getName().charAt(1)) + " ");
							if (Simplex.roundDouble(finalM.get(j, x)) > 0)
								if (Simplex.roundDouble(finalM.get(j, x)) != 1)
									equationsToShow.append("+ " + withoutDecimal(Simplex.roundDouble(finalM.get(j, x)))
											+ " D<sub>" + (modeloS.getVariableAt(base[j - 1]).getName().charAt(1))
											+ "</sub> ");
								else
									equationsToShow.append("+ " + "D<sub>"
											+ (modeloS.getVariableAt(base[j - 1]).getName().charAt(1)) + "</sub> ");
						}
					}
				}
				if (var.getName().startsWith("X")) {
					equations.append("-1 D" + (x + 1) + " ");
					equationsToShow.append("- D" + (x + 1) + " ");
				}
				x++;
			}
			equations.append("</body></html>");
			equationsToShow.append("</body></html>");
		} catch (Exception ex) {
			Logger.getLogger(Simplex.class.getName()).log(Level.SEVERE, null, ex);
		}
		equationsFO = equationsToShow.toString();
		return equations.toString();
	}

	private double[][] initializeIntervals(double[][] intervals) {
		for (int x = 0; x < intervals.length; x++) {
			for (int y = 0; y < intervals[x].length; y++) {
				if (y == 0)
					intervals[x][y] = -Double.MAX_VALUE;
				else
					intervals[x][y] = Double.MAX_VALUE;
			}
		}
		return intervals;
	}

	private String withoutDecimal(double d) {
		DecimalFormat df = new DecimalFormat("#.###");
		df.setDecimalSeparatorAlwaysShown(false);
		return df.format(d);
	}
}