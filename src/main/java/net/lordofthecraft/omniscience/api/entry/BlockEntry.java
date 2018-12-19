package net.lordofthecraft.omniscience.api.entry;

import static net.lordofthecraft.omniscience.api.data.DataKeys.NEW_BLOCK;
import static net.lordofthecraft.omniscience.api.data.DataKeys.ORIGINAL_BLOCK;

import java.util.Optional;
import net.lordofthecraft.omniscience.api.data.DataWrapper;
import net.lordofthecraft.omniscience.api.data.Transaction;
import net.lordofthecraft.omniscience.util.DataHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;

public class BlockEntry extends DataEntryComplete implements Actionable {

    public BlockEntry() {
    }

    @Override
    public ActionResult rollback() throws Exception {
        DataWrapper original = data.getWrapper(ORIGINAL_BLOCK)
                .orElseThrow(() -> skipped(SkipReason.INVALID));

        BlockData originalData = DataHelper.getBlockDataFromWrapper(original)
                .orElseThrow(() -> skipped(SkipReason.INVALID));
        Location location = DataHelper.getLocationFromDataWrapper(data)
                .orElseThrow(() -> skipped(SkipReason.INVALID_LOCATION));

        BlockState beforeState = location.getBlock().getState();

        location.getBlock().setBlockData(originalData);

        //TODO if there is additional stored state data we need to pull that down and apply it

        if (location.getBlock().getState() instanceof Sign) {
            String[] signText = DataHelper.getSignTextFromWrapper(original)
                                              .orElseThrow(() -> skipped(SkipReason.INVALID));
            Sign sign = (Sign) location.getBlock().getState();
            for (int i = 0; i < 4; i++) {
                sign.setLine(i, signText[i]);
            }
            sign.update(true);
        }

        return ActionResult.success(new Transaction<>(beforeState, location.getBlock().getState()));
    }

    @Override
    public ActionResult restore() throws Exception {
        Location location = DataHelper.getLocationFromDataWrapper(data)
                .orElseThrow(() -> skipped(SkipReason.INVALID_LOCATION));
        Optional<DataWrapper> oFinalState = data.getWrapper(NEW_BLOCK);
        BlockState beforeState = location.getBlock().getState();
        if (!oFinalState.isPresent()) {
            location.getBlock().setBlockData(Material.AIR.createBlockData());
            return ActionResult.success(new Transaction<>(beforeState, location.getBlock().getState()));
        }
        DataWrapper finalState = oFinalState.get();

        BlockData finalData = DataHelper.getBlockDataFromWrapper(finalState)
                .orElseThrow(() -> skipped(SkipReason.INVALID));

        location.getBlock().setBlockData(finalData);

        //TODO if there is additional stored state data we need to pull that down and apply it

        if (location.getBlock().getState() instanceof Sign) {
            String[] signText = DataHelper.getSignTextFromWrapper(finalState)
                                          .orElseThrow(() -> skipped(SkipReason.INVALID));
            Sign sign = (Sign) location.getBlock().getState();
            for (int i = 0; i < 4; i++) {
                sign.setLine(i, signText[i]);
            }
            sign.update(true);
        }

        return ActionResult.success(new Transaction<>(beforeState, location.getBlock().getState()));
    }
}
