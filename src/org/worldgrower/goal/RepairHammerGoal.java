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

import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;

public class RepairHammerGoal implements Goal {

	private static final int QUANTITY_TO_BUY = 5;
	
	public RepairHammerGoal(List<Goal> allGoals) {
		allGoals.add(this);
	}

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		OperationInfo buyOperationInfo = BuySellUtils.getBuyOperationInfo(performer, Constants.REPAIR_QUALITY, QUANTITY_TO_BUY, world);
		if (buyOperationInfo != null) {
			return buyOperationInfo;
		} else {
			return Goals.CREATE_REPAIR_HAMMER_GOAL.calculateGoal(performer, world);
		}
	}

	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
		defaultGoalMetOrNot(performer, world, goalMet, Constants.REPAIR_QUALITY);
	}
	
	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		return true;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public String getDescription() {
		return "looking for repair hammers";
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		return 0;
	}
}
