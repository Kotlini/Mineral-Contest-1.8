package fr.ylanouh.mineralcontest.game;

public enum GameState {
    LOBBY, WAITTING, GAME, END;

    private static GameState state;

    public static void setState(GameState gameState) {
        state = gameState;
    }

    public static boolean isState(GameState gameState) {
        return (state == gameState);
    }

    public static GameState getGameState() {
        return state;
    }
}