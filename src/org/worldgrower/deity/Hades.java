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
package org.worldgrower.deity;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.List;

import org.worldgrower.Constants;
import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.creaturetype.CreatureType;
import org.worldgrower.profession.Professions;

public class Hades implements Deity {

	@Override
	public String getName() {
		return "Hades";
	}

	@Override
	public String getExplanation() {
		return getName() + " is the God of underworld and all things beneath the earth.";
	}
	
	public Object readResolve() throws ObjectStreamException {
		return readResolveImpl();
	}

	@Override
	public List<String> getReasons() {
		return Arrays.asList(
				getName() + " rules the underworld and I worship him as a sign of respect for my relatives who are there.",
				"The cycle of life and death must remain undisturbed.",
				"As a priest of " + getName() + ", I strive to keep things of the underworld where they belong",
				getName() + " is God of the underworld, and as a grave digger I want to keep dead things in the underworld"
		);
				
	}

	@Override
	public int getReasonIndex(WorldObject performer, World world) {
		int undeadCreatureCount = world.findWorldObjects(w -> w.getProperty(Constants.CREATURE_TYPE) == CreatureType.UNDEAD_CREATURE_TYPE).size();
		if (undeadCreatureCount > 0) {
			return 1;
		} else if (performer.getProperty(Constants.PROFESSION) == Professions.PRIEST_PROFESSION) {
			return 2;
		} else if (performer.getProperty(Constants.PROFESSION) == Professions.GRAVE_DIGGER_PROFESSION) {
			return 3;
		}
		
		return -1;
	}
}
