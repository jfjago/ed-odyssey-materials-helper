package nl.jixxed.eliteodysseymaterials.service;

import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import de.saxsys.mvvmfx.testingutils.jfxrunner.TestInJfxThread;
import nl.jixxed.eliteodysseymaterials.constants.BlueprintConstants;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.ModuleBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.domain.WishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.enums.BlueprintName;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;
import nl.jixxed.eliteodysseymaterials.service.event.EventService;
import nl.jixxed.eliteodysseymaterials.service.event.LocationJournalEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;

import static nl.jixxed.eliteodysseymaterials.enums.BlueprintName.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(JfxToolkitExtension.class)
class PathServiceTest {
    @BeforeEach
    void setUp() {
        final ApplicationState APPLICATION_STATE = ApplicationState.getInstance();
        APPLICATION_STATE.setEngineerState(Engineer.DOMINO_GREEN, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.HERO_FERRARI, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.JUDE_NAVARRO, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.KIT_FOWLER, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.ODEN_GEIGER, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.TERRA_VELASQUEZ, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.UMA_LASZLO, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.WELLINGTON_BECK, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.YARDEN_BOND, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.BALTANOS, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.ELEANOR_BRESA, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.ROSA_DAYETTE, EngineerState.UNLOCKED);
        APPLICATION_STATE.setEngineerState(Engineer.YI_SHEN, EngineerState.UNLOCKED);
    }

    @Test
    @TestInJfxThread
    void calculateShortestPath() {
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final List<nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint> wishlistBlueprints = List.of(
                new nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint("TEST", new WishlistBlueprint(BlueprintName.GREATER_RANGE_PLASMA, true)),
                new nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint("TEST", new WishlistBlueprint(MAGAZINE_SIZE, true)),
                new nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint("TEST", new WishlistBlueprint(BlueprintName.SCOPE, true)),
                new nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint("TEST", new WishlistBlueprint(BlueprintName.STABILITY, true))
        );
        LocationService.getCurrentStarSystem();

        EventService.publish(new LocationJournalEvent("", Engineer.ODEN_GEIGER.getStarSystem(), "", "", true));

        final List<PathItem> pathItems = PathService.calculateShortestPath(wishlistBlueprints);

        assertAll(() -> {
            assertEquals(3, pathItems.size());
            assertEquals(Engineer.ODEN_GEIGER, pathItems.get(0).getEngineer());
            assertThat(pathItems.get(0).getRecipes().keySet()).containsExactlyInAnyOrder((ModuleBlueprint) BlueprintConstants.getRecipe(SCOPE));
            assertEquals(Engineer.KIT_FOWLER, pathItems.get(1).getEngineer());
            assertThat(pathItems.get(1).getRecipes().keySet()).containsExactlyInAnyOrder((ModuleBlueprint) BlueprintConstants.getRecipe(MAGAZINE_SIZE));
            assertEquals(Engineer.DOMINO_GREEN, pathItems.get(2).getEngineer());
            assertThat(pathItems.get(2).getRecipes().keySet()).containsExactlyInAnyOrder((ModuleBlueprint) BlueprintConstants.getRecipe(GREATER_RANGE_PLASMA), (ModuleBlueprint) BlueprintConstants.getRecipe(STABILITY));
            assertEquals(203.7865988002623, pathItems.get(0).getDistance() + pathItems.get(1).getDistance() + pathItems.get(2).getDistance());
//            System.out.println(pathItems.get(0).getDistance() + "/" + pathItems.get(1).getDistance() + "/" + pathItems.get(2).getDistance());
        });
    }

    @Test
    @TestInJfxThread
    void calculateShortestPath2() {
        LocaleService.setCurrentLocale(Locale.ENGLISH);
        final List<nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint> wishlistBlueprints = List.of(
                new nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint("TEST", new WishlistBlueprint(BlueprintName.GREATER_RANGE_PLASMA, true)),
                new nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint("TEST", new WishlistBlueprint(MAGAZINE_SIZE, true)),
                new nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint("TEST", new WishlistBlueprint(BlueprintName.SCOPE, true)),
                new nl.jixxed.eliteodysseymaterials.templates.WishlistBlueprint("TEST", new WishlistBlueprint(BlueprintName.STABILITY, true))
        );
        LocationService.getCurrentStarSystem();

        EventService.publish(new LocationJournalEvent("", Engineer.DOMINO_GREEN.getStarSystem(), "", "", true));
        final List<PathItem> pathItems = PathService.calculateShortestPath(wishlistBlueprints);

        assertAll(() -> {
            assertEquals(3, pathItems.size());
            assertEquals(Engineer.DOMINO_GREEN, pathItems.get(0).getEngineer());
            assertThat(pathItems.get(0).getRecipes().keySet()).containsExactlyInAnyOrder((ModuleBlueprint) BlueprintConstants.getRecipe(STABILITY), (ModuleBlueprint) BlueprintConstants.getRecipe(GREATER_RANGE_PLASMA));
            assertEquals(Engineer.KIT_FOWLER, pathItems.get(1).getEngineer());
            assertThat(pathItems.get(1).getRecipes().keySet()).containsExactlyInAnyOrder((ModuleBlueprint) BlueprintConstants.getRecipe(MAGAZINE_SIZE));
            assertEquals(Engineer.ODEN_GEIGER, pathItems.get(2).getEngineer());
            assertThat(pathItems.get(2).getRecipes().keySet()).containsExactlyInAnyOrder((ModuleBlueprint) BlueprintConstants.getRecipe(SCOPE));
            assertEquals(203.7865988002623, pathItems.get(0).getDistance() + pathItems.get(1).getDistance() + pathItems.get(2).getDistance());
//            System.out.println(pathItems.get(0).getDistance() + "/" + pathItems.get(1).getDistance() + "/" + pathItems.get(2).getDistance());
        });
    }
}