package nanodegree.bakingapp.view;

import java.text.NumberFormat;

public final class RecipeFormatUtil {
    private RecipeFormatUtil() {
    }

    public static String formatIngredientQuantity(float quantity) {
        NumberFormat nf;

        if (Math.round(quantity) == quantity) {
            nf = NumberFormat.getIntegerInstance();
        } else {
            nf = NumberFormat.getNumberInstance();

            String[] parts = Float.toString(quantity).split("\\.");

            nf.setMinimumFractionDigits(parts[1].length());
            nf.setMaximumFractionDigits(parts[1].length());
        }

        return nf.format(quantity);
    }
}
