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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.OperationInfo;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.IdMap;
import org.worldgrower.conversation.Conversations;
import org.worldgrower.history.Turn;

public class CollectTaxesGoal implements Goal {

	@Override
	public OperationInfo calculateGoal(WorldObject performer, World world) {
		List<WorldObject> targets = getCollectTaxesTargets(performer, world);
		Collections.sort(targets, new AmountToPayComparator(world));
		if (targets.size() > 0) {
			return new OperationInfo(performer, targets.get(0), Conversations.createArgs(Conversations.COLLECT_TAXES_CONVERSATION), Actions.TALK_ACTION);
		} else {
			return null;
		}
		
	}
	
	@Override
	public void goalMetOrNot(WorldObject performer, World world, boolean goalMet) {
	}

	@Override
	public boolean isGoalMet(WorldObject performer, World world) {
		List<WorldObject> targets = getCollectTaxesTargets(performer, world);
		return targets.size() == 0;
	}

	private List<WorldObject> getCollectTaxesTargets(WorldObject performer, World world) {
		WorldObject organization = GroupPropertyUtils.getVillagersOrganization(world);
		List<WorldObject> members = GroupPropertyUtils.findOrganizationMembers(organization, world);
		
		IdMap taxesPaidTurn = organization.getProperty(Constants.TAXES_PAID_TURN);
		Turn currentTurn = world.getCurrentTurn();
		
		List<WorldObject> targets = GoalUtils.findNearestTargets(performer, Actions.TALK_ACTION, w -> mustPayTaxes(members, taxesPaidTurn, currentTurn, w, world), world);
		return targets;
	}

	private boolean mustPayTaxes(List<WorldObject> members, IdMap taxesPaidTurn, Turn currentTurn, WorldObject w, World world) {
		return members.contains(w) && GroupPropertyUtils.getAmountToCollect(w, world) > 0;
	}
	
	@Override
	public boolean isUrgentGoalMet(WorldObject performer, World world) {
		return isGoalMet(performer, world);
	}

	@Override
	public String getDescription() {
		return "collecting taxes";
	}

	@Override
	public int evaluate(WorldObject performer, World world) {
		Integer organizationGold = performer.getProperty(Constants.ORGANIZATION_GOLD);
		return organizationGold != null ? organizationGold.intValue() : 0;
	}
	
	private static class AmountToPayComparator implements Comparator<WorldObject> {
		private final World world;
		
		public AmountToPayComparator(World world) {
			this.world = world;
		}

		@Override
		public int compare(WorldObject o1, WorldObject o2) {
			int amountToPay1 = GroupPropertyUtils.getAmountToCollect(o1, world);
			int amountToPay2 = GroupPropertyUtils.getAmountToCollect(o2, world);
			
			return amountToPay1 - amountToPay2;
		}
	}
}
