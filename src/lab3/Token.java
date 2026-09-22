package lab3;

/** One piece of tokenized text: its kind and the exact substring it came from. */
public record Token(TokenType type, String text) {
}
