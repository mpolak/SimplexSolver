package ch.nexpose.simplex;

import java.util.ArrayList;

import ch.nexpose.simplex.types.ConstraintType;
import ch.nexpose.simplex.types.OptimisationType;

/**
 * Data structure to support solving.
 * 
 * Created by cansik on 22/11/15, mpolak modified
 */
class SimplexSolverDataHolder {
    private OptimisationType optimisationType;
    private SimplexCoefficient[] coefficients;
    private SimplexConstraint[] constraints;

    private SimplexSolverDataHolder(SimplexProblem aSimplexProblem)
    {
      optimisationType = aSimplexProblem.getOptimisationType();
      coefficients = aSimplexProblem.getCoefficients();
      constraints = aSimplexProblem.getConstraints();
    }
    
    static SimplexSolverDataHolder create(SimplexProblem aSimplexProblem) {
      SimplexSolverDataHolder result = new SimplexSolverDataHolder(aSimplexProblem);
      return result;
    }

    public OptimisationType getOptimisationType() {
        return optimisationType;
    }

    public SimplexCoefficient[] getCoefficients() {
        return coefficients;
    }

    public SimplexConstraint[] getConstraints() {
        return constraints;
    }

    public double[] getSlackVariables()
    {
        double[] slackVars = new double[coefficients.length];

        for(int i = 0; i < coefficients.length; i++)
            slackVars[i] = coefficients[i].getValue();

        return slackVars;
    }

    public void convertInequation()
    {
        for(int i = 0; i < coefficients.length; i++)
        {
            coefficients[i].setValue(coefficients[i].getValue()*-1);
        }
    }

    public void convertEqualsConstraints()
    {
        ArrayList<SimplexConstraint> cons = new ArrayList<>();

        for(SimplexConstraint c : this.constraints)
        {
            if(c.getConstraintType() != ConstraintType.Equals) {
                cons.add(c);
                continue;
            }

            //is equals
            cons.add(new SimplexConstraint(c, ConstraintType.LessThanEquals));
            cons.add(new SimplexConstraint(c, ConstraintType.GreaterThanEquals));
        }

        SimplexConstraint[] s = new SimplexConstraint[cons.size()];
        this.constraints = cons.toArray(s);
    }
}
