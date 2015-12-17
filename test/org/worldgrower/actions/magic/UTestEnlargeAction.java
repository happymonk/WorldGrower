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
package org.worldgrower.actions.magic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.worldgrower.Constants;
import org.worldgrower.TestUtils;
import org.worldgrower.World;
import org.worldgrower.WorldImpl;
import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.condition.Condition;
import org.worldgrower.condition.Conditions;

public class UTestEnlargeAction {

	@Test
	public void testExecute() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = createPerformer(2);
		
		Actions.ENLARGE_ACTION.execute(performer, performer, new int[0], world);
		
		assertEquals(true, performer.getProperty(Constants.CONDITIONS).hasCondition(Condition.ENLARGED_CONDITION));
	}
	
	@Test
	public void testExecuteReduced() {
		World world = new WorldImpl(0, 0, null, null);
		WorldObject performer = createPerformer(2);
		
		Actions.REDUCE_ACTION.execute(performer, performer, new int[0], world);
		
		Actions.ENLARGE_ACTION.execute(performer, performer, new int[0], world);
		
		assertEquals(false, performer.getProperty(Constants.CONDITIONS).hasCondition(Condition.ENLARGED_CONDITION));
		assertEquals(false, performer.getProperty(Constants.CONDITIONS).hasCondition(Condition.REDUCED_CONDITION));
	}
	
	private WorldObject createPerformer(int id) {
		WorldObject performer = TestUtils.createSkilledWorldObject(id, Constants.CONDITIONS, new Conditions());
		performer.setProperty(Constants.X, 0);
		performer.setProperty(Constants.Y, 0);
		performer.setProperty(Constants.WIDTH, 1);
		performer.setProperty(Constants.HEIGHT, 1);
		performer.setProperty(Constants.ENERGY, 1000);
		return performer;
	}
}