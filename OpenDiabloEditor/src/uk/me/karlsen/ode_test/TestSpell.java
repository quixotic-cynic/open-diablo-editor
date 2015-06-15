package uk.me.karlsen.ode_test;

import java.util.Arrays;

import org.junit.Test;

import junit.framework.TestCase;
import uk.me.karlsen.ode.ReaderWriter;
import uk.me.karlsen.ode.SpellsStore;
import uk.me.karlsen.ode.TomeOfKnowledge;

public class TestSpell extends TestCase {

	protected byte[][] origSpellBytes = {
		{1, 6, 0, 0, 80, 45, 74, 0, 80, 45, 74, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 76, 1, 0, 0, 1, 3, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, -24, 3, 0, 0, 50, 0, 0, 0},
		{2, 5, 2, 0, 72, 45, 74, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 17, 0, 0, 0, 82, 37, 0, 0, 3, 1, 0, 0, 20, 0, 0, 0, 40, 0, 0, 0, -24, 3, 0, 0, 50, 0, 0, 0},
		{3, 10, 1, 0, 56, -13, 72, 0, 0, 0, 0, 0, 4, 0, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 78, 7, 0, 0, 1, 6, 0, 0, 20, 0, 0, 0, 60, 0, 0, 0, -72, 11, 0, 0, -106, 0, 0, 0},
		{4, 30, 1, 0, 64, 45, 74, 0, 0, 0, 0, 0, 5, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 0, 0, 0, 78, 11, 12, 0, 2, 16, 0, 0, 20, 0, 0, 0, 40, 0, 0, 0, 76, 29, 0, 0, -12, 1, 0, 0},
		{5, 13, 2, 0, 52, 45, 74, 0, 52, 45, 74, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 0, 0, 0, 23, 0, 0, 0, 80, 40, 0, 0, 2, 1, 0, 0, 8, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0},
		{6, 28, 0, 0, 40, 45, 74, 0, 0, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 76, 38, 0, 0, 2, 16, 0, 0, 8, 0, 0, 0, 16, 0, 0, 0, 112, 23, 0, 0, -112, 1, 0, 0},
		{7, 35, 2, 0, 112, 66, 72, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 80, 10, 0, 0, 3, 18, 0, 0, 8, 0, 0, 0, 12, 0, 0, 0, -72, 11, 0, 0, -56, 0, 0, 0},
		{8, 60, 2, 0, 28, 45, 74, 0, 0, 0, 0, 0, 6, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 51, 0, 0, 0, 76, 30, 0, 0, 3, 40, 0, 0, 8, 0, 0, 0, 16, 0, 0, 0, -32, 46, 0, 0, 32, 3, 0, 0},
		{9, 40, 2, 0, 16, 45, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 82, 39, 0, 0, 5, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 88, 2, 0, 0},
		{10, 12, 2, 0, 8, 45, 74, 0, 0, 0, 0, 0, 7, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 39, 0, 0, 0, 76, 3, 0, 0, 2, 4, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, -84, 13, 0, 0, -56, 0, 0, 0},
		{11, 33, 2, 0, -4, 44, 74, 0, 0, 0, 0, 0, 6, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25, 0, 0, 0, 76, 13, 0, 0, 0, 33, 0, 0, 4, 0, 0, 0, 10, 0, 0, 0, -128, 62, 0, 0, -80, 4, 0, 0},
		{12, 16, 0, 0, -16, 44, 74, 0, 0, 0, 0, 0, 8, 0, 0, 0, 7, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 48, 0, 0, 0, 76, 6, 0, 0, 1, 10, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, 64, 31, 0, 0, 44, 1, 0, 0},
		{13, 50, 0, 0, 68, -50, 73, 0, 0, 0, 0, 0, 9, 0, 0, 0, 8, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 61, 0, 0, 0, 76, 2, 0, 0, 2, 30, 0, 0, 16, 0, 0, 0, 32, 0, 0, 0, -80, 54, 0, 0, -74, 3, 0, 0},
		{14, 30, 1, 0, -32, 44, 74, 0, 0, 0, 0, 0, 8, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 54, 0, 0, 0, 76, 15, 0, 0, 1, 18, 0, 0, 20, 0, 0, 0, 60, 0, 0, 0, -8, 42, 0, 0, -18, 2, 0, 0},
		{15, 35, 0, 0, -44, 44, 74, 0, 0, 0, 0, 0, 9, 0, 0, 0, 8, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 54, 0, 0, 0, 76, 41, 0, 0, 3, 20, 0, 0, 20, 0, 0, 0, 40, 0, 0, 0, 16, 39, 0, 0, -118, 2, 0, 0},
		{16, 0, 1, 0, -60, 44, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 76, 0, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{17, 0, 2, 0, -76, 44, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 76, 0, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{18, 60, 2, 0, -84, 44, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 87, 0, 0, 0, 78, 42, 0, 0, 3, 35, 0, 0, 16, 0, 0, 0, 32, 0, 0, 0, 8, 82, 0, 0, 20, 5, 0, 0},
		{19, 0, 2, 0, -100, 44, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 76, 0, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{20, 11, 0, 0, 12, -18, 72, 0, 0, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 76, 49, 0, 0, 1, 6, 0, 0, 20, 0, 0, 0, 40, 0, 0, 0, -48, 7, 0, 0, 100, 0, 0, 0},
		{21, 100, 0, 0, 116, -54, 73, 0, 0, 0, 0, 0, 11, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 81, 0, 0, 0, 76, 33, 0, 0, 6, 60, 0, 0, 16, 0, 0, 0, 32, 0, 0, 0, 80, 70, 0, 0, 76, 4, 0, 0},
		{22, 0, 1, 0, -112, 44, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 82, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{23, 35, 2, 0, -124, 44, 74, 0, 0, 0, 0, 0, 14, 0, 0, 0, 12, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 105, 0, 0, 0, 80, 26, 0, 0, 3, 15, 0, 0, 16, 0, 0, 0, 32, 0, 0, 0, 32, 78, 0, 0, -30, 4, 0, 0},
		{24, -106, 0, 0, 120, 44, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -107, 0, 0, 0, 76, 44, 0, 0, 6, 90, 0, 0, 8, 0, 0, 0, 12, 0, 0, 0, 48, 117, 0, 0, -48, 7, 0, 0},
		{25, 100, 2, 0, 108, 44, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 93, 0, 0, 0, 76, 34, 0, 0, 0, 100, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, -112, 101, 0, 0, 64, 6, 0, 0},
		{26, 0, 2, 0, 96, 44, 74, 0, 96, 44, 74, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 0, 0, 0, -1, -1, -1, -1, 80, 45, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{27, 0, 2, 0, 80, 44, 74, 0, 80, 44, 74, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 0, 0, 0, -1, -1, -1, -1, 80, 46, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{28, 0, 2, 0, 68, 44, 74, 0, 68, 44, 74, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, 80, 47, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{29, 35, 0, 0, 56, 44, 74, 0, 0, 0, 0, 0, 8, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 76, 61, 0, 0, 2, 20, 0, 0, 20, 0, 0, 0, 60, 0, 0, 0, 4, 41, 0, 0, -68, 2, 0, 0},
		{30, 6, 1, 0, 40, 44, 74, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 25, 0, 0, 0, 76, 52, 0, 0, 1, 6, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, -24, 3, 0, 0, 50, 0, 0, 0},
		{31, 7, 2, 0, 28, 44, 74, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0, 76, 53, 0, 0, 1, 3, 0, 0, 40, 0, 0, 0, 80, 0, 0, 0, -24, 3, 0, 0, 50, 0, 0, 0},
		{32, 20, 2, 0, 16, 44, 74, 0, 0, 0, 0, 0, -1, -1, -1, -1, 5, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 30, 0, 0, 0, 82, 54, 0, 0, 0, 20, 0, 0, 4, 0, 0, 0, 10, 0, 0, 0, -96, 15, 0, 0, -6, 0, 0, 0},
		{33, 15, 2, 0, 4, 44, 74, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 0, 0, 0, 76, 55, 0, 0, 2, 8, 0, 0, 20, 0, 0, 0, 40, 0, 0, 0, -60, 9, 0, 0, -56, 0, 0, 0},
		{34, 5, 2, 0, -8, 43, 74, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 17, 0, 0, 0, 82, 60, 0, 0, 3, 1, 0, 0, 20, 0, 0, 0, 40, 0, 0, 0, -24, 3, 0, 0, 50, 0, 0, 0},
		{35, 25, 2, 0, -20, 43, 74, 0, 0, 0, 0, 0, 14, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 70, 0, 0, 0, 76, 24, 0, 0, 2, 14, 0, 0, 20, 0, 0, 0, 60, 0, 0, 0, 108, 107, 0, 0, 8, 7, 0, 0},
		{36, 24, 2, 0, -32, 43, 74, 0, 0, 0, 0, 0, 9, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 34, 0, 0, 0, 76, 63, 0, 0, 1, 12, 0, 0, 20, 0, 0, 0, 60, 0, 0, 0, -20, 44, 0, 0, 32, 3, 0, 0}
	};
	
	protected SpellsStore ss;
	
	@Override
	protected void setUp(){
		ReaderWriter rw = new ReaderWriter(false);
		ss = new SpellsStore(rw);
	}
	
	@Test
	public void testGetSpellAsBytes(){
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SPELLS; i++){
			String origString = Arrays.toString(origSpellBytes[i]);
			byte[] retrievedBytes = ss.getSpellAsBytes(i);
			String retrievedString = Arrays.toString(retrievedBytes);
			assertEquals(origString, retrievedString);
		}
	}
}