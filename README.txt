ORBITAL MENU (CLIENT-ONLY FABRIC MOD)
Press O in game to open the menu, aim at a block, click a strike.

It only sends normal commands (/summon tnt, /summon lightning_bolt, /particle),
so it works on any server/Realm where YOU have command permission (op / cheats).
Without permission the server will reject the commands. Don't use it on servers
where you don't have permission to blow things up.

BUILD
1. Install JDK 25.
2. Open https://fabricmc.net/develop/ , pick your Minecraft version, and copy the
   versions into gradle.properties (minecraft_version, loader_version,
   fabric_api_version, loom_version).
3. In this folder run:  gradle wrapper   (or open the folder in IntelliJ IDEA 2025.3+)
4. Run:  ./gradlew build     (Windows: gradlew.bat build)
5. Your mod is build/libs/orbital-menu-client-1.0.0.jar -> put it in .minecraft/mods
   together with Fabric API.

If a class name fails to compile, the Minecraft version moved on; check
https://docs.fabricmc.net for the porting notes.

BUILD WITHOUT INSTALLING ANYTHING (GitHub)
1. Make a free account at github.com and create a new repository.
2. Upload ALL files of this project (keep the .github folder).
3. Open the Actions tab -> "Build mod" -> wait for the green check.
4. Open the finished run -> Artifacts -> download orbital-menu-client-jar (zip).
5. Unzip it. Use the file WITHOUT "-sources" in the name, put it in your mods folder with Fabric API.
