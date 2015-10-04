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
package org.worldgrower.condition;

import org.worldgrower.ManagedOperation;
import org.worldgrower.World;
import org.worldgrower.WorldObject;

public class VampireBiteCondition implements Condition {

	@Override
	public boolean canTakeAction() {
		return true;
	}

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public String getDescription() {
		return "bitten by a vampire";
	}

	@Override
	public void onTurn(WorldObject worldObject, World world, int startTurn, CreatureTypeChangedListeners creatureTypeChangedListeners) {
		int currentTurn = world.getCurrentTurn().getValue();
		
		if (currentTurn - startTurn > 500) {
			VampireUtils.vampirizePerson(worldObject, creatureTypeChangedListeners);
			Conditions.remove(worldObject, this);
		}
	}
	
	@Override
	public boolean isDisease() {
		return true;
	}

	@Override
	public void conditionEnds(WorldObject worldObject) {
	}
	
	@Override
	public void perform(WorldObject performer, WorldObject target, int[] args, ManagedOperation managedOperation, World world) {
	}
}