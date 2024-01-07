package com.vanannek.socialmedia;

public class ReactionUtils {
    public static final String REACTION_ADDED_SUCCESSFULLY = "Reaction added successfully";
    public static final String REACTION_UPDATED_SUCCESSFULLY = "Reaction updated successfully";
    public static final String REACTION_DELETED_SUCCESSFULLY = "Reaction deleted successfully";
    public static final String REACTIONS_RETRIEVED_SUCCESSFULLY = "Reactions retrieved successfully";

    public static EReactionType toEReactionType(String type) {
        try {
            return EReactionType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new UnsupportedReactionTypeException("Unsupported reaction type: " + type);
        }
    }
}
