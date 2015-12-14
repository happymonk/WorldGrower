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
package org.worldgrower;

import static org.junit.Assert.assertEquals;
import static org.worldgrower.TestUtils.createWorldObject;

import org.junit.Test;
import org.worldgrower.actions.Actions;
import org.worldgrower.goal.Goals;

public class UTestMetaInformation {
	
	@Test
	public void testSetNoActionPossible() {
		WorldObject performer = createWorldObject(0, "performer");
		MetaInformation metaInformation = new MetaInformation(performer);

		metaInformation.setNoActionPossible();
		
		assertEquals(1, metaInformation.getCurrentTask().size());
		assertEquals(Actions.DO_NOTHING_ACTION, metaInformation.getCurrentTask().peek().getManagedOperation());
		assertEquals(Goals.IDLE_GOAL, metaInformation.getFinalGoal());
	}
}
