package co.uk.niadel.mpi.asm;

public interface IASMTransformer 
{
	/**
	 * Where the ASM class is basically told to do it's bytecode manipulating. I don't give
	 * any parameters as I can't feasibly pass the bytecodes and names of every single class
	 * in Minecraft, even thinking of doing mod classes and bytecodes as well is insane. However,
	 * UtillityMethods covers this.
	 */
	public void manipulateBytecodes();
}
