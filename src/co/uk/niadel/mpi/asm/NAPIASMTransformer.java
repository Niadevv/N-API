package co.uk.niadel.mpi.asm;

import java.util.Map;

public class NAPIASMTransformer extends ASMTransformer
{
	@Override
	public String[] requestTransformedClasses()
	{
		return new String[] {"net.minecraft.world.World"};
	}

	@Override
	public Map<String, Byte[]> manipulateBytecodes(Map<String, Byte[]> passedBytes)
	{
		return passedBytes;
	}
}
