import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import engine.core.MarioGame;
import engine.core.MarioResult;

public class PlayLevel {
    public static void printResults(MarioResult result, MarioGame game) {
        System.out.println("****************************************************************");
        System.out.println("Game Status: " + result.getGameStatus().toString() +
                " Percentage Completion: " + result.getCompletionPercentage());
        System.out.println("Lives: " + result.getCurrentLives() + " Coins: " + result.getCurrentCoins() +
                " Remaining Time: " + (int) Math.ceil(result.getRemainingTime() / 1000f));
        System.out.println("Mario State: " + result.getMarioMode() +
                " (Mushrooms: " + result.getNumCollectedMushrooms() + " Fire Flowers: " + result.getNumCollectedFireflower() + ")");
        System.out.println("Total Kills: " + result.getKillsTotal() + " (Stomps: " + result.getKillsByStomp() +
                " Fireballs: " + result.getKillsByFire() + " Shells: " + result.getKillsByShell() +
                " Falls: " + result.getKillsByFall() + ")");
        System.out.println("Bricks: " + result.getNumDestroyedBricks() + " Jumps: " + result.getNumJumps() +
                " Max X Jump: " + result.getMaxXJump() + " Max Air Time: " + result.getMaxJumpAirTime());
        
        // Print Mario's path
        System.out.println("Mario Path:");
        for (int[] position : game.getMarioPath()) {
            System.out.println("[" + position[0] + ", " + position[1] + "]");
        }
        System.out.println("****************************************************************");
    }

    public static String getLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
            System.err.println("Error reading level file: " + filepath);
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java PlayLevel <level_file>");
            return;
        }
    
        String levelFile = args[0];
        MarioGame game = new MarioGame();
    
        try {
            // Run the game with the provided level file
            MarioResult result = game.runGame(new agents.robinBaumgarten.Agent(), getLevel(levelFile), 100, 0, false);
            printResults(result, game); // Pass the game instance to printResults
        } catch (Exception e) {
            System.err.println("Error running the game with level file: " + levelFile);
            e.printStackTrace();
        }
    }
}
