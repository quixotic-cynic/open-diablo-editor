package uk.me.karlsen.ode.stores;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import uk.me.karlsen.ode.ReaderWriter;
import uk.me.karlsen.ode.TomeOfKnowledge;
import uk.me.karlsen.ode.types.Shrine;
import uk.me.karlsen.ode.types.ShrinesAsBytes;
import uk.me.karlsen.ode.utils.BinEditHelper;

public class ShrinesStore {
	
	private final static Logger LOGGER = Logger.getLogger(ShrinesStore.class.getName());

	ReaderWriter rw;
	private List<Shrine> shrines;

	byte[] origShrinePointerBytes;
	byte[] origMinShrineLevelBytes;
	byte[] origMaxShrineLevelBytes;
	byte[] origGameTypesInWhichPresentBytes;

	public ShrinesStore(ReaderWriter rw){
		this.rw = rw;
		shrines = new ArrayList<Shrine>();
		this.readInShrines();
	}

	//TODO -- remove redundant code
	public void readInShrines(){

		BinEditHelper beh = new BinEditHelper(rw);
		
		long[] shrinePointers = new long[TomeOfKnowledge.NUMBER_OF_SHRINES];
		int[] minShrineLevels = new int[TomeOfKnowledge.NUMBER_OF_SHRINES];
		int[] maxShrineLevels = new int[TomeOfKnowledge.NUMBER_OF_SHRINES];
		int[] gameTypesInWhichPresent = new int[TomeOfKnowledge.NUMBER_OF_SHRINES];

		long pos = TomeOfKnowledge.SHRINE_POINTERS_OFFSET;
		rw.seek(pos);
		origShrinePointerBytes = rw.readBytes(TomeOfKnowledge.NUMBER_OF_SHRINES*4);
		rw.seek(pos);
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			int[] pointer = new int[4];
			for(int j = 0; j < 4; j++){
				pointer[j] = rw.read();
				pos++;
				rw.seek(pos);
			}
			shrinePointers[i] = beh.convertThreeBytesToOffset(pointer);
		}

		LOGGER.info("");

