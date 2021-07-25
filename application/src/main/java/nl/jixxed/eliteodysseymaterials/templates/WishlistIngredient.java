package nl.jixxed.eliteodysseymaterials.templates;

import nl.jixxed.eliteodysseymaterials.enums.Material;
import nl.jixxed.eliteodysseymaterials.enums.StorageType;

public class WishlistIngredient extends Ingredient {

    public WishlistIngredient(final StorageType storageType, final Material material, final Integer amount, final Integer amountAvailable) {
        super(storageType, material, amount, amountAvailable);
    }

    public void highlight(final boolean enable, final Integer amountRequiredForRecipe) {
        if (enable) {
            this.getStyleClass().add("wishlist-highlight");
            this.getAmountRequiredLabel().setText(amountRequiredForRecipe.toString());
        } else {
            this.getStyleClass().removeAll("wishlist-highlight");
            this.getAmountRequiredLabel().setText(this.getAmountRequired().toString());
        }
        update();
    }

    public void lowlight(final boolean enable) {
        if (enable) {
            this.getStyleClass().add("wishlist-lowlight");
        } else {
            this.getStyleClass().removeAll("wishlist-lowlight");
        }
    }

}
