package co.uk.niadel.napi.asm;

/**
 * Interface implemented by all ASM transformers that want to not break all mods in existence.
 *
 * @author Niadel
 */
public interface IASMTransformer
{	
	/**
	 * Where you manipulate the bytecodes themselves - passedBytes is a Map of the
	 * byte[]s of the classes you requested in requestTransformedClasses. You can use ClassReader's String based constructor.
	 * @param className The fully qualified name of the curent class being transformed.
	 * @param bytes The bytes of className.
	 * @return The modified bytes. Return null if you are just reading the class.
	 */
	public byte[] manipulateBytecodes(String className, byte[] bytes);
}