		pos = TomeOfKnowledge.SHRINE_MIN_LEVELS_OFFSET;
		rw.seek(pos);
		origMinShrineLevelBytes = rw.readBytes(TomeOfKnowledge.NUMBER_OF_SHRINES);
		rw.seek(pos);
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			minShrineLevels[i] = rw.read();
			pos++;
			rw.seek(pos);
		}

		LOGGER.info("");

		pos = TomeOfKnowledge.SHRINE_MAX_LEVELS_OFFSET;
		rw.seek(pos);
		origMaxShrineLevelBytes = rw.readBytes(TomeOfKnowledge.NUMBER_OF_SHRINES);
		rw.seek(pos);
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			maxShrineLevels[i] = rw.read();
			pos++;
			rw.seek(pos);
		}

		LOGGER.info("");
		pos = TomeOfKnowledge.SHRINE_GAME_TYPE_OFFSET;
		rw.seek(pos);
		origGameTypesInWhichPresentBytes = rw.readBytes(TomeOfKnowledge.NUMBER_OF_SHRINES);
		rw.seek(pos);
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			gameTypesInWhichPresent[i] = rw.read();
			pos++;
			rw.seek(pos);
		}

		//create Shrine objects and add to list
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			BinEditHelper h = new BinEditHelper(rw);
			String shrineName = h.getNameUsingPointer(shrinePointers[i]);
			Shrine s = new Shrine(i, shrineName, shrinePointers[i], minShrineLevels[i], maxShrineLevels[i], gameTypesInWhichPresent[i]);
			shrines.add(s);
		}
	}

	public void printShrines() {
		for(Shrine s : shrines){
			s.printShrine();
		}
	}

	public ShrinesAsBytes getShrinesAsBytes() {
		byte[] shrinePointerBytes = this.getShrinePointerBytes();
		byte[] minShrineLevelBytes = this.getMinShrineLevelBytes();
		byte[] maxShrineLevelBytes = this.getMaxShrineLevelBytes();
		byte[] gameTypesInWhichPresentBytes = this.getGameTypesInWhichPresentBytes();

		ShrinesAsBytes sab = new ShrinesAsBytes(shrinePointerBytes, minShrineLevelBytes, maxShrineLevelBytes, gameTypesInWhichPresentBytes);
		return sab;
	}

	public byte[] getShrinePointerBytes(){
		byte[] shrinePointerBytes = new byte[TomeOfKnowledge.NUMBER_OF_SHRINES*4];
		int location = 0;
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			Shrine s = shrines.get(i);
			byte[] singleShrineBytes = s.getShrinePointerBytes();
			System.arraycopy(singleShrineBytes, 0, shrinePointerBytes, location, 4);
			location = location + 4;
		}
		return shrinePointerBytes;
	}

	public byte[] getMinShrineLevelBytes(){
		byte[] minShrineLevelBytes = new byte[TomeOfKnowledge.NUMBER_OF_SHRINES];
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			Shrine s = shrines.get(i);
			minShrineLevelBytes[i] = s.getMinShrineLevelByte();
		}
		return minShrineLevelBytes;
	}

	public byte[] getMaxShrineLevelBytes(){
		byte[] maxShrineLevelBytes = new byte[TomeOfKnowledge.NUMBER_OF_SHRINES];
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			Shrine s = shrines.get(i);
			maxShrineLevelBytes[i] = s.getMaxShrineLevelByte();
		}
		return maxShrineLevelBytes;
	}

	public byte[] getGameTypesInWhichPresentBytes(){
		byte[] gameTypesInWhichPresentBytes = new byte[TomeOfKnowledge.NUMBER_OF_SHRINES];
		for(int i = 0; i < TomeOfKnowledge.NUMBER_OF_SHRINES; i++){
			Shrine s = shrines.get(i);
			gameTypesInWhichPresentBytes[i] = s.getGameTypeByte();
		}
		return gameTypesInWhichPresentBytes;
	}

	public Shrine getShrine(int i) {
		return shrines.get(i);
	}

	public void writeShrinesToEXE() {
		ShrinesAsBytes sab = this.getShrinesAsBytes();

		byte[] shrinePointerBytes = sab.getShrinePointerBytes();
		rw.writeBytes(shrinePointerBytes, TomeOfKnowledge.SHRINE_POINTERS_OFFSET);

		byte[] minShrineLevelBytes = sab.getMinShrineLevelBytes();
		rw.writeBytes(minShrineLevelBytes, TomeOfKnowledge.SHRINE_MIN_LEVELS_OFFSET);

		byte[] maxShrineLevelBytes = sab.getMaxShrineLevelBytes();
		rw.writeBytes(maxShrineLevelBytes, TomeOfKnowledge.SHRINE_MAX_LEVELS_OFFSET);

		byte[] gameTypesInWhichPresentBytes = sab.getGameTypeBytes();
		rw.writeBytes(gameTypesInWhichPresentBytes, TomeOfKnowledge.SHRINE_GAME_TYPE_OFFSET);
	}

	void disableBadShrines() {
		Shrine hiddenShrine = this.getShrine(1);
		hiddenShrine.setMinShrineLevel(0);
		hiddenShrine.setMaxShrineLevel(0);

		Shrine fascinatingShrine = this.getShrine(9);
		fascinatingShrine.setMinShrineLevel(0);
		fascinatingShrine.setMaxShrineLevel(0);

		Shrine sacredShrine = this.getShrine(16);
		sacredShrine.setMinShrineLevel(0);
		sacredShrine.setMaxShrineLevel(0);

		Shrine secludedShrine = this.getShrine(22);
		secludedShrine.setMinShrineLevel(0);
		secludedShrine.setMaxShrineLevel(0);

		Shrine ornateShrine = this.getShrine(23);
		ornateShrine.setMinShrineLevel(0);
		ornateShrine.setMaxShrineLevel(0);

		Shrine taintedShrine = this.getShrine(25);
		taintedShrine.setMinShrineLevel(0);
		taintedShrine.setMaxShrineLevel(0);

	}

	public String[] getShrineNames() {
		String[] shrineNames = new String[shrines.size()];
		for(int i = 0; i < shrines.size(); i++){
			shrineNames[i] = shrines.get(i).getShrineName();
		}
		return shrineNames;
	}

	public Shrine getShrineByName(String shrineName) {
		Shrine shrineToReturn = null;
		for(Shrine s : shrines)
		{
			if(s.getShrineName().equals(shrineName))
			{
				shrineToReturn = s;
				break;
			}
		}
		return shrineToReturn;
	}
}
