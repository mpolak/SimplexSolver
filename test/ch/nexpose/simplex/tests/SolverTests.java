package ch.nexpose.simplex.tests;

/**
 * Created by cansik on 29/11/15.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import ch.nexpose.simplex.CsvReader;
import ch.nexpose.simplex.SimplexProblem;
import ch.nexpose.simplex.SimplexSolver;

public class SolverTests {

    private final double EPSILON = 1e-15;

    private SimplexSolver solver;

    public String readAllText(String filename)
    {
        String p = "/Users/cansik/git/fhnw/efalg/SimplexSolver/LP_problems/";
        Path path = FileSystems.getDefault().getPath(p + filename + ".csv");

        String contents = "Not Data";
        try {
            contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    @Before
    public void init()
    {
        solver = new SimplexSolver();
    }

    @Test
    public void basicExampleTest() {
        SimplexProblem p = CsvReader.parse(readAllText("BasicExample"));

        double result = solver.solve(p);
        double expected = 180;

        assertEquals(expected, result, EPSILON);
    }

    @Test
    public void infiniteSolutionsTest() {
        SimplexProblem p = CsvReader.parse(readAllText("InfiniteSolutions"));

        double result = solver.solve(p);
        assertTrue(Double.isInfinite(result));
    }

    @Test
    public void landWirtschaftTest() {
        SimplexProblem p = CsvReader.parse(readAllText("Landwirtschaft"));

        double result = solver.solve(p);
        double expected = 5400;

        assertEquals(expected, result, EPSILON);
    }

    @Test
    public void negSchlupfTest() {
        SimplexProblem p = CsvReader.parse(readAllText("NegSchlupf"));

        double result = solver.solve(p);
        double expected = 86;

        assertEquals(expected, result, EPSILON);
    }

    @Test
    public void nichtNegativiteatTest1() {
        SimplexProblem p = CsvReader.parse(readAllText("NichtNegativitaet_1"));

        Double result = solver.solve(p);
        Double expected = Double.NaN;

        assertEquals(expected, result);
    }

    @Test
    public void nichtNegativiteatTest2() {
        SimplexProblem p = CsvReader.parse(readAllText("NichtNegativitaet_2"));

        Double result = solver.solve(p);
        double expected = 10;

        assertEquals(expected, result, EPSILON);
    }

    @Test
    public void zweiSeafteTest() {
        SimplexProblem p = CsvReader.parse(readAllText("ZweiSaefte"));

        double result = solver.solve(p);
        double expected = 506.66666666666663;

        assertEquals(expected, result, EPSILON);
    }

    @Test
    public void markusTest() {
        SimplexProblem p = CsvReader.parse(readAllText("MarkusTest"));

        double result = solver.solve(p);
        double expected = 110;

        assertEquals(expected, result, EPSILON);
    }

    @Test
    public void minimizeTest() {
        SimplexProblem p = CsvReader.parse(readAllText("MinimizeTest"));

        double result = solver.solve(p);
        double expected = 106.4;

        assertEquals(expected, result, EPSILON);
    }

    @Test
    public void eisenBahnTest() {
        SimplexProblem p = CsvReader.parse(readAllText("Eisenbahn"));

        double result = solver.solve(p);
        double expected = 191;

        assertEquals(expected, result, EPSILON);
    }
}
