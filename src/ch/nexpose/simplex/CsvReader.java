package ch.nexpose.simplex;

import java.util.Arrays;

import ch.nexpose.simplex.types.ConstraintType;
import ch.nexpose.simplex.types.OptimisationType;

/**
 * CSV data reader.
 * 
 * @author polakm
 */
public class CsvReader implements SimplexProblem {
    private OptimisationType optimisationType;
    private SimplexCoefficient[] coefficients;
    private SimplexConstraint[] constraints;

    public static SimplexProblem parse(String aData) {
      
      CsvReader result = new CsvReader();
      result._parse(aData);
      return result;
    }
    
    private CsvReader()
    {
    }

    private static String _capitalize(String name) {
    if (name == null || name.length() == 0) {
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
     }
    
    private void _parse(String data)
    {
        int j = 0;
        String[] values = Arrays.stream(data.replace("\n", ";").split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        //read amount
        int amountVariables = Integer.parseInt(values[j++]);
        int amountConstraints = Integer.parseInt(values[j++]);

        //init arrays
        coefficients = new SimplexCoefficient[amountVariables+1];
        constraints = new SimplexConstraint[amountConstraints];

        //read type
        optimisationType = OptimisationType.valueOf(_capitalize(values[j++]));

        //read coefficients + d value
        for(int i = 0; i < amountVariables + 1; i++)
        {
            coefficients[i] = new SimplexCoefficient(Double.parseDouble(values[j++]));
        }

        //read notnegative type of coefficients
        for(int i = 0; i < amountVariables; i++)
        {
            coefficients[i].setNotNegative(Boolean.parseBoolean(values[j++]));
        }

        //read constraints
        for(int i = 0; i < amountConstraints; i++)
        {
            SimplexConstraint c = new SimplexConstraint(amountVariables);

            switch (values[j++])
            {
                case "<=":
                    c.setConstraintType(ConstraintType.LessThanEquals);
                    break;

                case "=":
                    c.setConstraintType(ConstraintType.Equals);
                    break;

                case ">=":
                    c.setConstraintType(ConstraintType.GreaterThanEquals);
                    break;
            }

            //read coefficients b & c
            for(int k = 0; k < amountVariables + 1; k++)
            {
                c.getCoefficients()[k] = (Double.parseDouble(values[j++]));
            }

            constraints[i] = c;
        }
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

}
