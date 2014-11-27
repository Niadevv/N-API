FOR MODDERS:
============

At time of writing, N-API is still a WIP. Mods using N-API may break (package names change, Minecraft changes force me to remove
certain features) and may still break after N-API is fully released.

Legitimate Distributors:
-----------------------
- https://github.com/Niadel/N-API - source code.
- There are no binary distributions at this point in time.

Installation (The good bit :D):
-------------------------------

There is no current way to install N-API beyond the WIP N-API Installer currently on my Github.

CONTRIBUTING TO N-API:
---------------------

If you didn't know, I'm the only developer for N-API, at least at the time of writing. I have no plans for that to change. However, because I'm the only developer, additions and changes happen a lot slower than with, say, Forge, which has, what 4 people working on it, all with a lot more time and dedication to it. But do you know the benefits of open source? Others can contribute - and, let's face it - Even if it wasn't open source, it'd only take a decompiler to get the rough source :P. Anyways, here's the list of ways you can contribute:

Letting me know about bugs and feature requests:
-----------------------------------------------

It's quite simple really, just make an issue. However, please keep it to one bug/feature an issue, and make sure your bug/feature request hasn't been submitted before. Unless, of course, it's an issue that's cropped up again, then in which case, mark it as being a reopened issue. Basically, Minecraft Forum's Suggestions section suggestion rule

Adding Features to N-API:
------------------------

Again, it's somewhat simple. Just make a pull request, explaining all of your changes and the feature. Of course, you will have to realise that, if I do accept it, I may make changes to your code if I feel it's untidy or could be optimised. I also may fix it's formatting to suit my personal style. BTW, it's this:

- Don't use external libraries. By external library I mean a library that isn't included in N-API or is not ASM.
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


CREDITS:
--------
- Gradle, for not letting me download more than a few dependencies, thus making me unable to use Forge as of 1.7.2 and
prompting me to make N-API.
- My fingers, for putting up with the 100,000+ lines of code added over the course of N-API's development.
- Anyone who has made a suggestion for a future or submitting a Issue on the N-API Github, or made a pull request. You've all in
some way helped me :D
- FML, for being a major inspiration and who's source has helped me in times of need. By the way, I didn't copy paste any code from FML
to my knowledge, don't worry CPW.
- Whatever motivated me to work on N-API as long as I have so far, because this is my most/longest developed project to date.
- The makers of The Legend Of Zelda's music, the makers of Sonic the Hedgehog's music, and other game music composers that I've
forgotten for making awesome music that I mainly listen to while developing N-API (primarily the first two listed).
- The MCP team, for their amazing work for the modding community, without which N-API would not exist, and neither would Forge or
any other modding APIs, most likely.
