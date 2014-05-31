package co.uk.niadel.mpi;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

import net.minecraft.client.main.Main;

/**
 * MCP Start thing, not mine at all. At a later date I may edit this to allow for Eclipse launching.
 * @author Niadel
 *
 */
public class Start
{
    public static void main(String[] args)
    {
        Main.main(concat(new String[] {"--version", "mcp", "--accessToken", "0", "--assetsDir", "assets/virtual/legacy"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second)
    {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
