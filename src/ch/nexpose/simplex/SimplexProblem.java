package ch.nexpose.simplex;

import ch.nexpose.simplex.types.OptimisationType;

/**
 * Interface to define a problem.
 * 
 * Created by cansik on 22/11/15, polakm.
 */
public interface SimplexProblem {

  public OptimisationType getOptimisationType();
  public SimplexCoefficient[] getCoefficients();
  public SimplexConstraint[] getConstraints();
}
