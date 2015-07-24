/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.actions;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.Map;

import org.worldgrower.ArgumentRange;
import org.worldgrower.Constants;
import org.worldgrower.Reach;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.WorldObjectImpl;
import org.worldgrower.attribute.ManagedProperty;
import org.worldgrower.generator.WellOnTurn;
import org.worldgrower.goal.GoalUtils;
import org.worldgrower.gui.ImageIds;

public class BuildWellAction implements BuildAction {

	@Override
	public void execute(WorldObject performer, WorldObject target, int[] args, World world) {
		int x = (Integer)target.getProperty(Constants.X) + 1;
		int y = (Integer)target.getProperty(Constants.Y) + 1;
		
		Map<ManagedProperty<?>, Object> properties = new HashMap<>();
		properties.put(Constants.X, x);
		properties.put(Constants.Y, y);
		properties.put(Constants.WIDTH, 2);
		properties.put(Constants.HEIGHT, 2);
		properties.put(Constants.WATER_SOURCE, 2000);
		properties.put(Constants.NAME, "well");
		properties.put(Constants.ID, world.generateUniqueId());
		properties.put(Constants.IMAGE_ID, ImageIds.WELL);
		properties.put(Constants.HIT_POINTS, 75);
		properties.put(Constants.HIT_POINTS_MAX, 75);
		properties.put(Constants.ARMOR, 0);
		properties.put(Constants.DAMAGE_RESIST, 0);
		
		WorldObject well = new WorldObjectImpl(properties, new WellOnTurn());
		world.addWorldObject(well);
		
		performer.getProperty(Constants.INVENTORY).removeQuantity(Constants.WOOD, 6);
	}

	@Override
	public boolean isValidTarget(WorldObject performer, WorldObject target, World world) {
		int x = (Integer)target.getProperty(Constants.X) + 1;
		int y = (Integer)target.getProperty(Constants.Y) + 1;
		return GoalUtils.isOpenSpace(x, y, 2, 2, world);
	}

	@Override
	public int distance(WorldObject performer, WorldObject target, int[] args, World world) {
		int distanceBetweenPerformerAndTarget = Reach.evaluateTarget(performer, args, target, 1);
		int wood = performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.WOOD);
		if (wood < 6) {
			return (6 - wood) * 1000 + distanceBetweenPerformerAndTarget;
		} else {
			return 0 + distanceBetweenPerformerAndTarget;
		}
	}

	@Override
	public ArgumentRange[] getArgumentRanges() {
		ArgumentRange[] argumentRanges = new ArgumentRange[2];
		argumentRanges[0] = new ArgumentRange(-3, 3);
		argumentRanges[1] = new ArgumentRange(-3, 3);
		return argumentRanges;
	}
	
	@Override
	public String getDescription(WorldObject performer, WorldObject target, int[] args, World world) {
		return "building a well";
	}

	@Override
	public String getSimpleDescription() {
		return "build well";
	}
	
	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}

	@Override
	public int getWidth() {
		return 2;
	}

	@Override
	public int getHeight() {
		return 2;
	}
}