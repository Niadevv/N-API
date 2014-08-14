package co.uk.niadel.mpi.gen.structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.gen.structure.StructureVillagePieces;

/**
 * The registry for village houses.
 */
public final class VillagePieceRegistry
{
	/**
	 * The list of village pieces that will be added.
	 */
	public static List<StructureVillagePieces.PieceWeight> pieces = new ArrayList<>();
	
	/**
	 * Adds a piece to the "To-Add" registry. Takes a PieceWeight for maximum control.
	 * @param piece
	 */
	public static final void addPiece(StructureVillagePieces.PieceWeight piece)
	{
		pieces.add(piece);
	}
	
	public static final void addAllPieces(ArrayList list)
	{
		Iterator<StructureVillagePieces.PieceWeight> iterator = pieces.iterator();
		
		while (iterator.hasNext())
		{
			list.add(iterator.next());
		}
	}
}
