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
package org.worldgrower.goal;

import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;

public class HouseGoal implements Goal {

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		if (performer.getProperty(Constants.INVENTORY).getQuantityFor(Constants.STONE) < 14) {
			return new StoneGoal().calculateGoal(performer, world);
		} else {
			WorldObject target = BuildLocationUtils.findOpenLocationNearExistingProperty(performer, 3, 5, world);
			return new OperationInfo(performer, target, new int[0], Actions.BUILD_HOUSE_ACTION);
		}
	}

	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		Integer houseId = performer.getProperty(Constants.HOUSE_ID);
		if (houseId != null) {
			WorldObject house = world.findWorldObject(Constants.ID, houseId);
			return (house.getProperty(Constants.SLEEP_COMFORT) == 5);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public String getDescription() {
		return "looking to have a house of my own";
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return (performer.getProperty(Constants.HOUSE_ID) != null) ? 1 : 0;
	}
}