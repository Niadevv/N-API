These are the general code guidelines for if you are contributing any code:

- Don't use external libraries. By external library I mean a library that isn't included in Minecraft or is not ASM.
- Tab indents. It makes awkward indenting easier (looks at both Eclipse AND IntelliJ) to fix. It's bad enough when I have to fix my
indenting on the Minecraft Forums when I'm helping someone with an issue >:|.
- Descriptive parameters and variable names, sometimes too descriptive :P.
- Braces go on the line after method/class/etc declarations, like in MCP decompiled code.
- I store most info in lists and maps: If I need a two way map, I use my DoubleMap class. There's probably only 3 fields at most in
all of N-API that are arrays.
- DO NOT BASE EDIT! There's a reflection library in util.reflection, use that. It is a pain to add a base edit. Using reflection
and ASM, there are only 3 base edits in all of N-API, and only one is technically necessary for N-API - the Bootstrap class to
allow NModLoader to load mods and call their methods. If you must base edit, use ASM. There is an N-API ASM transformer in the
asm package. The only exception to Reflection is if it is used where it will continuously be called. Make an issue if this is
the case.
- If possible, use for in loops, the colon ones. If you don't know what they are, you probably shouldn't be touching N-API code.
- English-English spelling. If I see an instance of American spelling in code, I instantly remove it and substitute English spelling. IE. Pants in American spelling = Trousers in English spelling, pants in English spelling = male underwear in American spelling.
- Complex code is well commented. In my code, when I do this, it can occasionally be TOO well commented.
- Javadoc for all methods, and most classes, unless they're just overloaded methods. If it is an overloaded method, though, at least
one of the overloaded methods HAS to be javadoc commented. You don't have to mess with @param or @return descriptions, though, but
it is highly recommend that you do. However, if you don't put the description for @param, please do describe what the parameters are
in the javadoc description for the method itself. I'm not expecting a paragraph, but I do want at least SOME description at least.
- Use Minecraft.getMinecraft().getTextureManager() if you need to use the render engine. This is a Forge compatible method, as it
means I don't have to base edit Minecraft.

I think I lied when I said it was somewhat simple :3 Just keep to these guidelines and you should be fine. Also, if you fix dodgy indenting (say, IDEA's idea of good auto-format indenting), thanks :D!
