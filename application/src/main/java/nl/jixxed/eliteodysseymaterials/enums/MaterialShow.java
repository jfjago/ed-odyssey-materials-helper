package nl.jixxed.eliteodysseymaterials.enums;

import nl.jixxed.eliteodysseymaterials.constants.BlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.Search;
import nl.jixxed.eliteodysseymaterials.domain.Storage;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

import java.util.Map;
import java.util.function.Predicate;

public enum MaterialShow {
    ALL,
    ALL_WITH_STOCK,
    ALL_ENGINEER_BLUEPRINT,
    REQUIRED_ENGINEER_BLUEPRINT,
    ALL_ENGINEER,
    REQUIRED_ENGINEER,
    BLUEPRINT,
    IRRELEVANT,
    IRRELEVANT_WITH_STOCK,
    PROHIBITED,
    FAVOURITES;

    private static final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();

    public String getLocalizationKey() {
        return "search.filter." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

    @SuppressWarnings("java:S1452")
    public static Predicate<? super Map.Entry<? extends OdysseyMaterial, Storage>> getFilter(final Search search) {
        return switch (search.getMaterialShow()) {
            case ALL -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> true;
            case ALL_WITH_STOCK -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> o.getValue().getTotalValue() > 0;
            case BLUEPRINT -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> BlueprintConstants.isBlueprintIngredientWithOverride(o.getKey());
            case IRRELEVANT -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> getIrrelevantFilter(o.getKey());
            case IRRELEVANT_WITH_STOCK -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> getIrrelevantFilter(o.getKey()) && o.getValue().getTotalValue() > 0;
            case PROHIBITED -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> o.getKey().isIllegal();
            case ALL_ENGINEER -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> BlueprintConstants.isEngineeringIngredient(o.getKey());
            case REQUIRED_ENGINEER -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> BlueprintConstants.isEngineeringIngredientAndNotCompleted(o.getKey());
            case ALL_ENGINEER_BLUEPRINT -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> BlueprintConstants.isBlueprintIngredientWithOverride(o.getKey()) || BlueprintConstants.isEngineeringIngredient(o.getKey());
            case REQUIRED_ENGINEER_BLUEPRINT -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> BlueprintConstants.isEngineeringIngredientAndNotCompleted(o.getKey()) || BlueprintConstants.isBlueprintIngredientWithOverride(o.getKey());
            case FAVOURITES -> (Map.Entry<? extends OdysseyMaterial, Storage> o) -> APPLICATION_STATE.isFavourite(o.getKey());
        };
    }

    private static boolean getIrrelevantFilter(final OdysseyMaterial odysseyMaterial) {
        return APPLICATION_STATE.getSoloMode() ? BlueprintConstants.isNotRelevantWithOverrideAndNotRequiredEngineeringIngredient(odysseyMaterial) : BlueprintConstants.isNotRelevantAndNotEngineeringIngredient(odysseyMaterial);
    }
}
