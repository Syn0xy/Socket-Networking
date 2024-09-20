package network.utils;

public final class RandomIdentifier {

    private static final int DEFAULT_IDENTIFIER_SIZE = 20;

    private static final int[][] CHARACTER_INDICES = {
        { '0', '9' },
        { 'A', 'Z' },
        { 'a', 'z' },
    };

    private static final int CHARACTER_INDICES_SIZE = RandomIdentifier.getCharacterIndicesSize();
        
    public static final Character[] CHARACTERS = RandomIdentifier.getCharacters();

    public static char randomCharacter() {
        final int randomIndice = (int) (Math.random() * RandomIdentifier.CHARACTERS.length);
        return RandomIdentifier.CHARACTERS[randomIndice];
    }
    
    public static String random(final int identifierSize) {
        final char[] identifier = new char[identifierSize];

        for (int i = 0; i < identifier.length; i++) {
            identifier[i] = RandomIdentifier.randomCharacter();
        }

        return String.valueOf(identifier);
    }
    
    public static String random() {
        return RandomIdentifier.random(RandomIdentifier.DEFAULT_IDENTIFIER_SIZE);
    }

    private static int getCharacterIndicesSize() {
        int size = 0;

        for (int i = 0; i < RandomIdentifier.CHARACTER_INDICES.length; i++) {
            size += RandomIdentifier.CHARACTER_INDICES[i][1] - RandomIdentifier.CHARACTER_INDICES[i][0] + 1;
        }

        return size;
    }

    private static Character[] getCharacters() {
        final Character[] characters = new Character[RandomIdentifier.CHARACTER_INDICES_SIZE];
        int c = 0;
        
        for (int i = 0; i < RandomIdentifier.CHARACTER_INDICES.length; i++) {
            final int start = RandomIdentifier.CHARACTER_INDICES[i][0];
            final int end = RandomIdentifier.CHARACTER_INDICES[i][1];

            for (int j = start; j <= end; j++) {
                characters[c++] = (char) j;
            }
        }

        return characters;
    }
    
}
